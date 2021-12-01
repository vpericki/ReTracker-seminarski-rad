package hr.trio.realestatetracker.service;


import hr.trio.realestatetracker.converter.RoleConverter;
import hr.trio.realestatetracker.converter.UserConverter;
import hr.trio.realestatetracker.dto.LoginDto;
import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.exception.RegistrationException;
import hr.trio.realestatetracker.jwt.JwtResponse;
import hr.trio.realestatetracker.jwt.JwtTokenBuilder;
import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.repository.RoleRepository;
import hr.trio.realestatetracker.repository.UserRepository;
import hr.trio.realestatetracker.service.impl.AuthenticationServiceImpl;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @InjectMocks
    private JwtTokenBuilder jwtTokenBuilder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserConverter userConverter;

    @Mock
    private RoleConverter roleConverter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void shouldLoginAUserAndReturnJWT() {
        final User user = User.builder().username("admin").password("pass").role(new Role(2L, "ROLE_ADMIN")).build();
        final LoginDto loginDto = LoginDto.builder().username("admin").password("pass").build();
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        jwtTokenBuilder.setSecret("kozadnjigazinajmanjekospiceosjeti");

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(user);

        final JwtResponse jwtResponse = authenticationService.login(loginDto);

        assertThat(jwtResponse).isNotNull();
    }

    @Test
    public void shouldRegisterUserAndReturnUserDto() {
        final User user = User.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com")
                .role(new Role(2L, "ROLE_ADMIN")).build();
        final RegistrationDto registrationDto = RegistrationDto.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com")
                .roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();
        final UserDto userDto = UserDto.builder().id(1L).username("admin").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com")
                .roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();

        when(userConverter.convert(registrationDto)).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(userDto);
        when(userRepository.save(user)).thenReturn(user);

        final UserDto registerDtoResponse = authenticationService.register(registrationDto);

        assertThat(registerDtoResponse).isEqualTo(userDto);
    }

    @Test
    public void shouldFailToRegisterUserWhenUsernameAlreadyExists() {
        final RegistrationDto registrationDto = RegistrationDto.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com")
                .roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();

        when(userRepository.existsByUsername(registrationDto.getUsername())).thenReturn(true);

        try {
            authenticationService.register(registrationDto);
            failBecauseExceptionWasNotThrown(RegistrationException.class);
        } catch (final RegistrationException e) {
            assertThat(e).hasMessage("Username already exists. Please use a different username.");
        }
    }

    @Test
    public void shouldFailToRegisterUserWhenEmailAlreadyInUse() {
        final RegistrationDto registrationDto = RegistrationDto.builder().id(1L).username("admin").password("pass").firstName("Admin").lastName("Admin")
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("admin.admin@email.com")
                .roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();

        when(userRepository.existsByEmail(registrationDto.getEmail())).thenReturn(true);

        try {
            authenticationService.register(registrationDto);
            failBecauseExceptionWasNotThrown(RegistrationException.class);
        } catch (final RegistrationException e) {
            assertThat(e).hasMessage("Email address is already in use. Please use a different address.");
        }
    }

    @Test
    public void shouldGetAllRoles() {
        final Role roleUser = new Role(1L, "ROLE_USER");
        final Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        final List<Role> roles = List.of(roleUser, roleAdmin);
        final RoleDto roleUserDto = new RoleDto(1L, "ROLE_USER");
        final RoleDto roleAdminDto = new RoleDto(2L, "ROLE_ADMIN");
        final List<RoleDto> expectedRoleDtos = List.of(roleUserDto, roleAdminDto);

        when(roleRepository.findAll()).thenReturn(roles);
        when(roleConverter.convert(roleUser)).thenReturn(roleUserDto);
        when(roleConverter.convert(roleAdmin)).thenReturn(roleAdminDto);

        final List<RoleDto> responseRoleDtos = authenticationService.getAllRoles();

        assertThat(responseRoleDtos).isEqualTo(expectedRoleDtos);
    }

}