package hr.trio.realestatetracker.validator;

import hr.trio.realestatetracker.dto.RealEstateDto;

public interface RealEstateValidator {

    void validateRealEstate(RealEstateDto realEstateDto);

    boolean canDeleteRealEstate(Long realEstateId);

}
