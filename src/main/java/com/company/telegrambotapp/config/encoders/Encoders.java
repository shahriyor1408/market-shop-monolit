package com.company.telegrambotapp.config.encoders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:19
 * telegram-bot-app/IntelliJ IDEA
 */

@Configuration
public class Encoders {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
