package com.example.quizapi.mappers;

import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Quiz;
import com.example.quizapi.dto.question.CreateQuestionDto;
import com.example.quizapi.dto.question.GetQuestionDto;
import com.example.quizapi.dto.question.UpdateQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QuestionMapper {
    default GetQuestionDto questionToGetQuestionDto(Question question) {
        return GetQuestionDto.builder()
                .id(question.getId())
                .quizId(question.getQuiz().getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .points(question.getPoints())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    default Question createQuestionDtoToQuestion(CreateQuestionDto createQuestionDto) {
        return Question.builder()
                        .title(createQuestionDto.getTitle())
                        .points(createQuestionDto.getPoints())
                        .quiz(Quiz.builder().id(createQuestionDto.getQuizId()).build())
                        .build();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "submittedAnswers", ignore = true)
    Question updateQuestionDtoToQuestion(UpdateQuestionDto updateQuestionDto);
}
