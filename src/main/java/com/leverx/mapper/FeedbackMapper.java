package com.leverx.mapper;

import com.leverx.dto.FeedbackDTO;
import com.leverx.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface FeedbackMapper {

    @Mapping(source = "feedback.rating", target = "rating")
    @Mapping(source = "feedback.trader.id", target = "traderId")
    @Mapping(source = "feedback.game.id", target = "gameId")
    FeedbackDTO.Response.Public toDto(Feedback feedback);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trader", ignore = true)
    @Mapping(target = "game", ignore = true)
    @Mapping(target = "approved", ignore = true)
    Feedback toEntity(FeedbackDTO.Request.Create feedbackDtoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "trader", ignore = true)
    @Mapping(target = "game", ignore = true)
    @Mapping(target = "rating", ignore = true)
    void updateEntity(FeedbackDTO.Request.Approve feedbackDtoRequest, @MappingTarget Feedback feedback);
}
