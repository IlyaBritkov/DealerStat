package com.leverx.service.impl;

import com.leverx.dto.GameDTO;
import com.leverx.entity.Game;
import com.leverx.entity.User;
import com.leverx.exception_handling.exception.GameCreationException;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.mapper.GameMapper;
import com.leverx.repository.GameRepository;
import com.leverx.repository.UserRepository;
import com.leverx.service.GameService;
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
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private final GameMapper gameMapper;

    @Override
    public List<GameDTO.Response.Public> findAll() {
        List<Game> gameList = gameRepository.findAll();

        return gameList.stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GameDTO.Response.Public> findById(Integer id) throws NoSuchEntityException {
        Game game = getIfGameByIdExists(id);

        return Optional.of(gameMapper.toDto(game));
    }

    @Override
    public GameDTO.Response.Public save(GameDTO.Request.Create gameDtoRequest) throws GameCreationException {
        User trader = userRepository.findById(gameDtoRequest.getTraderId()).orElseThrow(() ->
                new NoSuchEntityException(String.format("There is no trader with ID %d", gameDtoRequest.getTraderId())));

        Optional<Game> optionalGame = gameRepository.findByNameAndDescription(
                gameDtoRequest.getName(), gameDtoRequest.getDescription());

        Game game = optionalGame.orElseGet(() -> gameMapper.toEntity(gameDtoRequest));

        if (trader.getGames().stream().anyMatch(traderGame -> traderGame.getId().equals(game.getId()))) {
            throw new GameCreationException(String.format("Trader with ID=%d already has game with ID=%d", trader.getId(), game.getId()));
        }

        trader.addGame(game);
        userRepository.save(trader);

        return gameMapper.toDto(game);
    }

    @Override
    public GameDTO.Response.Public update(Integer id, GameDTO.Request.Update gameDtoRequest) throws NoSuchEntityException {
        Game persistedGame = getIfGameByIdExists(id);

        gameMapper.updateEntity(gameDtoRequest, persistedGame);
        gameRepository.save(persistedGame);

        return gameMapper.toDto(persistedGame);
    }

    @Override
    public void deleteById(Integer id) throws NoSuchEntityException {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
        } else {
            throw new NoSuchEntityException(String.format("There is no Game with ID = %d", id));
        }
    }

    private Game getIfGameByIdExists(Integer id) throws NoSuchEntityException {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return optionalGame.orElseThrow(() ->
                new NoSuchEntityException(String.format("There is no Game with id = %d", id)));
    }

}
