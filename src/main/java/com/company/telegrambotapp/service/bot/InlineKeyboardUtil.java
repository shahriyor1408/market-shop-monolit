package com.company.telegrambotapp.service.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineKeyboardUtil {

    public static InlineKeyboardButton getButton(String demo, String callBack) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(demo);
        inlineKeyboardButton.setCallbackData(callBack);

        return inlineKeyboardButton;
    }

    public static List<InlineKeyboardButton> getRow(InlineKeyboardButton... row) {
        return new ArrayList<>(Arrays.asList(row));
    }

    @SafeVarargs
    public static List<List<InlineKeyboardButton>> getRowList(List<InlineKeyboardButton>... rows) {
        return new ArrayList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup getMarkup(List<List<InlineKeyboardButton>> rowList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
