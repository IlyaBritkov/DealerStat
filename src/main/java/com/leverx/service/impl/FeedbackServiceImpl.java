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
import com.leverx.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    private final GameRepository gameRepository;

    private final UserService userService;
    private final UserRepository userRepository;

    private final FeedbackMapper feedbackMapper;

    @Override
    public List<FeedbackDTO.Response.Public> findAllApprovedFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAllByApprovedIsTrue();

        return feedbackList.stream()
                .map(feedbackMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<FeedbackDTO.Response.Public> findApprovedFeedbackById(Integer id) throws NoSuchEntityException {
        Feedback feedback = getIfApprovedFeedbackByIdExists(id);

        return Optional.of(feedbackMapper.toDto(feedback));
    }

    @Override
    public List<FeedbackDTO.Response.Public> findAllNotApprovedFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAllByApprovedIsNull();

        return feedbackList.stream()
                .map(feedbackMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<FeedbackDTO.Response.Public> findNotApprovedFeedbackById(Integer id) throws NoSuchEntityException {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isEmpty() || feedbackOptional.get().getApproved() != null) {
            throw new NoSuchEntityException(String.format("There is no unapproved Feedback with ID = %d", id));
        }
        return Optional.of(feedbackMapper.toDto(feedbackOptional.get()));
    }

    @Override
    public List<FeedbackDTO.Response.Public> findAllApprovedByUserId(Integer traderId) throws NoSuchEntityException {
        userService.findApprovedTraderById(traderId); // throw exception if doest exist
        List<Feedback> feedbackList = feedbackRepository.findAllByApprovedIsTrueAndTraderId(traderId);
        return feedbackList.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FeedbackDTO.Response.Public> findApprovedByIdAndUserId(Integer feedbackId, Integer traderId) throws NoSuchEntityException {
        userService.findApprovedTraderById(traderId); // throw exception if doest exist
        Optional<Feedback> feedbackOptional = feedbackRepository.findByIdAndTraderId(feedbackId, traderId);
        if (feedbackOptional.isEmpty()
                || feedbackOptional.get().getApproved() == null
                || !feedbackOptional.get().getApproved()) {
            throw new NoSuchEntityException(String.format("There is no approved Feedback with ID = %d", feedbackId));
        }

        return Optional.of(feedbackMapper.toDto(feedbackOptional.get()));
    }

    @Override
    public FeedbackDTO.Response.Public save(FeedbackDTO.Request.Create feedbackDtoRequest) throws NoSuchEntityException {
        Integer traderId = feedbackDtoRequest.getTraderId();
        Integer gameId = feedbackDtoRequest.getGameId();

        Optional<User> optionalTrader = userRepository.findByIdAndApprovedTrue(traderId);

        if (optionalTrader.isEmpty() ||
                optionalTrader.get().getRole() != UserRole.TRADER) {
            throw new NoSuchEntityException(String.format("There is no approved Trader with ID = %d", traderId));
        }

        User trader = optionalTrader.get();

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no Game with ID = %d", gameId)));

        Feedback newFeedback = feedbackMapper.toEntity(feedbackDtoRequest);
        newFeedback.setTrader(trader);
        newFeedback.setGame(game);
        feedbackRepository.save(newFeedback);
        return feedbackMapper.toDto(newFeedback);
    }

    @Override
    public FeedbackDTO.Response.Public approve(Integer id, FeedbackDTO.Request.Approve feedbackDtoRequest) throws NoSuchEntityException {
        Feedback persistedFeedback = feedbackRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(String.format("There is no approved Feedback with ID = %d", id)));
        feedbackMapper.updateEntity(feedbackDtoRequest, persistedFeedback);

        feedbackRepository.save(persistedFeedback);

        return feedbackMapper.toDto(persistedFeedback);
    }

    @Override
    public void deleteById(Integer id) throws NoSuchEntityException {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no Feedback with ID = %d", id)));

        Game game = feedback.getGame();
        game.removeFeedback(feedback);
        feedback.setGame(null);
        feedback.setTrader(null);
        feedbackRepository.delete(feedback);
    }

    private Feedback getIfApprovedFeedbackByIdExists(Integer id) throws NoSuchEntityException {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);
        if (optionalFeedback.isEmpty() ||
                optionalFeedback.get().getApproved() == null ||
                !optionalFeedback.get().getApproved()) {
            throw new NoSuchEntityException(String.format("There is no Feedback with ID = %d", id));
        }
        return optionalFeedback.get();
    }
}
