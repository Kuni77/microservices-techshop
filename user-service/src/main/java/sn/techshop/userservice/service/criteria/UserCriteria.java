package sn.techshop.userservice.service.criteria;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import sn.techshop.userservice.domain.Role;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserCriteria {
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Boolean enabled;

    public boolean hasAnyFilter() {
        return email != null || firstName != null || lastName != null ||
                role != null || enabled != null;
    }
}
