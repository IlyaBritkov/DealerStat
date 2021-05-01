package com.leverx.service;

import com.leverx.dto.GameDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;

import java.util.List;
import java.util.Optional;

public interface GameService {

    List<GameDTO.Response.Public> findAll();

    Optional<GameDTO.Response.Public> findById(Integer id);

    GameDTO.Response.Public save(GameDTO.Request.Create gameDtoRequest);

    GameDTO.Response.Public update(GameDTO.Request.Update gameDtoRequest) throws NoSuchEntityException;

    void deleteById(Integer id);
}
