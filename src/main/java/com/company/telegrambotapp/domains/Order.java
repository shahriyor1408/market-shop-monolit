package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

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
    @OneToMany
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private Long userId;
    private Integer totalPrice;
    private String location;

    private boolean delivered;
    private boolean active;

    @Builder(builderMethodName = "childBuilder")
    public Order(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt,
                 Long updatedBy, boolean isDeleted, List<OrderItem> orderItems, Long userId,
                 Integer totalPrice, String location, boolean delivered, boolean active) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.orderItems = orderItems;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.location = location;
        this.delivered = delivered;
        this.active = active;
    }
}
