package dev.fernando.user_service.mapper;

import org.mapstruct.Mapper;

import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.UpdateUserDto;
import dev.fernando.user_service.dto.ViewUserDto;
import dev.fernando.user_service.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserDto userRequest);
    User toEntity(UpdateUserDto updateUserDto);
    User toEntity(String userJson);
    ViewUserDto toDto(User user);
}
