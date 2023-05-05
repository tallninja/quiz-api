package com.example.quizapi.service.quiz;

import com.example.quizapi.domain.Quiz;

import java.util.List;

public interface QuizService {
    Quiz save(Quiz quiz);

    List<Quiz> findAll(int page, int size, String sortDirection, String sort);

    Quiz findById(Long id);

    Quiz update(Long id, Quiz quiz);

    Quiz delete(Long id);
}
