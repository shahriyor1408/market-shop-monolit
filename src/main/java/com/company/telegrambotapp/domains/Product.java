package com.company.telegrambotapp.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 08:58
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends Auditable {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private Timestamp expiry;

    @ManyToOne
    private Category category;

    @Builder(builderMethodName = "childBuilder")
    public Product(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy,
                   boolean isDeleted, String name, String description, Double price,
                   String companyName, Timestamp expiry, Category category) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.name = name;
        this.description = description;
        this.price = price;
        this.companyName = companyName;
        this.expiry = expiry;
        this.category = category;
    }
}
