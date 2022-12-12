package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Category;
import com.company.telegrambotapp.domains.ImageCategory;
import com.company.telegrambotapp.domains.ImageProduct;
import com.company.telegrambotapp.domains.Product;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 18:39
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
public class StorageService {

    private final String productImage;
    private final String categoryImage;
    private final String imagePath;

    public StorageService(@Value("${product.image.upload.path}") String productImage,
                          @Value("${category.image.upload.path}") String categoryImage,
                          @Value("${image.upload.path}") String imagePath) {
        this.productImage = productImage;
        this.categoryImage = categoryImage;
        this.imagePath = imagePath;
    }

    @PostConstruct
    public void createFile() {
        var uploadPath1 = Paths.get(productImage);
        var uploadPath2 = Paths.get(categoryImage);
        if (!Files.exists(uploadPath1)) {
            try {
                Files.createDirectory(uploadPath1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Files.exists(uploadPath2)) {
            try {
                Files.createDirectory(uploadPath2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ImageProduct uploadCoverProduct(MultipartFile file, @NonNull Product product) {
        var fileName = UUID.randomUUID() + file.getOriginalFilename();
        var dest = Paths.get(productImage + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
            return ImageProduct
                    .builder()
                    .contentType(file.getContentType())
                    .originalName(fileName)
                    .size(file.getSize())
                    .path(imagePath + dest.toAbsolutePath())
                    .product(product)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Something wrong try again! Check your action!");
        }
    }

    public ImageCategory uploadCoverCategory(MultipartFile file, @NonNull Category category) {
        var fileName = UUID.randomUUID() + file.getOriginalFilename();
        var dest = Paths.get(categoryImage + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
            return ImageCategory
                    .builder()
                    .contentType(file.getContentType())
                    .originalName(fileName)
                    .size(file.getSize())
                    .path(imagePath + dest.toAbsolutePath())
                    .category(category)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Something wrong try again! Check your action!");
        }
    }
}
