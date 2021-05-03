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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class FeedbackRestController {
    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<FeedbackDTO.Response.Public>> getAllApprovedFeedbacks() {
        List<FeedbackDTO.Response.Public> feedbackDtoResponseList = feedbackService.findAllApprovedFeedbacks();
        return new ResponseEntity<>(feedbackDtoResponseList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO.Response.Public> getApprovedFeedbackById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.findApprovedFeedbackById(id).get();
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/approve")
    @PreAuthorize("hasAuthority('not_approved_feedbacks:read')")
    public ResponseEntity<List<FeedbackDTO.Response.Public>> getAllNotApprovedFeedbacks() {
        List<FeedbackDTO.Response.Public> feedbackDtoResponseList = feedbackService.findAllNotApprovedFeedbacks();
        return new ResponseEntity<>(feedbackDtoResponseList, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('not_approved_feedbacks:read')")
    public ResponseEntity<FeedbackDTO.Response.Public> getNotApprovedFeedbackById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.findNotApprovedFeedbackById(id).get();
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedbackDTO.Response.Public> addNewFeedback(@RequestBody FeedbackDTO.Request.Create feedbackDtoRequest) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.save(feedbackDtoRequest);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('feedbacks:update')")
    public ResponseEntity<FeedbackDTO.Response.Public> updateFeedback(@PathVariable Integer id,
                                                                      @RequestBody FeedbackDTO.Request.Approve feedbackDtoRequest) throws NoSuchEntityException {
        FeedbackDTO.Response.Public feedbackDtoResponse = feedbackService.approve(id, feedbackDtoRequest);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('feedbacks:update')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFeedbackById(@PathVariable("id") Integer id) throws NoSuchEntityException {
        feedbackService.deleteById(id);
    }

}
