package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 12:23
 * telegram-bot-app/IntelliJ IDEA
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("from Category where isDeleted = false and id = :id")
    Optional<Category> get(Long id);

    @Query("from Category where isDeleted = false")
    List<Category> getAll();
}
