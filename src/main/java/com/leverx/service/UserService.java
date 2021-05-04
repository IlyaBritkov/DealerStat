package com.leverx.service;

import com.leverx.dto.FeedbackDTO;
import com.leverx.dto.GameDTO;
import com.leverx.dto.UserDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.exception_handling.exception.UserSignUpException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO.Response.Public> findAllApprovedTraders();

    Optional<UserDTO.Response.Public> findApprovedTraderById(Integer id) throws NoSuchEntityException;

    List<UserDTO.Response.Public> findAllNotApprovedTraders();

    Optional<UserDTO.Response.Public> findNotApprovedTraderById(Integer id) throws NoSuchEntityException;

    Optional<UserDTO.Response.Public> findByEmail(String email) throws UsernameNotFoundException;

    List<GameDTO.Response.Public> findAllGamesByUser(Integer id) throws NoSuchEntityException;

    Optional<GameDTO.Response.Public> findGameByIdByUser(Integer userId, Integer gameId) throws NoSuchEntityException;

    List<FeedbackDTO.Response.Public> findAllFeedbacksByUser(Integer id) throws NoSuchEntityException;

    Optional<FeedbackDTO.Response.Public> findFeedbackByIdByUserIdUser(Integer userId, Integer feedbackId);

    UserDTO.Response.Public save(UserDTO.Request.Create userDtoRequest) throws UserSignUpException;

    UserDTO.Response.Public update(Integer id, UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException;

    UserDTO.Response.Public approve(Integer id, UserDTO.Request.Approve userDtoRequest) throws NoSuchEntityException;

    void deleteById(Integer id);
}
