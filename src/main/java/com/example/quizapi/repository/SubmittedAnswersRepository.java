package com.example.quizapi.repository;

import com.example.quizapi.domain.SubmittedAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmittedAnswersRepository extends JpaRepository<SubmittedAnswers, Long> {
}
