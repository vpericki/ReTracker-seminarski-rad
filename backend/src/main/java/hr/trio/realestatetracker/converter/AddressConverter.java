package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.AddressDto;
import hr.trio.realestatetracker.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressConverter implements Converter<Address, AddressDto> {

    @Override
    public AddressDto convert(final Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .state(address.getState())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .build();
    }

    public Address convert(final AddressDto addressDto) {
        return Address.builder()
                .id(addressDto.getId())
                .country(addressDto.getCountry())
                .state(addressDto.getState())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .houseNumber(addressDto.getHouseNumber())
                .longitude(addressDto.getLongitude())
                .latitude(addressDto.getLatitude())
                .build();
    }

}
