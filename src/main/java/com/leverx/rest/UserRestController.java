package com.leverx.rest;

import com.leverx.dto.FeedbackDTO;
import com.leverx.dto.UserDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.exception_handling.exception.UserSignUpException;
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
public class UserRestController { // todo
    private final UserService userService;

    @GetMapping
//    @PreAuthorize("hasAuthority('users:read')")
    // all
    public ResponseEntity<List<UserDTO.Response.Public>> getAllApprovedTraders() {
        List<UserDTO.Response.Public> userList = userService.findAllApprovedTraders();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/{id}")
    // all
    public ResponseEntity<UserDTO.Response.Public> getApprovedTraderById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        Optional<UserDTO.Response.Public> optionalUser = userService.findApprovedTraderById(id);
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    // only admin
    public ResponseEntity<List<UserDTO.Response.Public>> getAllNotApprovedTraders() {
        List<UserDTO.Response.Public> userList = userService.findAllNotApprovedTraders();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    // only admin
    public ResponseEntity<UserDTO.Response.Public> getNotApprovedTraderById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        Optional<UserDTO.Response.Public> optionalUser = userService.findNotApprovedTraderById(id);
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}/feedbacks")
    // all
    public ResponseEntity<List<FeedbackDTO.Response.Public>> getFeedbackByIdByUserId(@PathVariable("id") Integer id) {
        List<FeedbackDTO.Response.Public> feedbacksDtoResponse = userService.findAllFeedbacksByUser(id);
        return new ResponseEntity<>(feedbacksDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/feedbacks/{feedbackId}")
    // all
    public ResponseEntity<FeedbackDTO.Response.Public> getFeedbackByIdByUserId(@PathVariable("userId") Integer userId,
                                                                               @PathVariable("feedbackId") Integer feedbackId) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = userService.findFeedbackByIdByUserIdUser(userId, feedbackId);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @PostMapping
    // anon
    public ResponseEntity<UserDTO.Response.Public> addNewUser(@RequestBody UserDTO.Request.Create userDtoRequest) throws UserSignUpException {
        UserDTO.Response.Public userDtoResponse = userService.save(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }

    @PatchMapping
    // trader and admin
    public ResponseEntity<UserDTO.Response.Public> updateUser(@RequestBody UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException {
        UserDTO.Response.Public userDtoResponse = userService.update(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }


    @PatchMapping("/approve/{id}")
    // only admin
    public ResponseEntity<UserDTO.Response.Public> updateUser(@RequestBody UserDTO.Request.Approve userDtoRequest) throws NoSuchEntityException {
        UserDTO.Response.Public userDtoResponse = userService.approve(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    // trader and admin
    public void deleteUserById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        userService.deleteById(id);
    }
}
