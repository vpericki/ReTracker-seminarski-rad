package hr.trio.realestatetracker.model.codebook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Roles {
    ROLE_USER(1L, "ROLE_USER"),
    ROLE_ADMIN(2L, "ROLE_ADMIN");

    private final Long id;
    private final String name;
}
