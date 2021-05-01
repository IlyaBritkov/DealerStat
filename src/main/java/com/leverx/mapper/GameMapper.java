package com.leverx.mapper;

import com.leverx.dto.GameDTO;
import com.leverx.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameDTO.Response.Public toDto(Game game);
}
