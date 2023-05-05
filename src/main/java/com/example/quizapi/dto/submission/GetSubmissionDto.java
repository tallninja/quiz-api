package com.example.quizapi.dto.submission;

import com.example.quizapi.dto.quiz.GetQuizDto;
import lombok.Data;

import java.util.Set;

@Data
public class GetSubmissionDto {
    private Long id;
    private GetQuizDto quiz;
    private Set<GetSubmittedAnswersDto> submittedAnswers;
}
