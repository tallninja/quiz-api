package com.example.quizapi.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAnswerDto {
    @NotBlank
    private String title;

    private boolean correct;

    @NotNull
    private Long questionId;
}
