package com.company.telegrambotapp;

import com.company.telegrambotapp.domains.auth.AuthRole;
import com.company.telegrambotapp.domains.auth.AuthUser;
import com.company.telegrambotapp.repository.auth.AuthRoleRepository;
import com.company.telegrambotapp.repository.auth.AuthUserRepository;
import com.company.telegrambotapp.service.bot.MyBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class TelegramBotAppApplication {
    private final MyBot myBot;
    private final AuthUserRepository authUserRepository;

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotAppApplication.class, args);
    }

    @PostConstruct
    public void register() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(myBot);
            AuthRole authRole = AuthRole.builder()
                    .name("Admin")
                    .code("ADMIN")
                    .build();

            AuthUser authUser = AuthUser.builder()
                    .username("shahriyor")
                    .password("admin")
                    .fullName("Sohidjonov Shahriyor")
                    .telephone("+998900265214")
                    .roles(List.of(authRole))
                    .build();
            authUserRepository.save(authUser);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
