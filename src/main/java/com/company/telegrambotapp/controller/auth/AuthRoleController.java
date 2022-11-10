package com.company.telegrambotapp.controller.auth;

import com.company.telegrambotapp.controller.ApiController;
import com.company.telegrambotapp.dtos.auth.AuthRoleCreateDTO;
import com.company.telegrambotapp.dtos.auth.AuthRoleDTO;
import com.company.telegrambotapp.response.ApiResponse;
import com.company.telegrambotapp.service.auth.AuthRoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:19
 * telegram-bot-app/IntelliJ IDEA
 */

@RestController
public class AuthRoleController extends ApiController<AuthRoleService> {


    protected AuthRoleController(AuthRoleService service) {
        super(service);
    }

    @GetMapping(PATH + "/role")
    public ApiResponse<List<AuthRoleDTO>> getAll() {
        return new ApiResponse<>(service.getAll());
    }

    @GetMapping(PATH + "/role/{id}")
    public ApiResponse<AuthRoleDTO> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @PostMapping(PATH + "/role")
    public ApiResponse<Void> create(@Valid @RequestBody AuthRoleCreateDTO dto) {
        service.create(dto);
        // TODO: 19/08/22 standardize status codes
        return new ApiResponse<>(201);
    }

    @DeleteMapping(PATH + "/role/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        // TODO: 19/08/22 standardize status codes
        return new ApiResponse<>(204);
    }


}
