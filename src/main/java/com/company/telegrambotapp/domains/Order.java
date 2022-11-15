package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 10:41
 * telegram-bot-app/IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class Order extends Auditable {

    @Column(nullable = false)
    private Long userId;
    private Double totalPrice;
    private String location;

    private boolean delivered;
    private boolean active;

    @Builder(builderMethodName = "childBuilder")
    public Order(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt,
                 Long updatedBy, boolean isDeleted, Long userId,
                 Double totalPrice, String location, boolean delivered, boolean active) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.location = location;
        this.delivered = delivered;
        this.active = active;
    }
}
