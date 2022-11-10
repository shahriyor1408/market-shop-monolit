package com.company.telegrambotapp.service.bot;

import com.company.telegrambotapp.domains.Order;
import com.company.telegrambotapp.domains.OrderItem;
import com.company.telegrambotapp.dtos.product.ProductDto;
import com.company.telegrambotapp.service.auth.AuthUserService;
import com.company.telegrambotapp.service.project.CategoryService;
import com.company.telegrambotapp.service.project.ProductService;
import com.company.telegrambotapp.service.project.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
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

    private final StorageService storageService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final AuthUserService userService;
    private Long chatId;

    @Override
    public String getBotUsername() {
        return BotConstants.USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        if (update.hasMessage()) {
            if (String.valueOf(userId).equals(BotConstants.ADMIN_ID)) {
                chatId = update.getMessage().getChatId();
                handleMessage(update);
            } else {
                sendMessage.setText("You don't have permission! Try again!");
                log(update.getMessage().getFrom(), update.getMessage().getText());
                sendMsg(sendMessage);
            }
        }
    }

    public void handleMessage(Update update) {
        Message message = update.getMessage();
        User user = update.getMessage().getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        if (message.getText().equals("/start")) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setParseMode(ParseMode.MARKDOWN);
            sendPhoto.setChatId(message.getChatId());
            sendPhoto.setPhoto(new InputFile("https://firebasestorage.googleapis.com/v0/b/shakhriyor-s-project.appspot.com/o/market.png?alt=media&token=a699a9dd-8e96-4968-91d9-fbaf726a5fe1"));
            sendPhoto.setCaption("**" + user.getUserName()
                    + " admin panelga xush kelibsiz!\nBu yerda sizga buyurtmalar haqida xabar berib turiladi.**");
            sendMsg(sendPhoto);
        }
    }

    public void log(User user, String text) {
        String info = String.format("ID: %s,  first name: %s,  last name: %s, text: %s",
                user.getId(), user.getFirstName(), user.getLastName(), text);
        System.out.println(info);
    }

    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void order(Order order) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText(getText(order));
        sendMsg(sendMessage);
    }

    private String getText(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**Yangi buyurtma!**\n\n");
        List<OrderItem> orderItems = order.getOrderItems();

        int counter = 1;
        for (OrderItem orderItem : orderItems) {
            ProductDto productDto = productService.get(orderItem.getProductId());
            stringBuilder.append(counter++).append(". **Nomi** - ").append(productDto.getName()).append("\n");
            stringBuilder.append(counter++).append(". **Kategoriyasi** - ").append(categoryService.get(productDto.getCategoryId())).append("\n");
            stringBuilder.append(counter++).append("  **Soni** - ").append(orderItem.getCount()).append("\n\n");
        }
        stringBuilder.append("**Manzil** : ").append(order.getLocation()).append("\n");
        stringBuilder.append("**Telefon** : ").append(userService.getCurrentAuthUser().getTelephone()).append("\n");
        return stringBuilder.toString();
    }
}
