package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.converter.RealEstateConverter;
import hr.trio.realestatetracker.converter.UserConverter;
import hr.trio.realestatetracker.dto.AddressDto;
import hr.trio.realestatetracker.dto.FilterRealEstateDto;
import hr.trio.realestatetracker.dto.ImageDto;
import hr.trio.realestatetracker.dto.RealEstateDto;
import hr.trio.realestatetracker.dto.RealEstateTypeDto;
import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.dto.UserDto;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.exception.PermissionException;
import hr.trio.realestatetracker.model.Address;
import hr.trio.realestatetracker.model.Image;
import hr.trio.realestatetracker.model.RealEstate;
import hr.trio.realestatetracker.model.RealEstateRating;
import hr.trio.realestatetracker.model.RealEstateRatingId;
import hr.trio.realestatetracker.model.RealEstateType;
import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.repository.RealEstateRatingRepository;
import hr.trio.realestatetracker.repository.RealEstateRepository;
import hr.trio.realestatetracker.service.impl.RealEstateServiceImpl;
import hr.trio.realestatetracker.specification.RealEstateSpecification;
import hr.trio.realestatetracker.validator.RealEstateValidator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RealEstateServiceTest {

    @InjectMocks
    private RealEstateServiceImpl realEstateService;

    @Mock
    private CurrentUserService currentUserService;

    @Mock
    private RealEstateRepository realEstateRepository;

    @Mock
    private RealEstateRatingRepository realEstateRatingRepository;

    @Mock
    private RealEstateValidator realEstateValidator;

    @Mock
    private RealEstateConverter realEstateConverter;

    @Mock
    private UserConverter userConverter;

    @Test
    public void shouldGetAllRealEstate() {
        final RealEstate realEstate = RealEstate.builder().id(1L).build();
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).build();
        final int page = 0;
        final int size = 1;
        final Pageable pageable = PageRequest.of(page, size);

        when(realEstateRepository.findAll(any(RealEstateSpecification.class), eq(pageable))).thenReturn(new PageImpl<>(List.of(realEstate)));
        when(realEstateConverter.convert(realEstate)).thenReturn(realEstateDto);

        assertThat(realEstateService.getAllRealEstate(page, size, new FilterRealEstateDto())).isEqualTo(new PageImpl<>(List.of(realEstateDto)));
    }

    @Test
    public void shouldGetRealEstateById() {
        final RealEstate realEstate = RealEstate.builder().id(1L).build();
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).build();

        when(realEstateRepository.findById(1L)).thenReturn(Optional.of(realEstate));
        when(realEstateConverter.convert(realEstate)).thenReturn(realEstateDto);

        assertThat(realEstateService.getRealEstateById(1L)).isEqualTo(realEstateDto);
    }

    @Test
    public void shouldFailToGetRealEstateById() {
        when(realEstateRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            realEstateService.getRealEstateById(1L);
            failBecauseExceptionWasNotThrown(NotFoundException.class);
        } catch (final NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Real estate with ID 1 not found.");
        }
    }

    @Test
    public void shouldUpdateRealEstate() {
        final User user = User.builder().id(1L).username("dpilipovic").role(new Role(2L, "ROLE_ADMIN")).build();
        final UserDto userDto = UserDto.builder().id(1L).username("dpilipovic").roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();
        final Image image = Image.builder().id(1L).path("some/path").build();
        final ImageDto imageDto = ImageDto.builder().id(1L).path("some/path").build();
        final RealEstate realEstate = RealEstate.builder().id(1L).name("Test stan")
                .realEstateType(RealEstateType.builder().id(2L).type("APARTMENT").build()).address(Address.builder().build())
                .createdBy(user).baths(1).rooms(2).creationDate(LocalDateTime.now()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(image)).rating(5.0).build();
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).name("APARTMENT FOR RENT")
                .realEstateTypeDto(RealEstateTypeDto.builder().id(2L).type("APARTMENT").build()).address(AddressDto.builder().build())
                .createdBy(userDto).baths(1).rooms(2).creationDate(LocalDateTime.now()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(imageDto)).rating(5.0).build();
        final RealEstate updatedRealEstate = RealEstate.builder().id(1L).name("Test stan")
                .realEstateType(RealEstateType.builder().id(2L).type("APARTMENT FOR RENT").build()).address(Address.builder().build())
                .createdBy(user).baths(1).rooms(2).creationDate(LocalDateTime.now()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(image)).rating(5.0).updatedBy(user).updateDate(LocalDateTime.now()).build();

        when(realEstateRepository.existsById(1L)).thenReturn(true);
        when(realEstateConverter.convert(realEstateDto)).thenReturn(realEstate);
        when(realEstateRepository.save(any(RealEstate.class))).thenReturn(updatedRealEstate);
        when(realEstateConverter.convert(updatedRealEstate)).thenReturn(realEstateDto);

        assertThat(realEstateService.updateRealEstate(realEstateDto)).isEqualTo(realEstateDto);
    }

    @Test
    public void shouldUpdateRealEstateRating() {
        final User user = User.builder().id(1L).username("dpilipovic").role(new Role(2L, "ROLE_ADMIN")).build();
        final User user2 = User.builder().id(2L).username("dpilipovic2").role(new Role(1L, "ROLE_USER")).build();
        final UserDto userDto = UserDto.builder().id(1L).username("dpilipovic").roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();
        final Image image = Image.builder().id(1L).path("some/path").build();
        final ImageDto imageDto = ImageDto.builder().id(1L).path("some/path").build();
        final RealEstate realEstate = RealEstate.builder().id(1L).name("APARTMENT FOR RENT")
                .realEstateType(RealEstateType.builder().id(2L).type("APARTMENT").build()).address(Address.builder().build())
                .createdBy(user).baths(1).rooms(2).creationDate(getLocalDateTime()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(image)).rating(5.0).build();
        final RealEstateRating realEstateRating1 = new RealEstateRating(new RealEstateRatingId(realEstate, user2), 5.0);
        final RealEstateRating realEstateRating2 = new RealEstateRating(new RealEstateRatingId(realEstate, user), 4.0);
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).name("APARTMENT FOR RENT")
                .realEstateTypeDto(RealEstateTypeDto.builder().id(2L).type("APARTMENT").build()).address(AddressDto.builder().build())
                .createdBy(userDto).baths(1).rooms(2).creationDate(getLocalDateTime()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(imageDto)).rating(4.5).build();
        final RealEstate updatedRealEstate = RealEstate.builder().id(1L).name("APARTMENT FOR RENT")
                .realEstateType(RealEstateType.builder().id(2L).type("APARTMENT").build()).address(Address.builder().build())
                .createdBy(user).baths(1).rooms(2).creationDate(getLocalDateTime()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(image)).rating(4.5).build();

        when(realEstateRepository.findById(1L)).thenReturn(Optional.ofNullable(realEstate));
        when(realEstateRatingRepository.save(any(RealEstateRating.class))).thenReturn(realEstateRating2);
        when(realEstateRatingRepository.findAllByRealEstateRatingId_RealEstate(realEstate)).thenReturn(List.of(realEstateRating1, realEstateRating2));
        when(realEstateRepository.save(any(RealEstate.class))).thenReturn(updatedRealEstate);
        when(realEstateConverter.convert(updatedRealEstate)).thenReturn(realEstateDto);

        assertThat(realEstateService.updateRealEstateRating(realEstate.getId(), 4.0)).isEqualTo(realEstateDto);
    }

    @Test
    public void shouldCreateRealEstate() {
        final User user = User.builder().id(1L).username("dpilipovic").role(new Role(2L, "ROLE_ADMIN")).build();
        final UserDto userDto = UserDto.builder().id(1L).username("dpilipovic").roleDto(new RoleDto(2L, "ROLE_ADMIN")).build();
        final Image image = Image.builder().id(1L).path("some/path").build();
        final ImageDto imageDto = ImageDto.builder().id(1L).path("some/path").build();
        final RealEstate realEstate = RealEstate.builder().id(1L).name("Test stan")
                .realEstateType(RealEstateType.builder().id(2L).type("APARTMENT FOR RENT").build()).address(Address.builder().build())
                .createdBy(user).baths(1).rooms(2).creationDate(LocalDateTime.now()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(image)).rating(5.0).build();
        final RealEstateDto realEstateDto = RealEstateDto.builder().id(1L).name("Test stan")
                .realEstateTypeDto(RealEstateTypeDto.builder().id(2L).type("APARTMENT FOR RENT").build()).address(AddressDto.builder().build())
                .createdBy(userDto).baths(1).rooms(2).creationDate(LocalDateTime.now()).forRent(true).rentPrice(BigDecimal.valueOf(3000)).quadrature(80)
                .images(Collections.singleton(imageDto)).rating(5.0).build();

        when(currentUserService.getLoggedInUser()).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(userDto);
        when(realEstateConverter.convert(realEstateDto)).thenReturn(realEstate);
        when(realEstateRepository.save(realEstate)).thenReturn(realEstate);
        when(realEstateConverter.convert(realEstate)).thenReturn(realEstateDto);

        assertThat(realEstateService.createRealEstate(realEstateDto)).isEqualTo(realEstateDto);
    }

    @Test
    public void shouldDeleteRealEstateByIdWithUserThatCreatedIt() {
        final User currentUser = User.builder().build();
        final RealEstate realEstate = RealEstate.builder().id(1L).createdBy(currentUser).build();

        when(realEstateValidator.canDeleteRealEstate(realEstate.getId())).thenReturn(true);

        realEstateService.deleteRealEstateById(1L);

        verify(realEstateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldFailToDeleteRealEstateByIdBecauseDifferentOwner() {
        try {
            realEstateService.deleteRealEstateById(1L);
            failBecauseExceptionWasNotThrown(PermissionException.class);
        } catch (final PermissionException e) {
            assertThat(e.getMessage()).isEqualTo("Can not delete real estate with a different owner.");
        }
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(LocalDate.of(2021, 10, 24), LocalTime.NOON);
    }
}