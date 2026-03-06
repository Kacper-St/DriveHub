package io.github.kacperst.drivehub.modules.user.mapper;

import io.github.kacperst.drivehub.modules.user.dto.UserRequest;
import io.github.kacperst.drivehub.modules.user.dto.UserResponse;
import io.github.kacperst.drivehub.modules.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> users);

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "forcePasswordChange",  ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authProvider", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateUser(UserRequest userRequest, @MappingTarget User user);

    default Set<String> mapRoles(User user) {
        if (user.getRoles() == null) return null;
        return user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(java.util.stream.Collectors.toSet());
    }
}
