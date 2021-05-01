package com.leverx.service.impl;

import com.leverx.dto.GameDTO;
import com.leverx.entity.Game;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.mapper.GameMapper;
import com.leverx.repository.GameRepository;
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
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            throw new NoSuchEntityException(String.format("There is no Game with ID = %d", id));
        } else {
            return Optional.of(gameMapper.toDto(gameOptional.get()));
        }
    }

    @Override
    public GameDTO.Response.Public save(GameDTO.Request.Create gameDtoRequest) {
        Game newGame = gameMapper.toEntity(gameDtoRequest);

        Game savedGame = gameRepository.save(newGame);

        return gameMapper.toDto(savedGame);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public GameDTO.Response.Public update(GameDTO.Request.Update gameDtoRequest) throws NoSuchEntityException{
        if (gameRepository.existsById(gameDtoRequest.getId())) {
            Game persistedGame = gameRepository.findById(gameDtoRequest.getId()).get();

            gameMapper.updateEntity(gameDtoRequest, persistedGame);
            gameRepository.save(persistedGame);

            return gameMapper.toDto(persistedGame);
        } else {
            throw new NoSuchEntityException(String.format("There is no Game with id = %d to update", gameDtoRequest.getId()));
        }
    }

    @Override
    public void deleteById(Integer id) throws NoSuchEntityException {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
        } else {
            throw new NoSuchEntityException(String.format("There is no Game with ID = %d", id));
        }
    }

}
