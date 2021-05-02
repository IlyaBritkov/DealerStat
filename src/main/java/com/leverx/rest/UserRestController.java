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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO.Response.Public>> getAllApprovedTraders() {
        List<UserDTO.Response.Public> userList = userService.findAllApprovedTraders();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO.Response.Public> getApprovedTraderById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        Optional<UserDTO.Response.Public> optionalUser = userService.findApprovedTraderById(id);
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @GetMapping("/approve")
    @PreAuthorize("hasAuthority('not_approved_users:read')")
    public ResponseEntity<List<UserDTO.Response.Public>> getAllNotApprovedTraders() {
        List<UserDTO.Response.Public> userList = userService.findAllNotApprovedTraders();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('not_approved_users:read')")
    public ResponseEntity<UserDTO.Response.Public> getNotApprovedTraderById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        Optional<UserDTO.Response.Public> optionalUser = userService.findNotApprovedTraderById(id);
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}/feedbacks")
    public ResponseEntity<List<FeedbackDTO.Response.Public>> getFeedbackByIdByUserId(@PathVariable("id") Integer id) {
        List<FeedbackDTO.Response.Public> feedbacksDtoResponse = userService.findAllFeedbacksByUser(id);
        return new ResponseEntity<>(feedbacksDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/feedbacks/{feedbackId}")
    public ResponseEntity<FeedbackDTO.Response.Public> getFeedbackByIdByUserId(@PathVariable("userId") Integer userId,
                                                                               @PathVariable("feedbackId") Integer feedbackId) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = userService.findFeedbackByIdByUserIdUser(userId, feedbackId);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO.Response.Public> addNewUser(@RequestBody UserDTO.Request.Create userDtoRequest) throws UserSignUpException {
        UserDTO.Response.Public userDtoResponse = userService.save(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:update')")
    public ResponseEntity<UserDTO.Response.Public> updateUser(@PathVariable("id") Integer id,
                                                              @RequestBody UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException {
        UserDTO.Response.Public userDtoResponse = userService.update(id, userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }


    @PatchMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('not_approved_users:update')")
    public ResponseEntity<UserDTO.Response.Public> updateNotApprovedUser(@PathVariable("id") Integer id,
                                                                         @RequestBody UserDTO.Request.Approve userDtoRequest) throws NoSuchEntityException {
        UserDTO.Response.Public userDtoResponse = userService.approve(id, userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('users:update')")
    public void deleteUserById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        userService.deleteById(id);
    }
}
