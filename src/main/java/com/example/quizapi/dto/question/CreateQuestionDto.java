package com.example.quizapi.dto.question;

import com.example.quizapi.domain.Quiz;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateQuestionDto {
    @NotBlank
    private String title;

    @NotNull
    @Min(0)
    @Max(100)
    private int points;

    private String description;

    @NotNull
    private Long quizId;
}
