package com.leverx.service;

import com.leverx.dto.FeedbackDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    List<FeedbackDTO.Response.Public> findAllApprovedFeedbacks();

    Optional<FeedbackDTO.Response.Public> findApprovedFeedbackById(Integer id) throws RuntimeException;

    List<FeedbackDTO.Response.Public> findAllNotApprovedFeedbacks();

    Optional<FeedbackDTO.Response.Public> findNotApprovedFeedbackById(Integer id);

    List<FeedbackDTO.Response.Public> findAllApprovedByUserId(Integer id) throws NoSuchEntityException;

    Optional<FeedbackDTO.Response.Public> findApprovedByIdAndUserId(Integer feedbackId, Integer userId) throws NoSuchEntityException;

    FeedbackDTO.Response.Public save(FeedbackDTO.Request.Create feedbackDtoRequest) throws NoSuchEntityException;

    FeedbackDTO.Response.Public approve(Integer id, FeedbackDTO.Request.Approve feedbackDtoRequest) throws NoSuchEntityException;

    void deleteById(Integer id) throws NoSuchEntityException;
}
