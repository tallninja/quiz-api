package com.example.quizapi.dto.quiz;

import com.example.quizapi.annotations.QuizDuration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateQuizDto {
    @NotBlank()
    private String title;

    @QuizDuration
    private String duration;

    private String description;
}
