package com.company.telegrambotapp.controller;

import com.company.telegrambotapp.domains.Category;
import com.company.telegrambotapp.dtos.category.CategoryCreateDto;
import com.company.telegrambotapp.dtos.category.CategoryUpdateDto;
import com.company.telegrambotapp.response.ApiResponse;
import com.company.telegrambotapp.service.project.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 12:22
 * telegram-bot-app/IntelliJ IDEA
 */
@RestController
public class CategoryController extends ApiController<CategoryService> {

    protected CategoryController(CategoryService service) {
        super(service);
    }

    @PostMapping(PATH + "/category/create")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<Long> create(@Valid @RequestBody CategoryCreateDto dto) {
        return new ApiResponse<>(service.create(dto));
    }

    @GetMapping(PATH + "/category/get/{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<Category> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @GetMapping(PATH + "/category/getAll")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<List<Category>> getAll() {
        return new ApiResponse<>(service.getAll());
    }

    @PutMapping(PATH + "/category/update")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<Category> update(@Valid @RequestBody CategoryUpdateDto dto) {
        return new ApiResponse<>(service.update(dto));
    }

    @DeleteMapping(PATH + "/category/delete/{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<Long> delete(@PathVariable Long id) {
        return new ApiResponse<>(service.delete(id));
    }
}
