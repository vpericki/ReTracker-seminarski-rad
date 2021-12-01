package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RoleConverterTest {

    @InjectMocks
    private RoleConverter roleConverter;

    @Test
    public void shouldConvertRoleToRoleDto() {
        final Role role = Role.builder().id(2L).name("ROLE_ADMIN").build();
        final RoleDto expectedDto = RoleDto.builder().id(2L).name("ROLE_ADMIN").build();

        assertThat(roleConverter.convert(role)).isEqualTo(expectedDto);
    }

    @Test
    public void shouldConvertRoleDtoToRole() {
        final RoleDto roleDto = RoleDto.builder().id(2L).name("ROLE_ADMIN").build();
        final Role expectedRole = Role.builder().id(2L).name("ROLE_ADMIN").build();

        assertThat(roleConverter.convert(roleDto)).isEqualTo(expectedRole);
    }

}
