package sn.techshop.userservice.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @Email(message = "Email doit être valide")
        @NotBlank(message = "Email obligatoire")
        String email,

        @NotBlank(message = "Mot de passe obligatoire")
        @Size(min = 8, max = 100, message = "Mot de passe entre 8 et 100 caractères")
        String password,

        @NotBlank(message = "Prénom obligatoire")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Nom obligatoire")
        @Size(max = 100)
        String lastName
) {
}
