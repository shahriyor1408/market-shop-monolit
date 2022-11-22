package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Image;
import com.company.telegrambotapp.domains.Product;
import com.google.cloud.storage.Storage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 18:39
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
public class StorageService {
    @Value("${image.upload.path}")
    private String uploadLocation;

    @PostConstruct
    public void createFile() {
        var uploadPath = Paths.get(uploadLocation);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectory(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Image uploadCover(MultipartFile file, @NonNull Product product) {
        var fileName = file.getOriginalFilename();
        var dest = Paths.get(uploadLocation + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
            return Image
                    .builder()
                    .contentType(file.getContentType())
                    .originalName(fileName)
                    .size(file.getSize())
                    .path(dest.toAbsolutePath().toString())
                    .product(product)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Something wrong try again! Check your action!");
        }
    }
}
