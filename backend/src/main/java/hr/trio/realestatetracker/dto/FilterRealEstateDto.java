package hr.trio.realestatetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRealEstateDto {

    private String name;
    private String description;
    private BigDecimal minRentPrice;
    private BigDecimal maxRentPrice;
    private BigDecimal minSellPrice;
    private BigDecimal maxSellPrice;
    private LocalDateTime creationDateAfter;
    private LocalDateTime creationDateBefore;
    private Double minRating;
    private Double maxRating;
    private Integer minQuadrature;
    private Integer maxQuadrature;
    private Integer minRooms;
    private Integer maxRooms;
    private Integer minBaths;
    private Integer maxBaths;
    private boolean forRent;
    private boolean forSale;
    private List<Long> realEstateTypeList;
    private AddressDto address;
    private Integer radius;
    private List<Long> userIdList;
    private List<Long> companyIdList;
    private Double longitudeA;
    private Double longitudeB;
    private Double latitudeA;
    private Double latitudeB;

}
