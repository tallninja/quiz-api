package com.example.quizapi.service.answer;

import com.example.quizapi.domain.Answer;
import com.example.quizapi.domain.Question;

import java.util.List;

public interface AnswerService {
    Answer save(Answer question);

    List<Answer> findAll(
            int page,
            int size,
            String sortDirection,
            String sort
    );

    Answer findById(Long id);

    Answer update(Long id, Answer question);

    Answer delete(Long id);

    List<Answer> findByQuestion(Question question);
}
