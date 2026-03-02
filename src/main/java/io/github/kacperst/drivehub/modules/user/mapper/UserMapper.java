package io.github.kacperst.drivehub.modules.user.mapper;

import io.github.kacperst.drivehub.modules.user.dto.UserRequest;
import io.github.kacperst.drivehub.modules.user.dto.UserResponse;
import io.github.kacperst.drivehub.modules.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "roles",
            expression = "java(user.getRoles().stream().map(role -> role.getName().name()).collect(java.util.stream.Collectors.toSet()))")
    UserResponse toResponse(User user);
}
