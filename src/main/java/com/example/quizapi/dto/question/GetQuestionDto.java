package com.example.quizapi.dto.question;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetQuestionDto {
    private Long id;

    private Long quizId;

    private String title;

    private String description;

    private int points;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
