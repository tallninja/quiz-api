package com.example.quizapi.service.submissionscore;

import com.example.quizapi.domain.Submission;
import com.example.quizapi.domain.SubmissionScore;
import org.springframework.data.domain.Page;

public interface SubmissionScoreService {
    Page<SubmissionScore> findAll(int page, int size, String sort, String sortDirection);
    SubmissionScore findById(Long id);
    SubmissionScore findBySubmission(Submission submission);
    SubmissionScore save(SubmissionScore submissionScore);
    SubmissionScore update(Long id, SubmissionScore submissionScore);
    SubmissionScore delete(Long id);
}
