package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 25/11/22 Friday 16:17
 * telegram-bot-app/IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ImageCategory {
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
    private Category category;
}
