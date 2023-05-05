package com.example.quizapi.dto.answer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetAnswerDto {
    private Long id;

    private Long questionId;

    private String title;

    private boolean correct;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
