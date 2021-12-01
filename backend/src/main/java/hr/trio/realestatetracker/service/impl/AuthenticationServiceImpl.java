package hr.trio.realestatetracker.service.impl;

import hr.trio.realestatetracker.converter.RoleConverter;
import hr.trio.realestatetracker.converter.UserConverter;
import hr.trio.realestatetracker.dto.LoginDto;
import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.exception.RegistrationException;
import hr.trio.realestatetracker.jwt.JwtResponse;
import hr.trio.realestatetracker.jwt.JwtTokenBuilder;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.model.codebook.Roles;
import hr.trio.realestatetracker.repository.RoleRepository;
import hr.trio.realestatetracker.repository.UserRepository;
import hr.trio.realestatetracker.service.AuthenticationService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserConverter userConverter;

    private final RoleConverter roleConverter;

    @Override
    public JwtResponse login(final LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials", e);
        }

        User user = userRepository.findByUsername(loginDto.getUsername());

        return new JwtResponse(JwtTokenBuilder.generateToken(user));
    }

    @Override
    @Transactional
    public UserDto register(final RegistrationDto registrationDto) {
        checkValidity(registrationDto);

        registrationDto.setPassword(new BCryptPasswordEncoder().encode(registrationDto.getPassword()));
        registrationDto.setRoleDto(getUserRole());

        return userConverter.convert(userRepository.save(userConverter.convert(registrationDto)));
    }

    private RoleDto getUserRole() {
        return RoleDto.builder().id(Roles.ROLE_USER.getId()).name(Roles.ROLE_USER.getName()).build();
    }

    private void checkValidity(final RegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RegistrationException("Email address is already in use. Please use a different address.");
        }

        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RegistrationException("Username already exists. Please use a different username.");
        }
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(roleConverter::convert).collect(Collectors.toList());
    }

}
