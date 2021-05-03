package com.leverx.mapper;

import com.leverx.dto.GameDTO;
import com.leverx.entity.Game;
import org.mapstruct.*;

@Mapper(config = MapperConfiguration.class)
public interface GameMapper {
    GameDTO.Response.Public toDto(Game game);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "traders", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    Game toEntity(GameDTO.Request.Create gameDtoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "traders", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    void updateEntity(GameDTO.Request.Update gameDtoRequest, @MappingTarget Game game);
}
