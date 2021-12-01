package hr.trio.realestatetracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hr.trio.realestatetracker.advice.ExceptionAdvice;
import hr.trio.realestatetracker.dto.ApiResponse;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.service.UserService;
import hr.trio.realestatetracker.support.RealEstateTrackerMvcTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@RealEstateTrackerMvcTest(UserController.class)
@ImportAutoConfiguration(ExceptionAdvice.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserById() throws Exception {
        final UserDto userDto = UserDto.builder().id(1L).username("test").build();

        when(userService.getUserById(1L)).thenReturn(userDto);

        mvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(new ApiResponse(userDto))))
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