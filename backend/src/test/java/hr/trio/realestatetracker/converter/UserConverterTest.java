package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.CompanyDto;
import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.model.Company;
import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

    @InjectMocks
    private UserConverter userConverter;

    @Mock
    private RoleConverter roleConverter;

    @Mock
    private CompanyConverter companyConverter;

    @Test
    public void shouldConvertUserToUserDto() {
        final Role role = new Role(2L, "ROLE_ADMIN");
        final RoleDto roleDto = new RoleDto(2L, "ROLE_ADMIN");
        final Company company = Company.builder().id(1L).name("company").build();
        final CompanyDto companyDto = CompanyDto.builder().id(1L).name("company").build();

        final User user = User.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com").role(role).company(company).build();

        final UserDto ExpectedDto = UserDto.builder().id(1L).username("admin").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com").roleDto(roleDto).companyDto(companyDto).build();

        when(roleConverter.convert(role)).thenReturn(roleDto);
        when(companyConverter.convert(company)).thenReturn(companyDto);

        assertThat(userConverter.convert(user)).isEqualTo(ExpectedDto);
    }

    @Test
    public void shouldConvertRegisterDtoToUser() {
        final Role role = new Role(2L, "ROLE_ADMIN");
        final RoleDto roleDto = new RoleDto(2L, "ROLE_ADMIN");
        final Company company = Company.builder().id(1L).name("company").build();
        final CompanyDto companyDto = CompanyDto.builder().id(1L).name("company").build();

        final User expectedUser = User.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com").role(role).company(company).build();

        final RegistrationDto registrationDto = RegistrationDto.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com").roleDto(roleDto).companyDto(companyDto).build();

        when(roleConverter.convert(roleDto)).thenReturn(role);
        when(companyConverter.convert(companyDto)).thenReturn(company);

        assertThat(userConverter.convert(registrationDto)).isEqualTo(expectedUser);
    }

}
