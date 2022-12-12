package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.ImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 25/11/22 Friday 17:00
 * telegram-bot-app/IntelliJ IDEA
 */
public interface ImageCategoryRepository extends JpaRepository<ImageCategory, Long> {
    @Query(value = "from ImageCategory where category.id = :id")
    Optional<List<ImageCategory>> getImagesByCategoryId(@Param(value = "id") Long id);
}
