package com.company.telegrambotapp.config.security;

import com.company.telegrambotapp.domains.auth.AuthUser;
import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.exceptions.GenericRuntimeException;
import com.company.telegrambotapp.repository.auth.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 16/11/22 Wednesday 16:16
 * telegram-bot-app/IntelliJ IDEA
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationProvider {

    private final PasswordEncoder encoder;
    private final AuthUserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthUser authUser = repository.findByUsername(username).orElseThrow(() -> {
            throw new GenericNotFoundException("Invalid username or password!", 404);
        });
        if (!encoder.matches(password, authUser.getPassword())) {
            String message;
            int statusCode;
            authUser.setLoginTryCount(authUser.getLoginTryCount() + 1);
            if (authUser.getLoginTryCount() == 3) {
                message = "You are blocked! Try 2 minutes later!";
                statusCode = 403;
                authUser.setStatus(AuthUser.Status.BLOCKED);
                authUser.setLastLoginTime(LocalDateTime.now());
            } else {
                message = "Invalid username or password!";
                statusCode = 400;
            }
            repository.save(authUser);
            throw new GenericRuntimeException(message, statusCode);
        }
        return new UsernamePasswordAuthenticationToken(new UserDetails(authUser), authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
