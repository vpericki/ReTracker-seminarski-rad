package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.converter.UserConverter;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.repository.UserRepository;
import hr.trio.realestatetracker.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private CurrentUserService currentUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @Test
    public void shouldGetUserById() {
        final User user = User.builder().id(1L).username("test").firstName("test").lastName("test").dateOfBirth(getDateOfBirth()).build();
        final UserDto userDto = UserDto.builder().id(1L).username("test").firstName("test").lastName("test").dateOfBirth(getDateOfBirth()).build();

        when(currentUserService.isLoggedIn()).thenReturn(true);
        when(currentUserService.getLoggedInUser()).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userConverter.convert(user)).thenReturn(userDto);

        assertThat(userService.getUserById(1L)).isEqualTo(userDto);
    }

    @Test
    public void shouldGetUserWithoutPersonalDataById() {
        final User user = User.builder().id(1L).username("test").build();
        final User user2 = User.builder().id(2L).username("test2").firstName("test2").lastName("test2").dateOfBirth(getDateOfBirth()).build();
        final UserDto userDto = UserDto.builder().username("test2").build();

        when(currentUserService.isLoggedIn()).thenReturn(true);
        when(currentUserService.getLoggedInUser()).thenReturn(user);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userConverter.convert(user2)).thenReturn(userDto);

        assertThat(userService.getUserById(2L)).isEqualTo(userDto);
    }

    @Test
    public void shouldFailToGetUserByNonexistentId() {
        final User user = User.builder().id(1L).username("test").firstName("test").lastName("test").dateOfBirth(getDateOfBirth()).build();

        when(currentUserService.isLoggedIn()).thenReturn(true);
        when(currentUserService.getLoggedInUser()).thenReturn(user);

        try {
            userService.getUserById(1L);
            failBecauseExceptionWasNotThrown(NotFoundException.class);
        } catch (final NotFoundException e) {
            assertThat(e).hasMessage("User with ID 1 not found.");
        }
    }

    private LocalDate getDateOfBirth() {
        return LocalDate.of(1998, 1, 1);
    }

}
