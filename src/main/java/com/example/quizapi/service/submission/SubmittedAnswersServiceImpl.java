package com.example.quizapi.service.submission;

import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Submission;
import com.example.quizapi.domain.SubmittedAnswers;
import com.example.quizapi.repository.AnswerRepository;
import com.example.quizapi.repository.SubmittedAnswersRepository;
import com.example.quizapi.service.question.QuestionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubmittedAnswersServiceImpl implements SubmittedAnswersService {

    private final SubmittedAnswersRepository submittedAnswersRepository;
    private final QuestionService questionService;

    private final AnswerRepository answerRepository;

    public SubmittedAnswersServiceImpl(
            SubmittedAnswersRepository submittedAnswersRepository,
            QuestionService questionService,
            AnswerRepository answerRepository
    ) {
        this.submittedAnswersRepository = submittedAnswersRepository;
        this.questionService = questionService;
        this.answerRepository = answerRepository;
    }

    @Override
    public SubmittedAnswers save(SubmittedAnswers answers) {
        return submittedAnswersRepository.save(answers);
    }

    @Override
    public Set<SubmittedAnswers> saveAll(
            Set<SubmittedAnswers> submittedAnswers,
            Submission submission
    ) {
        return new HashSet<>(
                submittedAnswersRepository
                        .saveAll(submittedAnswers
                        .stream()
                        .peek(sa -> {
                            Question question = questionService.findById(sa.getQuestion().getId());
                            sa.setSubmission(submission);
                            sa.setQuestion(question);
                        })
                        .collect(Collectors.toSet())
        ));
    }
}
