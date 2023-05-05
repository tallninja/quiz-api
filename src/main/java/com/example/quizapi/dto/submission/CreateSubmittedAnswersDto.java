package com.example.quizapi.dto.submission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateSubmittedAnswersDto {
    @NotNull
    private Long questionId;

    @NotNull
    private Set<Long> answers;
}
