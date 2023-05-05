package com.example.quizapi.repository;

import com.example.quizapi.domain.Answer;
import com.example.quizapi.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public List<Answer> findByQuestion(Question question);
}
