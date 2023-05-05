package com.example.quizapi.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAnswerDto {
    @NotBlank
    private String title;

    @NotNull
    private boolean correct;
}
