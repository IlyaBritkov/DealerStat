package com.leverx.service;

import com.leverx.dto.FeedbackDTO;
import com.leverx.exception_handling.exception.NoSuchEntityException;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    List<FeedbackDTO.Response.Public> findAll();

    Optional<FeedbackDTO.Response.Public> findById(Integer id) throws RuntimeException;

    List<FeedbackDTO.Response.Public> findAllByUserId(Integer id);

    Optional<FeedbackDTO.Response.Public> findByIdAndUserId(Integer feedbackId, Integer userId);

    FeedbackDTO.Response.Public save(FeedbackDTO.Request.Create feedbackDtoRequest) throws NoSuchEntityException;

    FeedbackDTO.Response.Public update(FeedbackDTO.Request.Update feedbackDtoRequest) throws NoSuchEntityException;

    void deleteById(Integer id) throws RuntimeException;
}
