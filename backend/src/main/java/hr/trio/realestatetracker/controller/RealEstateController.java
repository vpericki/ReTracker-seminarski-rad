package hr.trio.realestatetracker.controller;

import hr.trio.realestatetracker.dto.ApiResponse;
import hr.trio.realestatetracker.dto.FilterRealEstateDto;
import hr.trio.realestatetracker.dto.RealEstateDto;
import hr.trio.realestatetracker.service.RealEstateService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/real-estate")
@CrossOrigin(origins = "http://localhost:3000")
public class RealEstateController {

    private final RealEstateService realEstateService;

    @PostMapping("/all-real-estate")
    public ResponseEntity<ApiResponse> getAllRealEstate(@Param("page") final int page,
                                                        @Param("size") final int size,
                                                        @RequestBody(required = false) final FilterRealEstateDto filterRealEstateDto) {
        return new ResponseEntity<>(new ApiResponse(realEstateService.getAllRealEstate(page, size, filterRealEstateDto)), HttpStatus.OK);
    }

    @GetMapping("/{realEstateId}")
    public ResponseEntity<ApiResponse> getRealEstateById(@PathVariable final Long realEstateId) {
        return new ResponseEntity<>(new ApiResponse(realEstateService.getRealEstateById(realEstateId)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateRealEstate(@Valid @RequestBody final RealEstateDto realEstateDto) {
        return new ResponseEntity<>(new ApiResponse(realEstateService.updateRealEstate(realEstateDto)), HttpStatus.OK);
    }

    @PatchMapping("/rating/{realEstateId}/{rating}")
    public ResponseEntity<ApiResponse> updateRealEstateRating(@PathVariable final Long realEstateId, @PathVariable final Double rating) {
        return new ResponseEntity<>(new ApiResponse(realEstateService.updateRealEstateRating(realEstateId, rating)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createRealEstate(@Valid @RequestBody final RealEstateDto realEstateDto) {
        return new ResponseEntity<>(new ApiResponse(realEstateService.createRealEstate(realEstateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{realEstateId}")
    public ResponseEntity<ApiResponse> deleteRealEstate(@PathVariable final Long realEstateId) {
        realEstateService.deleteRealEstateById(realEstateId);
        return ResponseEntity.noContent().build();
    }

}
