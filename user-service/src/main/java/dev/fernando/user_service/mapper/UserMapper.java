package dev.fernando.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.UpdateUserDto;
import dev.fernando.user_service.dto.ViewUserDto;
import dev.fernando.user_service.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "active", source = "isActive")
    User toEntity(CreateUserDto userRequest);
    @Mapping(target = "active", source = "isActive")
    User toEntity(UpdateUserDto updateUserDto);
    @Mapping(target = "isActive", source = "active")
    ViewUserDto toDto(User user);
}
