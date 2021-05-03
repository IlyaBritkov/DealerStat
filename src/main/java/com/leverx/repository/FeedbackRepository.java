package com.leverx.repository;

import com.leverx.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findAllByApprovedIsTrue();

    List<Feedback> findAllByApprovedIsNull();

    void deleteFeedbackById(Integer integer);

    List<Feedback> findAllByApprovedIsTrueAndTraderId(Integer id);

    Optional<Feedback> findByIdAndTraderId(Integer feedbackId, Integer userId);

}
