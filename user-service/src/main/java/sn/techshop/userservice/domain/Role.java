package sn.techshop.userservice.domain;

import lombok.Getter;

public enum Role {
    USER("USER", "Utilisateur standard"),
    ADMIN("ADMIN", "Administrateur"),
    MODERATOR("MODERATOR", "Modérateur");

    private final String authority;
    @Getter
    private final String description;

    Role(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    public String getAuthority() {
        return "ROLE_" + authority;
    }

    public String getValue() {
        return authority;
    }
}
