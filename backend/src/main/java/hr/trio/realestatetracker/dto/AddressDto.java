package hr.trio.realestatetracker.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;

    @NotNull(message = "Country is mandatory")
    private String country;

    @NotNull(message = "State is mandatory")
    private String state;

    @NotNull(message = "City is mandatory")
    private String city;

    @NotNull(message = "Street is mandatory")
    private String street;

    @NotNull(message = "House number is mandatory")
    private String houseNumber;

    private String floor;

    private String doorNumber;

    @NotNull(message = "Longitude is mandatory")
    private Double longitude;

    @NotNull(message = "Latitude is mandatory")
    private Double latitude;

}
