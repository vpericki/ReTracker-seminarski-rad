package hr.trio.realestatetracker.controller;

import hr.trio.realestatetracker.dto.ApiResponse;
import hr.trio.realestatetracker.dto.LoginDto;
import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody final LoginDto loginDto) {
        return new ResponseEntity<>(new ApiResponse(authenticationService.login(loginDto)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody final RegistrationDto registrationDto) {
        return new ResponseEntity<>(new ApiResponse(authenticationService.register(registrationDto)), HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse> getAllRoles() {
        return new ResponseEntity<>(new ApiResponse(authenticationService.getAllRoles()), HttpStatus.OK);
    }

}
