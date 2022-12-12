package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 21/11/22 Monday 16:44
 * telegram-bot-app/IntelliJ IDEA
 */
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {

    @Query(value = "from ImageProduct where product.id = :id")
    Optional<List<ImageProduct>> getImagesByProductId(@Param(value = "id") Long id);
}
