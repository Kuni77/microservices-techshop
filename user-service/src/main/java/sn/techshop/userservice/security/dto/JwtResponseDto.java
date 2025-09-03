package sn.techshop.userservice.security.dto;

import sn.techshop.userservice.service.dto.UserDto;

public record JwtResponseDto(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn,
        UserDto user
) {
    public JwtResponseDto {
        tokenType = "Bearer";
    }
}
