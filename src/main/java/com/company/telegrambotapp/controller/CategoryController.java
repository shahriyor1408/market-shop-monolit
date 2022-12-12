package com.company.telegrambotapp.controller;

import com.company.telegrambotapp.domains.Category;
import com.company.telegrambotapp.dtos.category.CategoryCreateDto;
import com.company.telegrambotapp.dtos.category.CategoryDto;
import com.company.telegrambotapp.dtos.category.CategoryUpdateDto;
import com.company.telegrambotapp.response.ApiResponse;
import com.company.telegrambotapp.service.project.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public ApiResponse<CategoryDto> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @GetMapping(PATH + "/category/getAll")
    public ApiResponse<List<CategoryDto>> getAll() {
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

    @PostMapping(value = PATH + "/category/uploadCover/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> uploadCover(@RequestBody MultipartFile file, @PathVariable Long id) {
        service.uploadCover(id, file);
        return new ApiResponse<>(200, true);
    }

    @GetMapping(value = "/image", produces = MediaType.MULTIPART_FORM_DATA)
    public void getImage(@RequestParam(name = "img") String img, HttpServletResponse resp) {
        ServletOutputStream outputStream;
        try {
            outputStream = resp.getOutputStream();
            Path path = Path.of(img);
            Files.copy(path, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
