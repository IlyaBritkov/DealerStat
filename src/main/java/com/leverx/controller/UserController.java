package com.leverx.controller;

import com.leverx.dto.UserDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO.Response.Public>> getAllUsers() {
        List<UserDTO.Response.Public> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO.Response.Public> getUserById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        Optional<UserDTO.Response.Public> optionalUser = userService.findById(id);
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO.Response.Public> addNewUser(@RequestBody UserDTO.Request.Create userDtoRequest) {
        UserDTO.Response.Public userDtoResponse = userService.save(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserDTO.Response.Public> updateUser(@RequestBody UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException{
        UserDTO.Response.Public userDtoResponse = userService.update(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        userService.deleteById(id);
    }
}
