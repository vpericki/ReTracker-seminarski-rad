package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.dto.LoginDto;
import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.jwt.JwtResponse;
import java.util.List;

public interface AuthenticationService {

    JwtResponse login(LoginDto loginDto);

    UserDto register(RegistrationDto registrationDto);

    List<RoleDto> getAllRoles();

}
