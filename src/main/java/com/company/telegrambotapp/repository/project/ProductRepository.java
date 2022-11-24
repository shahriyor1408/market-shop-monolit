package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.Product;
import com.company.telegrambotapp.dtos.product.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:06
 * telegram-bot-app/IntelliJ IDEA
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("from Product where id = :id and isDeleted = false")
    Optional<Product> get(@Param(value = "id") Long categoryId);

    @Query("from Product where category.id = :id and isDeleted = false")
    Optional<List<Product>> getAllByCategory(@Param(value = "id") Long categoryId);

    @Query("from Product where isDeleted = false")
    Optional<List<Product>> getAll();

    @Query("from Product where name like %:search%")
    Optional<ArrayList<Product>> search(@Param(value = "search") String search);
}
