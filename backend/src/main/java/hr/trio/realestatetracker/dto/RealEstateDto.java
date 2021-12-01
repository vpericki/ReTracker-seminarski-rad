package hr.trio.realestatetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateDto {

    private Long id;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Description is mandatory")
    private String description;

    private BigDecimal rentPrice;

    private BigDecimal sellPrice;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Double rating;

    @NotNull(message = "Quadrature is mandatory")
    private Integer quadrature;

    private Integer rooms;

    private Integer baths;

    private boolean forRent;

    private boolean forSale;

    @NotNull(message = "Images are mandatory")
    private Set<ImageDto> images;

    @NotNull(message = "Real estate type is mandatory")
    private RealEstateTypeDto realEstateTypeDto;

    @NotNull(message = "Address is mandatory")
    private AddressDto address;

    private UserDto createdBy;

    private UserDto updatedBy;

}
