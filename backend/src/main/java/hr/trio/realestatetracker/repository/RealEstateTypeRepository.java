package hr.trio.realestatetracker.repository;

import hr.trio.realestatetracker.model.RealEstateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateTypeRepository extends JpaRepository<RealEstateType, Long> {
}
