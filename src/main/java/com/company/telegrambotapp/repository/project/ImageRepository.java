package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.Image;
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
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "from Image where product.id = :id")
    Optional<List<Image>> getImagesByProductId(@Param(value = "id") Long id);
}
