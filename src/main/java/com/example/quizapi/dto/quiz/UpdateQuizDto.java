package com.example.quizapi.dto.quiz;

import com.example.quizapi.annotations.QuizDuration;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateQuizDto {
    @NotBlank
    private String title;

    @QuizDuration
    private String duration;

    private String description;
}
