package hr.trio.realestatetracker.model.codebook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RealEstateTypes {
    LOT(1L, "LOT"),
    APARTMENT(2L, "APARTMENT"),
    HOUSE(3L, "HOUSE");

    private final Long id;
    private final String type;
}
