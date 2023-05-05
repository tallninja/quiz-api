package com.example.quizapi.dto.quiz;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetQuizDto {
    private Long id;

    private String title;

    private String duration;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
