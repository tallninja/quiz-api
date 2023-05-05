package com.example.quizapi.dto.submission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateSubmissionDto {
    @NotNull
    private Long quizId;

    @NotNull
    private Set<CreateSubmittedAnswersDto> submittedAnswers;
}
