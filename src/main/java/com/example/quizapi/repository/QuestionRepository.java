package com.example.quizapi.repository;

import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public List<Question> findByQuiz(Quiz quiz, Pageable pageRequest);
}
