package hr.trio.realestatetracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hr.trio.realestatetracker.advice.ExceptionAdvice;
import hr.trio.realestatetracker.dto.ApiResponse;
import hr.trio.realestatetracker.dto.LoginDto;
import hr.trio.realestatetracker.dto.RegistrationDto;
import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.jwt.JwtResponse;
import hr.trio.realestatetracker.service.AuthenticationService;
import hr.trio.realestatetracker.support.RealEstateTrackerMvcTest;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@RealEstateTrackerMvcTest(AuthenticationController.class)
@ImportAutoConfiguration(ExceptionAdvice.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void shouldLoginAUserAndReturnToken() throws Exception {
        final LoginDto loginDto = new LoginDto("admin", "pass");
        final JwtResponse jwtResponse = new JwtResponse("token");

        when(authenticationService.login(loginDto)).thenReturn(jwtResponse);

        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(new ApiResponse(jwtResponse))))
                .andDo(print());
    }

    @Test
    public void shouldRegisterAUserAndReturnRegisterDto() throws Exception {
        final RoleDto roleDto = new RoleDto(2L, "ROLE_ADMIN");
        final RegistrationDto registrationDto = new RegistrationDto("admin", "pass", roleDto);
        final UserDto userDto = UserDto.builder().username("admin").roleDto(roleDto).build();

        when(authenticationService.register(registrationDto)).thenReturn(userDto);

        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registrationDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(new ApiResponse(userDto))))
                .andDo(print());
    }

    @Test
    public void shouldGetAllRoles() throws Exception {
        final List<RoleDto> expectedRoleDtos = List.of(new RoleDto(1L, "ROLE_USER"), new RoleDto(2L, "ROLE_ADMIN"));

        mvc.perform(get("/api/auth/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedRoleDtos)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}