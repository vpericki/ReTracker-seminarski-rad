package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoleConverter implements Converter<Role, RoleDto> {

    @Override
    public RoleDto convert(final Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role convert(final RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }

}
