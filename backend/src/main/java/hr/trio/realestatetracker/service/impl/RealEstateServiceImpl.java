package hr.trio.realestatetracker.service.impl;

import hr.trio.realestatetracker.converter.RealEstateConverter;
import hr.trio.realestatetracker.converter.UserConverter;
import hr.trio.realestatetracker.dto.FilterRealEstateDto;
import hr.trio.realestatetracker.dto.RealEstateDto;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.exception.PermissionException;
import hr.trio.realestatetracker.model.RealEstate;
import hr.trio.realestatetracker.model.RealEstateRating;
import hr.trio.realestatetracker.model.RealEstateRatingId;
import hr.trio.realestatetracker.repository.RealEstateRatingRepository;
import hr.trio.realestatetracker.repository.RealEstateRepository;
import hr.trio.realestatetracker.service.CurrentUserService;
import hr.trio.realestatetracker.service.RealEstateService;
import hr.trio.realestatetracker.specification.RealEstateSpecification;
import hr.trio.realestatetracker.validator.RealEstateValidator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealEstateServiceImpl implements RealEstateService {

    private final CurrentUserService currentUserService;

    private final RealEstateRepository realEstateRepository;

    private final RealEstateRatingRepository realEstateRatingRepository;

    private final RealEstateValidator realEstateValidator;

    private final RealEstateConverter realEstateConverter;

    private final UserConverter userConverter;

    @Override
    public Page<RealEstateDto> getAllRealEstate(final int page, final int size, final FilterRealEstateDto filterRealEstateDto) {
        final Pageable pageable = PageRequest.of(page, size);

        return realEstateRepository.findAll(new RealEstateSpecification(filterRealEstateDto), pageable).map(realEstateConverter::convert);
    }

    @Override
    public RealEstateDto getRealEstateById(final Long id) {
        return realEstateConverter.convert(realEstateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Real estate with ID " + id + " not found.")));
    }

    @Override
    public RealEstateDto updateRealEstate(final RealEstateDto realEstateDto) {
        if (!realEstateRepository.existsById(realEstateDto.getId())) {
            throw new NotFoundException("Real estate with ID " + realEstateDto.getId() + " not found.");
        }

        realEstateValidator.validateRealEstate(realEstateDto);

        final RealEstate realEstate = realEstateConverter.convert(realEstateDto);
        realEstate.setUpdateDate(LocalDateTime.now());
        realEstate.setUpdatedBy(currentUserService.getLoggedInUser());

        return realEstateConverter.convert(realEstateRepository.save(realEstate));
    }

    @Override
    public RealEstateDto updateRealEstateRating(final Long realEstateId, final Double rating) {
        final RealEstate realEstate = realEstateRepository.findById(realEstateId)
                .orElseThrow(() -> new NotFoundException("Real estate with ID " + realEstateId + " not found."));

        final RealEstateRating realEstateRating = new RealEstateRating(new RealEstateRatingId(realEstate, currentUserService.getLoggedInUser()), rating);

        realEstateRatingRepository.save(realEstateRating);

        final Double newRating = realEstateRatingRepository.findAllByRealEstateRatingId_RealEstate(realEstate).stream()
                .mapToDouble(RealEstateRating::getRating)
                .average()
                .getAsDouble();

        realEstate.setRating(newRating);
        realEstate.setUpdatedBy(currentUserService.getLoggedInUser());
        realEstate.setUpdateDate(LocalDateTime.now());

        return realEstateConverter.convert(realEstateRepository.save(realEstate));
    }

    @Override
    public RealEstateDto createRealEstate(final RealEstateDto realEstateDto) {
        realEstateValidator.validateRealEstate(realEstateDto);

        realEstateDto.setCreatedBy(userConverter.convert(currentUserService.getLoggedInUser()));
        realEstateDto.setCreationDate(LocalDateTime.now());

        return realEstateConverter.convert(realEstateRepository.save(realEstateConverter.convert(realEstateDto)));
    }

    @Override
    public void deleteRealEstateById(final Long id) {
        if (!realEstateValidator.canDeleteRealEstate(id)) {
            throw new PermissionException("Can not delete real estate with a different owner.");
        }

        realEstateRepository.deleteById(id);
    }

}
