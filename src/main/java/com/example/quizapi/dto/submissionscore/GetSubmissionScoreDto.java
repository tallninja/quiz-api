package com.example.quizapi.dto.submissionscore;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetSubmissionScoreDto {
    private Long id;
    private Long submissionId;
    private Long totalScore;
    private Long finalScore;
    private Double percentageScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
