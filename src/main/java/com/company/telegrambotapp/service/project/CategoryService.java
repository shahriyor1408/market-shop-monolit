package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Category;
import com.company.telegrambotapp.dtos.category.CategoryCreateDto;
import com.company.telegrambotapp.dtos.category.CategoryUpdateDto;
import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.mapper.CategoryMapper;
import com.company.telegrambotapp.repository.project.CategoryRepository;
import com.company.telegrambotapp.service.auth.AuthUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 12:23
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final AuthUserService userService;

    public Long create(@NonNull CategoryCreateDto dto) {
        return repository.save(Category.childBuilder()
                .name(dto.getName())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy(userService.getCurrentAuthUser().getId())
                .build()).getId();
    }

    public Category get(@NonNull Long id) {
        return repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Category is not found!", 404);
        });
    }

    public List<Category> getAll() {
        return repository.getAll();
    }

    public Category update(@NonNull CategoryUpdateDto dto) {
        Category category = repository.findById(dto.getId()).orElseThrow(() -> {
            throw new GenericNotFoundException("Category is not found!", 404);
        });
        mapper.update(dto, category);
        category.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        category.setUpdatedBy(userService.getCurrentAuthUser().getId());
        return repository.save(category);
    }

    public Long delete(@NonNull Long id) {
        Category category = get(id);
        category.setDeleted(true);
        return repository.save(category).getId();
    }
}
