package com.leverx.rest;

import com.leverx.dto.GameDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class GameRestController {
    private final GameService gameService;

    @GetMapping
    @PreAuthorize("hasAuthority('games:read')")
    public ResponseEntity<List<GameDTO.Response.Public>> getAllGames() {
        List<GameDTO.Response.Public> gameList = gameService.findAll();
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('games:read')")
    public ResponseEntity<GameDTO.Response.Public> getGameById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        Optional<GameDTO.Response.Public> optionalGame = gameService.findById(id);
        return new ResponseEntity<>(optionalGame.get(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('games:write')")
    public ResponseEntity<GameDTO.Response.Public> addNewGame(@RequestBody GameDTO.Request.Create gameDtoRequest) {
        GameDTO.Response.Public gameDtoResponse = gameService.save(gameDtoRequest);
        return new ResponseEntity<>(gameDtoResponse, HttpStatus.CREATED);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('games:write')")
    public ResponseEntity<GameDTO.Response.Public> updateGame(@RequestBody GameDTO.Request.Update gameDtoRequest) throws NoSuchEntityException {
        GameDTO.Response.Public gameDtoResponse = gameService.update(gameDtoRequest);
        return new ResponseEntity<>(gameDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('games:write')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGameById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        gameService.deleteById(id);
    }
}
