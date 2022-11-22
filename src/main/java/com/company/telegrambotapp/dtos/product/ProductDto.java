package com.company.telegrambotapp.dtos.product;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 23:04
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String companyName;
    private Timestamp expiry;
    private String[] images;
    private Long categoryId;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private boolean isDeleted;
}
