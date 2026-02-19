package io.github.kacperst.drivehub.modules.user.mapper;

import io.github.kacperst.drivehub.modules.user.dto.RegisterRequest;
import io.github.kacperst.drivehub.modules.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
    public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterRequest request);
}
