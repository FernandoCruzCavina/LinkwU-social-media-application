package dev.fernando.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.UpdateUserDto;
import dev.fernando.user_service.dto.ViewUserDto;
import dev.fernando.user_service.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(CreateUserDto userRequest);
    User toEntity(UpdateUserDto updateUserDto);
    ViewUserDto toDto(User user);
}
