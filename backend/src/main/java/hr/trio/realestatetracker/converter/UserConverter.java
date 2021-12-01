package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserConverter implements Converter<User, UserDto> {

    private final RoleConverter roleConverter;
    private final CompanyConverter companyConverter;

    @Override
    public UserDto convert(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .rating(user.getRating())
                .roleDto(roleConverter.convert(user.getRole()))
                .companyDto(user.getCompany() != null ? companyConverter.convert(user.getCompany()) : null)
                .build();
    }

    public User convert(final RegistrationDto registrationDto) {
        return User.builder()
                .id(registrationDto.getId())
                .username(registrationDto.getUsername())
                .password(registrationDto.getPassword())
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .dateOfBirth(registrationDto.getDateOfBirth())
                .rating(registrationDto.getRating())
                .role(roleConverter.convert(registrationDto.getRoleDto()))
                .company(registrationDto.getCompanyDto() != null ? companyConverter.convert(registrationDto.getCompanyDto()) : null)
                .build();
    }

    public User convert(final UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .dateOfBirth(userDto.getDateOfBirth())
                .rating(userDto.getRating())
                .role(roleConverter.convert(userDto.getRoleDto()))
                .company(userDto.getCompanyDto() != null ? companyConverter.convert(userDto.getCompanyDto()) : null)
                .build();
    }

}
