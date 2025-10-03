package ir.rayanovinmt.core.security.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    UserLoadDto toLoadDto(User user);
    UserResponseDto toResponseDto(User user, String token);
}

