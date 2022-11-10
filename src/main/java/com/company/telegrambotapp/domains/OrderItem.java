package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 10:46
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem extends Auditable {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer count;

    @Builder(builderMethodName = "childBuilder")
    public OrderItem(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt,
                     Long updatedBy, boolean isDeleted, Long productId, Integer count) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.productId = productId;
        this.count = count;
    }
}
