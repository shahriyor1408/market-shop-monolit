package com.company.telegrambotapp.service.project;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 18:39
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
public class StorageService {
    private Storage storage;

    public String upload(MultipartFile file) throws IOException {
        String newFilename = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
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
        return newFilename;
    }


    public ResponseEntity<byte[]> getCover(@PathVariable String filename) throws IOException {
        BlobId blobId = BlobId.of("shakhriyor-s-project.appspot.com", filename);

        Blob blob = storage.get(blobId);
        Map<String, String> metadata = blob.getMetadata();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + metadata.get("originalName"))
                .contentLength(Long.parseLong(metadata.get("size")))
                .contentType(MediaType.parseMediaType(metadata.get("contentType")))
                .body(blob.getContent());
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
