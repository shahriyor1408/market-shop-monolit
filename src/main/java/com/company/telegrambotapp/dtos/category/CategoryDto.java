package com.company.telegrambotapp.dtos.category;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 03/12/22 Saturday 10:34
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private long id;
    private String name;
    private String[] images;
    private Timestamp createdAt;
    private long createdBy;
}
