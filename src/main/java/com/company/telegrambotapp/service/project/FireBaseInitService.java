package com.company.telegrambotapp.service.project;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 18:42
 * telegram-bot-app/IntelliJ IDEA
 */
public class FireBaseInitService {
    @Service
    class FBInitialize {
        @PostConstruct
        public void init() {
            try {
                FileInputStream serviceAccount = new FileInputStream("/home/shahriyor/IdeaProjects/telegram-bot-app/src/main/resources/shakhriyor-s-project-firebase-adminsdk-kag4e-df82b362b0.json");
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
