package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.repository.UserRepository;
import hr.trio.realestatetracker.service.impl.CurrentUserServiceImpl;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentUserServiceTest {

    @InjectMocks
    private CurrentUserServiceImpl currentUserService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldGetLoggedInRole() {
        final Role admin = new Role(2L, "ROLE_ADMIN");

        setAuthenticationForRole(admin);

        assertThat(currentUserService.getLoggedInUserRole()).isEqualTo(admin);
    }

    @Test
    public void shouldGetLoggedInUser() {
        final Role admin = new Role(2L, "ROLE_ADMIN");
        final User user = User.builder().id(1L).firstName("David").lastName("Pilipovic").username("david.pilipovic").role(admin)
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("david.pilipovic@kaiba.corp").build();
        setAuthenticationForRole(admin);

        when(userRepository.findByUsername("david.pilipovic")).thenReturn(user);

        assertThat(currentUserService.getLoggedInUser()).isEqualTo(user);
    }

    @Test
    public void shouldGetLoggedInUserUsername() {
        final Role admin = new Role(2L, "ROLE_ADMIN");
        setAuthenticationForRole(admin);

        assertThat(currentUserService.getLoggedInUserUsername()).isEqualTo("david.pilipovic");
    }

    @Test
    public void shouldGetTrueWhenCheckingIsAdmin() {
        final Role admin = new Role(2L, "ROLE_ADMIN");
        final User user = User.builder().id(1L).firstName("David").lastName("Pilipovic").username("david.pilipovic").role(admin)
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("david.pilipovic@kaiba.corp").build();
        setAuthenticationForRole(admin);

        when(userRepository.findByUsername("david.pilipovic")).thenReturn(user);

        assertThat(currentUserService.isLoggedInUserAdmin()).isEqualTo(true);
    }

    @Test
    public void shouldGetFalseWhenCheckingIsAdmin() {
        final Role roleUser = new Role(1L, "ROLE_USER");

        setAuthenticationForRole(roleUser);

        assertThat(currentUserService.isLoggedInUserAdmin()).isEqualTo(false);
    }

    private void setAuthenticationForRole(Role userRole) {
        final User user = User.builder().id(1L).firstName("David").lastName("Pilipovic").username("david.pilipovic").role(userRole)
                .dateOfBirth(LocalDate.of(1998, 1, 1)).email("david.pilipovic@kaiba.corp").build();

        final Authentication authentication = mock(Authentication.class);
        final SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getName()).thenReturn("david.pilipovic");
        when(userRepository.findByUsername(authentication.getName())).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

}
