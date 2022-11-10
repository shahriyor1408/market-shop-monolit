package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 11:11
 * telegram-bot-app/IntelliJ IDEA
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from Order where isDeleted = false and active = false")
    Optional<List<Order>> getAll();

    @Query("from Order where id = :id and isDeleted = false and delivered = false and active = false")
    Optional<Order> get(@Param(value = "id") Long id);

    @Query("from Order where userId= :userId and isDeleted = false and delivered = false and active = true")
    Optional<Order> getByUserId(@Param(value = "userId") Long userId);
}
