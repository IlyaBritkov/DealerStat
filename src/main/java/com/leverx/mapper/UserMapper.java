package com.leverx.mapper;

import com.leverx.configuration.MapperConfiguration;
import com.leverx.dto.UserDTO;
import com.leverx.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

    @Mapping(source = "user.role", target = "role")
    UserDTO.Response.Public toDto(User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "games", ignore = true)
    User toEntity(UserDTO.Request.Create userDtoRequest);


    @Mapping(target = "role", ignore = true)
    @Mapping(target = "games", ignore = true)
    void updateEntity(UserDTO.Request.Update userDtoRequest, @MappingTarget User user);
}
