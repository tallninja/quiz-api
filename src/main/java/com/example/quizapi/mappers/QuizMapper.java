package com.example.quizapi.mappers;

import com.example.quizapi.domain.Quiz;
import com.example.quizapi.dto.quiz.CreateQuizDto;
import com.example.quizapi.dto.quiz.GetQuizDto;
import com.example.quizapi.dto.quiz.UpdateQuizDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QuizMapper {
    GetQuizDto quizToGetQuizDto(Quiz quiz);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Quiz createQuizDtoToQuiz(CreateQuizDto createQuizDto);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Quiz updateQuizDtoToQuiz(UpdateQuizDto updateQuizDto);
}
