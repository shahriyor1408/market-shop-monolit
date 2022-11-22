package com.company.telegrambotapp.service.bot;

import com.company.telegrambotapp.domains.Order;
import com.company.telegrambotapp.domains.OrderItem;
import com.company.telegrambotapp.dtos.product.ProductDto;
import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.repository.project.OrderRepository;
import com.company.telegrambotapp.service.auth.AuthUserService;
import com.company.telegrambotapp.service.project.CategoryService;
import com.company.telegrambotapp.service.project.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 09/11/22 Wednesday 17:19
 * telegram-bot-app/IntelliJ IDEA
 */
@RequiredArgsConstructor
@Service
public class MyBot extends TelegramLongPollingBot {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final AuthUserService userService;
    private final OrderRepository orderRepository;
    private Long chatId;

    @Value("${telegram.bot.token}")
    private String TOKEN;

    @Value("${telegram.bot.username}")
    private String USERNAME;

    @Value("${telegram.bot.admin_id}")
    private String ADMIN_ID;

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long userId = update.getMessage().getFrom().getId();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            if (String.valueOf(userId).equals(ADMIN_ID)) {
                chatId = update.getMessage().getChatId();
                handleMessage(update);
            } else {
                sendMessage.setText("Sizda ruxsat mavjud emas!");
                sendMsg(sendMessage);
            }
            log(update.getMessage().getFrom(), update.getMessage().getText());
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            User user = callbackQuery.getFrom();
            String data = callbackQuery.getData();
            handleCallBack(message, user, data);
        }
    }

    private void handleCallBack(Message message, User user, String data) {
        String orderId = data.split("/")[1];
        if (data.startsWith("confirm/")) {
            EditMessageText editMessageText = new EditMessageText(String.valueOf(message.getChatId()));
            editMessageText.setChatId(String.valueOf(user.getId()));
            editMessageText.setMessageId(message.getMessageId());
            Order order = orderRepository.findById(Long.valueOf(orderId)).orElseThrow(() -> {
                throw new GenericNotFoundException("Order not found!", 404);
            });
            order.setDelivered(true);
            orderRepository.save(order);
            editMessageText.setText("Buyurtma yetkazildi!");
            sendMsg(editMessageText);
        } else if (data.startsWith("reject/")) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            sendMsg(deleteMessage);
        }
    }

    private void handleMessage(Update update) {
        Message message = update.getMessage();
        User user = update.getMessage().getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        if (message.getText().equals("/start")) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setParseMode(ParseMode.MARKDOWN);
            sendPhoto.setChatId(message.getChatId());
            sendPhoto.setPhoto(new InputFile("https://firebasestorage.googleapis.com/v0/b/shakhriyor-s-project.appspot.com/o/market.png?alt=media&token=a699a9dd-8e96-4968-91d9-fbaf726a5fe1"));
            sendPhoto.setCaption("**@" + user.getUserName()
                    + " admin panelga xush kelibsiz!\nBu yerda sizga buyurtmalar haqida xabar berib turiladi.**");
            sendMsg(sendPhoto);
        }
    }

    private void log(User user, String text) {
        String info = String.format("ID: %s,  first name: %s,  last name: %s, text: %s",
                user.getId(), user.getFirstName(), user.getLastName(), text);
        System.out.println(info);
    }

    private void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void order(@NonNull Order order, @NonNull List<OrderItem> orderItemList) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText(getText(order, orderItemList));
        sendMessage.setReplyMarkup(getKeyBoardButtons(order));
        sendMsg(sendMessage);
    }

    private ReplyKeyboard getKeyBoardButtons(Order order) {
        InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("☑️Tasdiqlash", "confirm/" + order.getId());
        InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("❌Bekor qilish", "reject/" + order.getId());

        List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);
        List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);
        return InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row1, row2));
    }

    private String getText(Order order, @NonNull List<OrderItem> orderItemList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**Yangi buyurtma!**\n\n");
        int counter = 1;
        for (OrderItem orderItem : orderItemList) {
            ProductDto productDto = productService.get(orderItem.getProductId());
            stringBuilder.append(counter++).append(".**Nomi** - ").append(productDto.getName()).append("\n");
            stringBuilder.append("**Kategoriyasi** - ").append(categoryService.get(productDto.getCategoryId()).getName()).append("\n");
            stringBuilder.append("**Soni** - ").append(orderItem.getCount()).append("\n");
            stringBuilder.append("**Narxi(1 dona)** - ").append(productDto.getPrice()).append("\n\n");
        }
        stringBuilder.append("**Jami narx** : ").append(order.getTotalPrice()).append("$\n");
        stringBuilder.append("**Manzil** : ").append(order.getLocation()).append("\n");
        stringBuilder.append("**Username** : ").append(userService.getCurrentAuthUser().getUsername()).append("\n");
        stringBuilder.append("**Telefon** : ").append(userService.getCurrentAuthUser().getTelephone()).append("\n");
        return stringBuilder.toString();
    }
}
