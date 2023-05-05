package com.example.quizapi.service.submission;

import com.example.quizapi.domain.Submission;
import com.example.quizapi.domain.SubmittedAnswers;

import java.util.Set;

public interface SubmittedAnswersService {

    SubmittedAnswers save(SubmittedAnswers answers);
    Set<SubmittedAnswers> saveAll(Set<SubmittedAnswers> submittedAnswers, Submission submission);
}
