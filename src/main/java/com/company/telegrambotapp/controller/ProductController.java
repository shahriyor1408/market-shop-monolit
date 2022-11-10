package com.company.telegrambotapp.controller;

import com.company.telegrambotapp.dtos.product.ProductCreateDto;
import com.company.telegrambotapp.dtos.product.ProductDto;
import com.company.telegrambotapp.dtos.product.ProductUpdateDto;
import com.company.telegrambotapp.response.ApiResponse;
import com.company.telegrambotapp.service.project.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:04
 * telegram-bot-app/IntelliJ IDEA
 */
@RestController
public class ProductController extends ApiController<ProductService> {

    protected ProductController(ProductService service) {
        super(service);
    }

    @PostMapping(PATH + "/product/create")
    public ApiResponse<Long> create(@Valid @RequestBody ProductCreateDto dto) {
        return new ApiResponse<>(service.create(dto));
    }

    @GetMapping(PATH + "/product/get/{id}")
    public ApiResponse<ProductDto> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @GetMapping(PATH + "/product/getAll/{categoryId}")
    public ApiResponse<List<ProductDto>> getAllByCategory(@PathVariable Long categoryId) {
        return new ApiResponse<>(service.getAllByCategory(categoryId));
    }

    @GetMapping(PATH + "/product/getAll")
    public ApiResponse<List<ProductDto>> getAll() {
        return new ApiResponse<>(service.getAll());
    }

    @PutMapping(PATH + "/product/update")
    public ApiResponse<ProductDto> update(@Valid @RequestBody ProductUpdateDto dto) {
        return new ApiResponse<>(service.update(dto));
    }

    @DeleteMapping(PATH + "/product/delete/{id}")
    public ApiResponse<Long> delete(@PathVariable Long id) {
        return new ApiResponse<>(service.delete(id));
    }

    @PostMapping(value = PATH + "/product/uploadCover/{id}", consumes = "multipart/form-data")
    public ApiResponse<Void> uploadCover(@RequestBody MultipartFile file, @PathVariable Long id) {
        service.uploadCover(id, file);
        return new ApiResponse<>(200, true);
    }

    @GetMapping(PATH + "/product/getCover/{id}")
    public ResponseEntity<byte[]> getCover(@PathVariable Long id) {
        return service.getCover(id);
    }
}
