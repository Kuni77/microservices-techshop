package sn.techshop.userservice.service.dto;

import sn.techshop.userservice.domain.Role;

import java.util.UUID;

public record UserDto(
        UUID id,
        String email,
        String firstName,
        String lastName,
        Role role,
        Boolean enabled
) {
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
