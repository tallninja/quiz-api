package com.example.quizapi.mappers;

import com.example.quizapi.domain.*;
import com.example.quizapi.dto.submission.CreateSubmissionDto;
import com.example.quizapi.dto.submission.GetSubmissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SubmissionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    default Submission createSubmissionDtoToSubmission(CreateSubmissionDto createSubmissionDto) {
        Quiz quiz = Quiz.builder().id(createSubmissionDto.getQuizId()).build();
        Set<SubmittedAnswers> submittedAnswersSet = createSubmissionDto.getSubmittedAnswers()
                .stream()
                .map(saDto -> {
                    Question question = Question.builder().id(saDto.getQuestionId()).build();
                    Set<Answer> answerSet = saDto.getAnswers()
                            .stream()
                            .map(a -> Answer.builder().id(a).build())
                            .collect(Collectors.toSet());
                    return SubmittedAnswers.builder().question(question).answers(answerSet).build();
                }).collect(Collectors.toSet());
        return Submission.builder().quiz(quiz).submittedAnswers(submittedAnswersSet).build();
    }

    GetSubmissionDto submissionToGetSubmissionDto(Submission submission);
}
