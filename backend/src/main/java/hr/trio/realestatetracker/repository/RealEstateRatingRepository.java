package hr.trio.realestatetracker.repository;

import hr.trio.realestatetracker.model.RealEstate;
import hr.trio.realestatetracker.model.RealEstateRating;
import hr.trio.realestatetracker.model.RealEstateRatingId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealEstateRatingRepository extends JpaRepository<RealEstateRating, RealEstateRatingId> {

    List<RealEstateRating> findAllByRealEstateRatingId_RealEstate(RealEstate realEstate);

}
