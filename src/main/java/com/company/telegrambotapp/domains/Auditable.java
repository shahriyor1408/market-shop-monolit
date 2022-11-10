package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:21
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Builder.Default
    protected Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    protected Long createdBy;

    protected Timestamp updatedAt;
    protected Long updatedBy;

    @Builder.Default
    protected boolean isDeleted = false;
}