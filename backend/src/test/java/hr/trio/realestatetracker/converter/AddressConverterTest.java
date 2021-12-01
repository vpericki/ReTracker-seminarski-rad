package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.AddressDto;
import hr.trio.realestatetracker.model.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AddressConverterTest {

    @InjectMocks
    private AddressConverter addressConverter;

    @Test
    public void shouldConvertAddressToAddressDto() {
        final Address address = Address.builder().id(1L).city("Zagreb").country("Croatia").state("Zagrebacka zupanija").street("Ilica").houseNumber("1").build();
        final AddressDto addressDto = AddressDto.builder().id(1L).city("Zagreb").country("Croatia").state("Zagrebacka zupanija").street("Ilica").houseNumber("1").build();

        assertThat(addressConverter.convert(address)).isEqualTo(addressDto);
    }

    @Test
    public void shouldConvertAddressDtoToAddress() {
        final Address address = Address.builder().id(1L).city("Zagreb").country("Croatia").state("Zagrebacka zupanija").street("Ilica").houseNumber("1").build();
        final AddressDto addressDto = AddressDto.builder().id(1L).city("Zagreb").country("Croatia").state("Zagrebacka zupanija").street("Ilica").houseNumber("1").build();

        assertThat(addressConverter.convert(addressDto)).isEqualTo(address);
    }

}