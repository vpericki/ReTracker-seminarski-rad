package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.CompanyDto;
import hr.trio.realestatetracker.model.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CompanyConverter implements Converter<Company, CompanyDto> {

    private final AddressConverter addressConverter;

    @Override
    public CompanyDto convert(final Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .email(company.getEmail())
                .rating(company.getRating())
                .address(addressConverter.convert(company.getAddress()))
                .build();
    }

    public Company convert(final CompanyDto companyDto) {
        return Company.builder()
                .id(companyDto.getId())
                .name(companyDto.getName())
                .email(companyDto.getEmail())
                .rating(companyDto.getRating())
                .address(addressConverter.convert(companyDto.getAddress()))
                .build();
    }

}
