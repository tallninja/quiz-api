package com.example.quizapi.repository;

import com.example.quizapi.domain.SubmissionScore;
import com.example.quizapi.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionScoreRepository extends JpaRepository<SubmissionScore, Long> {
    Optional<SubmissionScore> findBySubmission(Submission submission);
}
