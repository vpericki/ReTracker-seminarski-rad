package hr.trio.realestatetracker.service.impl;

import hr.trio.realestatetracker.converter.UserConverter;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.repository.UserRepository;
import hr.trio.realestatetracker.service.CurrentUserService;
import hr.trio.realestatetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final UserConverter userConverter;

    @Override
    public UserDto getUserById(final Long userId) {
        if (currentUserService.isLoggedIn() && currentUserService.getLoggedInUser().getId().equals(userId)) {
            return getUserDto(userId);
        }

        return removePrivateInfo(getUserDto(userId));
    }

    private UserDto getUserDto(final Long userId) {
        return userConverter.convert(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found.")));
    }

    private UserDto removePrivateInfo(final UserDto userDto) {
        userDto.setId(null);
        userDto.setFirstName(null);
        userDto.setLastName(null);
        userDto.setDateOfBirth(null);
        userDto.setRoleDto(null);

        return userDto;
    }

}
