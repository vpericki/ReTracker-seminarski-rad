package hr.trio.realestatetracker.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private Double rating;
    private RoleDto roleDto;
    private CompanyDto companyDto;

    public RegistrationDto(String username, String password, RoleDto roleDto) {
        this.username = username;
        this.roleDto = roleDto;
    }

}

