package hr.trio.realestatetracker.validator.impl;

import hr.trio.realestatetracker.dto.AddressDto;
import hr.trio.realestatetracker.dto.RealEstateDto;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.model.RealEstate;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.model.codebook.RealEstateTypes;
import hr.trio.realestatetracker.repository.RealEstateRepository;
import hr.trio.realestatetracker.service.CurrentUserService;
import hr.trio.realestatetracker.validator.RealEstateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealEstateValidatorImpl implements RealEstateValidator {

    private final CurrentUserService currentUserService;

    private final RealEstateRepository realEstateRepository;

    @Override
    public void validateRealEstate(final RealEstateDto realEstateDto) {
        if (!realEstateDto.isForRent() && !realEstateDto.isForSale()) {
            throw new IllegalArgumentException("Real estate has to be listed for rent or sale.");
        }

        if (realEstateDto.isForSale() && realEstateDto.getSellPrice() == null) {
            throw new IllegalArgumentException("Real estate that is for sale has to have a sale price.");
        }

        if (realEstateDto.isForRent() && realEstateDto.getRentPrice() == null) {
            throw new IllegalArgumentException("Real estate that is for rent has to have a rent price.");
        }

        if (realEstateDto.getQuadrature() == null) {
            throw new IllegalArgumentException("Quadrature is mandatory.");
        }

        if (!realEstateDto.getRealEstateTypeDto().getType().equals(RealEstateTypes.LOT.getType())) {
            if (realEstateDto.getRooms() == null || realEstateDto.getRooms() == 0) {
                throw new IllegalArgumentException("Number of rooms is mandatory.");
            }

            if (realEstateDto.getBaths() == null) {
                throw new IllegalArgumentException("Number of baths is mandatory.");
            }
        }

        if (realEstateDto.getImages().isEmpty()) {
            throw new IllegalArgumentException("Images of the real estate are mandatory.");
        }

        if (realEstateDto.getRealEstateTypeDto() == null) {
            throw new IllegalArgumentException("Real estate type is mandatory.");
        }

        if (realEstateDto.getAddress() == null) {
            throw new IllegalArgumentException("Address is mandatory.");
        } else {
            validateAddress(realEstateDto.getAddress());
        }
    }

    private void validateAddress(final AddressDto addressDto) {
        //TODO validate that country, postal code, street and house number actually exists and address is unique
    }

    @Override
    public boolean canDeleteRealEstate(final Long id) {
        final RealEstate realEstate = realEstateRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("Real estate with ID " + id + " not found."));
        final User currentUser = currentUserService.getLoggedInUser();

        return currentUserService.isAdmin(currentUser) || realEstate.getCreatedBy().equals(currentUser) || realEstateBelongsToUserCompany(realEstate, currentUser);
    }

    private boolean realEstateBelongsToUserCompany(final RealEstate realEstate, final User currentUser) {
        return realEstate.getCreatedBy().getCompany() != null && currentUser.getCompany() != null
                && realEstate.getCreatedBy().getCompany().equals(currentUser.getCompany());
    }
}
