package com.company.telegrambotapp;

import com.company.telegrambotapp.service.bot.MyBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class TelegramBotAppApplication {
    private final MyBot myBot;

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotAppApplication.class, args);
    }

    @PostConstruct
    public void register() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
