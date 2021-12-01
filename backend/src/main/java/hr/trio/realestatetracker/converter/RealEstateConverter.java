package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.RealEstateDto;
import hr.trio.realestatetracker.dto.RealEstateTypeDto;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.model.RealEstate;
import hr.trio.realestatetracker.model.RealEstateType;
import hr.trio.realestatetracker.repository.RealEstateTypeRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealEstateConverter implements Converter<RealEstate, RealEstateDto> {

    private final AddressConverter addressConverter;
    private final ImageConverter imageConverter;
    private final UserConverter userConverter;
    private final RealEstateTypeRepository realEstateTypeRepository;

    @Override
    public RealEstateDto convert(final RealEstate realEstate) {
        return RealEstateDto.builder()
                .id(realEstate.getId())
                .name(realEstate.getName())
                .description(realEstate.getDescription())
                .rentPrice(realEstate.getRentPrice())
                .sellPrice(realEstate.getSellPrice())
                .creationDate(realEstate.getCreationDate())
                .updateDate(realEstate.getUpdateDate())
                .rating(realEstate.getRating())
                .quadrature(realEstate.getQuadrature())
                .rooms(realEstate.getRooms())
                .baths(realEstate.getBaths())
                .forRent(realEstate.isForRent())
                .forSale(realEstate.isForSale())
                .images(realEstate.getImages().stream().map(imageConverter::convert).collect(Collectors.toSet()))
                .address(addressConverter.convert(realEstate.getAddress()))
                .createdBy(userConverter.convert(realEstate.getCreatedBy()))
                .creationDate(realEstate.getCreationDate())
                .updatedBy(realEstate.getUpdatedBy() != null ? userConverter.convert(realEstate.getUpdatedBy()) : null)
                .updateDate(realEstate.getUpdateDate())
                .realEstateTypeDto(RealEstateTypeDto.builder().id(realEstate.getRealEstateType().getId()).type(realEstate.getRealEstateType().getType()).build())
                .build();
    }

    public RealEstate convert(final RealEstateDto realEstateDto) {
        return RealEstate.builder()
                .id(realEstateDto.getId())
                .name(realEstateDto.getName())
                .description(realEstateDto.getDescription())
                .rentPrice(realEstateDto.getRentPrice())
                .sellPrice(realEstateDto.getSellPrice())
                .creationDate(realEstateDto.getCreationDate())
                .updateDate(realEstateDto.getUpdateDate())
                .rating(realEstateDto.getRating())
                .quadrature(realEstateDto.getQuadrature())
                .rooms(realEstateDto.getRooms())
                .baths(realEstateDto.getBaths())
                .forRent(realEstateDto.isForRent())
                .forSale(realEstateDto.isForSale())
                .images(realEstateDto.getImages().stream().map(imageConverter::convert).collect(Collectors.toSet()))
                .address(addressConverter.convert(realEstateDto.getAddress()))
                .createdBy(userConverter.convert(realEstateDto.getCreatedBy()))
                .creationDate(realEstateDto.getCreationDate())
                .updatedBy(realEstateDto.getUpdatedBy() != null ? userConverter.convert(realEstateDto.getUpdatedBy()) : null)
                .updateDate(realEstateDto.getUpdateDate())
                .realEstateType(getRealEstateType(realEstateDto))
                .build();
    }

    private RealEstateType getRealEstateType(RealEstateDto realEstateDto) {
        return realEstateTypeRepository.findById(realEstateDto.getRealEstateTypeDto().getId())
                .orElseThrow(() -> new NotFoundException(String.format("Real estate type with ID %d not found", realEstateDto.getRealEstateTypeDto().getId())));
    }

}
