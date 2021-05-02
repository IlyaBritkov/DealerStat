package com.leverx.rest;

import com.leverx.dto.FeedbackDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.service.FeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class FeedbackRestController {
    private final FeedbackService feedbackService;

    @GetMapping
    // all
    public ResponseEntity<List<FeedbackDTO.Response.Public>> getAllFeedbacks() {
        List<FeedbackDTO.Response.Public> feedbackDtoResponseList = feedbackService.findAll();
        return new ResponseEntity<>(feedbackDtoResponseList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/{id}")
    // all
    public ResponseEntity<FeedbackDTO.Response.Public> getFeedbackById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.findById(id).get();
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    // anon + new user
    public ResponseEntity<FeedbackDTO.Response.Public> addNewFeedback(@RequestBody FeedbackDTO.Request.Create feedbackDtoRequest) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.save(feedbackDtoRequest);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.CREATED);
    }

    @PatchMapping
    // only admin
    public ResponseEntity<FeedbackDTO.Response.Public> updateFeedback(@RequestBody FeedbackDTO.Request.Update feedbackDtoRequest) throws NoSuchEntityException{
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.update(feedbackDtoRequest);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    // only admin
    public void deleteFeedbackById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        feedbackService.deleteById(id);
    }

}
