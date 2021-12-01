package hr.trio.realestatetracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hr.trio.realestatetracker.advice.ExceptionAdvice;
import hr.trio.realestatetracker.dto.ApiResponse;
import hr.trio.realestatetracker.dto.FilterRealEstateDto;
import hr.trio.realestatetracker.dto.RealEstateDto;
import hr.trio.realestatetracker.service.RealEstateService;
import hr.trio.realestatetracker.support.RealEstateTrackerMvcTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@RealEstateTrackerMvcTest(RealEstateController.class)
@ImportAutoConfiguration(ExceptionAdvice.class)
class RealEstateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RealEstateService realEstateService;

    @Test
    void shouldGetAllRealEstateAndReturnOk() throws Exception {
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).build();
        final FilterRealEstateDto filterRealEstateDto = FilterRealEstateDto.builder().build();

        when(realEstateService.getAllRealEstate(0, 1, filterRealEstateDto)).thenReturn(new PageImpl<>(List.of(realEstateDto)));

        mvc.perform(post("/api/real-estate/all-real-estate")
                .param("page", "0")
                .param("size", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(filterRealEstateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(new ApiResponse(new PageImpl<>(List.of(realEstateDto))))))
                .andDo(print());
    }

    @Test
    void shouldGetRealEstateByIdAndReturnOk() throws Exception {
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).build();

        when(realEstateService.getRealEstateById(1L)).thenReturn(realEstateDto);

        mvc.perform(get("/api/real-estate/{realEstateId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(new ApiResponse(realEstateDto))))
                .andDo(print());
    }

    @Test
    void shouldUpdateRealEstateAndReturnOk() throws Exception {
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).name("tst").build();
        final RealEstateDto updated = RealEstateDto.builder().id(1L).name("test").build();

        when(realEstateService.updateRealEstate(realEstateDto)).thenReturn(updated);

        mvc.perform(put("/api/real-estate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(realEstateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(new ApiResponse(updated))))
                .andDo(print());
    }

    @Test
    void shouldUpdateRealEstateRatingAndReturnOk() throws Exception {
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).name("test").rating(5.0).build();

        when(realEstateService.updateRealEstateRating(1L, 5.0)).thenReturn(realEstateDto);

        mvc.perform(patch("/api/real-estate/rating/{realEstateId}/{rating}", 1L, 5.0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(realEstateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(new ApiResponse(realEstateDto))))
                .andDo(print());
    }

    @Test
    void shouldCreateRealEstateAndReturnCreated() throws Exception {
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).name("tst").build();

        when(realEstateService.createRealEstate(realEstateDto)).thenReturn(realEstateDto);

        mvc.perform(post("/api/real-estate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(realEstateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(new ApiResponse(realEstateDto))))
                .andDo(print());
    }

    @Test
    void shouldDeleteRealEstateAndReturnNoContent() throws Exception {
        mvc.perform(delete("/api/real-estate/{realEstateId}", 1L))
                .andExpect(status().isNoContent())
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