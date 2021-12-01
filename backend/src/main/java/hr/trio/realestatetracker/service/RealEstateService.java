package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.dto.FilterRealEstateDto;
import hr.trio.realestatetracker.dto.RealEstateDto;
import org.springframework.data.domain.Page;

public interface RealEstateService {

    Page<RealEstateDto> getAllRealEstate(int page, int size, FilterRealEstateDto filterRealEstateDto);

    RealEstateDto getRealEstateById(Long id);

    RealEstateDto updateRealEstate(RealEstateDto realEstateDto);

    RealEstateDto updateRealEstateRating(Long realEstateId, Double rating);

    RealEstateDto createRealEstate(RealEstateDto realEstateDto);

    void deleteRealEstateById(Long id);
}
