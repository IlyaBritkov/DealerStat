package com.leverx.service;

import com.leverx.dto.UserDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO.Response.Public> findAll();

    Optional<UserDTO.Response.Public> findById(Integer id);

    UserDTO.Response.Public save(UserDTO.Request.Create userDtoRequest);

    UserDTO.Response.Public update(UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException;

    void deleteById(Integer id);
}
