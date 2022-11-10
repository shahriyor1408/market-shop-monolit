package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Product;
import com.company.telegrambotapp.dtos.product.ProductCreateDto;
import com.company.telegrambotapp.dtos.product.ProductDto;
import com.company.telegrambotapp.dtos.product.ProductUpdateDto;
import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.mapper.ProductMapper;
import com.company.telegrambotapp.repository.project.ProductRepository;
import com.company.telegrambotapp.service.auth.AuthUserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:04
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final StorageService storageService;
    private final CategoryService categoryService;

    private final ProductMapper mapper;
    private final AuthUserService userService;

    public Long create(@NonNull ProductCreateDto dto) {
        return repository.save(Product.childBuilder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .companyName(dto.getCompanyName())
                .expiry(dto.getExpiry())
                .category(categoryService.get(dto.getCategoryId()))
                .createdBy(userService.getCurrentAuthUser().getId())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build()).getId();
    }

    public ProductDto get(@NonNull Long id) {
        Product product = repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        });
        ProductDto dto = mapper.toDto(product);
        dto.setCategoryId(categoryService.get(product.getCategory().getId()).getId());
        return dto;
    }

    private Product getOne(@NonNull Long id) {
        return repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        });
    }

    public List<ProductDto> getAll() {
        List<ProductDto> productDtos = new ArrayList<>();
        repository.getAll().orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        }).forEach(product -> productDtos.add(get(product.getId())));
        return productDtos;
    }

    public ProductDto update(@NonNull ProductUpdateDto dto) {
        Product product = getOne(dto.getId());
        mapper.update(dto, product);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedBy(userService.getCurrentAuthUser().getId());
        product.setCategory(categoryService.get(dto.getCategoryId()));
        repository.save(product);
        return get(product.getId());
    }

    public Long delete(@NonNull Long id) {
        Product product = getOne(id);
        product.setDeleted(true);
        return repository.save(product).getId();
    }

    public List<ProductDto> getAllByCategory(@NonNull Long categoryId) {
        List<ProductDto> productDtos = new ArrayList<>();
        repository.getAllByCategory(categoryId).orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        }).forEach(product -> productDtos.add(get(product.getId())));
        return productDtos;
    }

    public ResponseEntity<byte[]> getCover(@NonNull Long id) {
        Product product = getOne(id);
        try {
            return storageService.getCover(product.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadCover(@NonNull Long id, @NonNull MultipartFile file) {
        try {
            Product product = getOne(id);
            product.setImage(storageService.upload(file));
            repository.save(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
