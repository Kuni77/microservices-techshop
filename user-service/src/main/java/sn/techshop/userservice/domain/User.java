package sn.techshop.userservice.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class User {
    @UUID
    private String id;

    @Email(message = "Email must be validated")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password at least 8 caractères")
    private String password;

    @NotBlank(message = "Firstname is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(max = 100)
    private String lastName;

    private Role role = Role.USER;

    private Boolean active = true;
}
