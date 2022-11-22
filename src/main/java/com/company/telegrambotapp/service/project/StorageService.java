package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Image;
import com.company.telegrambotapp.domains.Product;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 18:39
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
public class StorageService {
    private Storage storage;

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

    public String upload(MultipartFile file) throws IOException {
        String newFilename = "unnamed." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        BlobId blobId = BlobId.of("shakhriyor-s-project.appspot.com", newFilename);

        Map<String, String> metaData = new HashMap<>();
        metaData.put("originalName", file.getOriginalFilename());
        metaData.put("contentType", file.getContentType());
        metaData.put("size", "" + file.getSize());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setMetadata(metaData)
                .build();
        storage.create(blobInfo, file.getInputStream().readAllBytes());
        return "https://firebasestorage.googleapis.com/v0/b/shakhriyor-s-project.appspot.com/o/"
                + newFilename + "?alt=media&token=a699a9dd-8e96-4968-91d9-fbaf726a5fe1";
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
                    .path(uploadLocation + "/" + fileName)
                    .product(product)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Something wrong try again! Check your action!");
        }
    }

    @EventListener
    public void init(ApplicationReadyEvent applicationReadyEvent) {
        try {
            InputStream serviceAccount = new FileInputStream("/home/shahriyor/IdeaProjects/telegram-bot-app/src/main/resources/shakhriyor-s-project-firebase-adminsdk-kag4e-df82b362b0.json");
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("market-uz-project")
                    .build().getService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
