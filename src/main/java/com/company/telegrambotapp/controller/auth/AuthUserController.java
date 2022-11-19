package com.company.telegrambotapp.controller.auth;

import com.company.telegrambotapp.controller.ApiController;
import com.company.telegrambotapp.domains.auth.AuthUser;
import com.company.telegrambotapp.dtos.JwtResponse;
import com.company.telegrambotapp.dtos.LoginRequest;
import com.company.telegrambotapp.dtos.RefreshTokenRequest;
import com.company.telegrambotapp.dtos.UserRegisterDTO;
import com.company.telegrambotapp.response.ApiResponse;
import com.company.telegrambotapp.service.auth.AuthUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:19
 * telegram-bot-app/IntelliJ IDEA
 */
@RestController
public class AuthUserController extends ApiController<AuthUserService> {
    protected AuthUserController(AuthUserService service) {
        super(service);
    }

    @PostMapping(value = PATH + "/auth/login", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ApiResponse<>(service.login(loginRequest));
    }

    @GetMapping(value = PATH + "/auth/refresh", produces = "application/json")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ApiResponse<>(service.refreshToken(refreshTokenRequest));
    }

    @PostMapping(PATH + "/auth/register")
    public ApiResponse<AuthUser> register(@Valid @RequestBody UserRegisterDTO dto) {
        return new ApiResponse<>(service.register(dto));
    }

    @GetMapping(PATH + "/auth/me")
    @PreAuthorize(value = "isAuthenticated()")
    public AuthUser me() {
        return service.getCurrentAuthUser();
    }
}
