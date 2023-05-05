package com.example.quizapi.dto.question;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateQuestionDto {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Min(0)
    @Max(100)
    private int points;
}
