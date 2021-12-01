package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.dto.UserDto;

public interface UserService {

    UserDto getUserById(Long userId);

}
