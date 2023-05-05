package com.example.quizapi.service.submission;

import com.example.quizapi.domain.Submission;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubmissionService {

    Page<Submission> findAll(int page, int size, String sort, String sortDirection);
    Submission findById(Long id);
    Submission save(Submission submission);
    Submission delete(Long id);

}
