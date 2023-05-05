package com.example.quizapi.service.question;

import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Quiz;

import java.util.List;

public interface QuestionService {
    Question save(Question question);

    List<Question> findAll(
            int page,
            int size,
            String sortDirection,
            String sort
    );

    Question findById(Long id);

    Question update(Long id, Question question);

    Question delete(Long id);

    List<Question> findByQuiz(
            Quiz quiz,
            int page,
            int size,
            String sort,
            String sortDirection
    );
}
