package sn.techshop.userservice.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.relational.core.mapping.Table;
import sn.techshop.userservice.domain.audit.Auditable;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Table("users")
public class User extends Auditable<Integer> {
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

    private Boolean enabled = true;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isEnabled() {
        return enabled != null && enabled;
    }
}
