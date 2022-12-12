package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 21/11/22 Monday 16:14
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String contentType;

    @ManyToOne
    private Product product;
}
