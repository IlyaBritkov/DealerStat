package com.leverx.service.impl;

import com.leverx.dto.FeedbackDTO;
import com.leverx.entity.Feedback;
import com.leverx.entity.Game;
import com.leverx.entity.User;
import com.leverx.enums.UserRole;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.mapper.FeedbackMapper;
import com.leverx.repository.FeedbackRepository;
import com.leverx.repository.GameRepository;
import com.leverx.repository.UserRepository;
import com.leverx.service.FeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private final FeedbackMapper feedbackMapper;

    public List<FeedbackDTO.Response.Public> findAll() {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        return feedbackList.stream()
                .map(feedbackMapper::toDto).collect(Collectors.toList());
    }

    public Optional<FeedbackDTO.Response.Public> findById(Integer id) throws NoSuchEntityException {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no Feedback with ID = %d", id)));

        return Optional.of(feedbackMapper.toDto(feedback));

    }

    public FeedbackDTO.Response.Public save(FeedbackDTO.Request.Create feedbackDtoRequest) throws NoSuchEntityException {
        Integer traderId = feedbackDtoRequest.getTraderId();
        Integer gameId = feedbackDtoRequest.getGameId();

        Optional<User> optionalTrader = userRepository.findById(traderId);

        if (optionalTrader.isEmpty() ||
                optionalTrader.get().getRole() != UserRole.TRADER) {
            throw new NoSuchEntityException(String.format("There is no Trader with ID = %d", traderId));
        }

        User trader = optionalTrader.get();

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no Game with ID = %d", traderId)));

        Feedback newFeedback = feedbackMapper.toEntity(feedbackDtoRequest);
        newFeedback.setTrader(trader);
        newFeedback.setGame(game);
        feedbackRepository.save(newFeedback);
        return feedbackMapper.toDto(newFeedback);
    }

    @Override
    public FeedbackDTO.Response.Public update(FeedbackDTO.Request.Update feedbackDtoRequest) throws NoSuchEntityException {
        Feedback persistedFeedback = feedbackRepository.findById(feedbackDtoRequest.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no Feedback with id = %d to update", feedbackDtoRequest.getId())));
        feedbackMapper.updateEntity(feedbackDtoRequest, persistedFeedback);

        feedbackRepository.save(persistedFeedback);

        return feedbackMapper.toDto(persistedFeedback);
    }

    @Transactional
    public void deleteById(Integer id) throws NoSuchEntityException {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no Feedback with ID = %d", id)));

        Game game = feedback.getGame();
        game.removeFeedback(feedback);
        feedback.setGame(null);
        feedback.setTrader(null);
        feedbackRepository.delete(feedback);
    }
}
