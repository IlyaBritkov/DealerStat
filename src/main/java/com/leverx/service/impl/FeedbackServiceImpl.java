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
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isEmpty()) {
            throw new NoSuchEntityException(String.format("There is no Feedback with ID = %d", id));
        } else {
            return Optional.of(feedbackMapper.toDto(feedbackOptional.get()));
        }
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

        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            throw new NoSuchEntityException(String.format("There is no Game with ID = %d", traderId));
        }

        Game game = optionalGame.get();

        Feedback newFeedback = feedbackMapper.toEntity(feedbackDtoRequest);
        newFeedback.setTrader(trader);
        newFeedback.setGame(game);
        feedbackRepository.save(newFeedback);
        return feedbackMapper.toDto(newFeedback);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public FeedbackDTO.Response.Public update(FeedbackDTO.Request.Update feedbackDtoRequest) throws NoSuchEntityException {
        if (feedbackRepository.existsById(feedbackDtoRequest.getId())) {

            Feedback persistedFeedback = feedbackRepository.findById(feedbackDtoRequest.getId()).get();
            feedbackMapper.updateEntity(feedbackDtoRequest, persistedFeedback);

            feedbackRepository.save(persistedFeedback);

            return feedbackMapper.toDto(persistedFeedback);
        } else {
            throw new NoSuchEntityException(String.format("There is no Feedback with id = %d to update", feedbackDtoRequest.getId()));
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    public void deleteById(Integer id) throws NoSuchEntityException {
        if (feedbackRepository.existsById(id)) {
            Feedback feedback = feedbackRepository.findById(id).get();
            Game game = feedback.getGame();
            game.removeFeedback(feedback);
            feedback.setGame(null);
            feedback.setTrader(null);
            feedbackRepository.delete(feedback);
        } else {
            throw new NoSuchEntityException(String.format("There is no Feedback with ID = %d", id));
        }
    }
}
