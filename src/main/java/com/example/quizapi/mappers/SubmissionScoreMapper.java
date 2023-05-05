package com.example.quizapi.mappers;

import com.example.quizapi.domain.SubmissionScore;
import com.example.quizapi.dto.submissionscore.GetSubmissionScoreDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubmissionScoreMapper {
    default GetSubmissionScoreDto submissionScoreToGetSubmissionScoreDto(SubmissionScore submissionScore) {
        return GetSubmissionScoreDto.builder()
                .id(submissionScore.getId())
                .submissionId(submissionScore.getSubmission().getId())
                .totalScore(submissionScore.getTotalScore())
                .finalScore(submissionScore.getFinalScore())
                .percentageScore(submissionScore.getPercentageScore())
                .createdAt(submissionScore.getCreatedAt())
                .updatedAt(submissionScore.getUpdatedAt())
                .build();
    }
}
