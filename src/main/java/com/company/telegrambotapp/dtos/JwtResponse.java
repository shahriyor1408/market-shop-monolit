package com.company.telegrambotapp.dtos;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType) {
}
