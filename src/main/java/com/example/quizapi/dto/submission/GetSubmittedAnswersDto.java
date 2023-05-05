package com.example.quizapi.dto.submission;

import com.example.quizapi.dto.answer.GetAnswerDto;
import com.example.quizapi.dto.question.GetQuestionDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class GetSubmittedAnswersDto {
    private Long id;
    private GetQuestionDto question;
    private Set<GetAnswerDto> answers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
