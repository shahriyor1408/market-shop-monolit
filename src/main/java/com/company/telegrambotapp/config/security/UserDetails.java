package com.company.telegrambotapp.config.security;

import com.company.telegrambotapp.domains.auth.AuthUser;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:19
 * telegram-bot-app/IntelliJ IDEA
 */
@Builder
public record UserDetails(AuthUser authUser) implements org.springframework.security.core.userdetails.UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(authUser.getRoles()))
            authUser.getRoles().forEach(role -> {
                authorities.add(authority.apply(role.getAuthority()));
                if (Objects.nonNull(role.getPermissions()))
                    role.getPermissions().forEach(permission -> authorities.add(authority.apply(permission.getAuthority())));
            });
        return authorities;
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.isActive();
    }

    @Override
    public boolean isEnabled() {
        return authUser.isActive();
    }

    private final static Function<String, SimpleGrantedAuthority> authority = SimpleGrantedAuthority::new;
}
