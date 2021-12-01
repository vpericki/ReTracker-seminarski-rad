package hr.trio.realestatetracker.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateRatingId implements Serializable {

    @ManyToOne
    private RealEstate realEstate;

    @ManyToOne
    private User user;

}
