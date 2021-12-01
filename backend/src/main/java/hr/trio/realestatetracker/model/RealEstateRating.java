package hr.trio.realestatetracker.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "real_estate_rating")
public class RealEstateRating {

    @EmbeddedId
    private RealEstateRatingId realEstateRatingId;

    private Double rating;

}
