package com.example.quizapi.mappers;

import com.example.quizapi.domain.Answer;
import com.example.quizapi.domain.Question;
import com.example.quizapi.dto.answer.CreateAnswerDto;
import com.example.quizapi.dto.answer.GetAnswerDto;
import com.example.quizapi.dto.answer.UpdateAnswerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AnswerMapper {
    default GetAnswerDto answerToGetAnswerDto(Answer answer) {
        return GetAnswerDto.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .title(answer.getTitle())
                .correct(answer.isCorrect())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    default Answer createAnswerDtoToAnswer(CreateAnswerDto createAnswerDto) {
        return Answer.builder()
                .title(createAnswerDto.getTitle())
                .correct(createAnswerDto.isCorrect())
                .question(Question.builder().id(createAnswerDto.getQuestionId()).build())
                .build();
    }

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "submittedAnswers", ignore = true)
    Answer updateAnswerDtoToAnswer(UpdateAnswerDto updateAnswerDto);
}
