package com.example.quizapi.service.submission;

import com.example.quizapi.domain.*;
import com.example.quizapi.repository.SubmissionRepository;
import com.example.quizapi.service.quiz.QuizService;
import com.example.quizapi.service.submissionscore.SubmissionScoreService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmittedAnswersService submittedAnswersService;
    private final QuizService quizService;
    private final SubmissionScoreService submissionScoreService;

    public SubmissionServiceImpl(
            SubmissionRepository submissionRepository,
            SubmittedAnswersService submittedAnswersService,
            QuizService quizService,
            SubmissionScoreService submissionScoreService
    ) {
        this.submissionRepository = submissionRepository;
        this.submittedAnswersService = submittedAnswersService;
        this.quizService = quizService;
        this.submissionScoreService = submissionScoreService;
    }

    @Override
    public Page<Submission> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return submissionRepository.findAll(pageRequest);
    }

    @Override
    public Submission findById(Long id) {
        Optional<Submission> submission = submissionRepository.findById(id);
        return submission.orElseThrow(() -> {
                    log.error("Submission " + id + " was not found.");
                    return new NoSuchElementException("Submission was not found");
                });
    }

    @Override
    public Submission save(Submission submission) {
        Quiz quiz = quizService.findById(submission.getQuiz().getId());
        Submission _submission = submissionRepository.save(Submission.builder().quiz(quiz).build());
        Set<SubmittedAnswers> submittedAnswers = submittedAnswersService.saveAll(
                submission.getSubmittedAnswers(), _submission);
        _submission.setSubmittedAnswers(submittedAnswers);
        gradeSubmission(_submission);
        return _submission;
    }

    @Override
    public Submission delete(Long id) {
        Submission _submission = submissionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Submission " + id + " was not found.");
                    return new NoSuchElementException("Submission was not found");
                }
        );
        submissionRepository.delete(_submission);
        return _submission;
    }

    public void gradeSubmission(Submission submission) {
        AtomicLong finalScore = new AtomicLong(0L);

        // logic to calculate the scores
        submission.getSubmittedAnswers().forEach(sa ->{
            Question question = sa.getQuestion();
            int points = question.getPoints();
            long correctAnswersCount = question.getAnswers()
                    .stream().filter(Answer::isCorrect).count();
            long correctGivenAnswersCount = sa.getAnswers()
                    .stream().filter(Answer::isCorrect).count();
            if (correctAnswersCount == correctGivenAnswersCount)
                finalScore.addAndGet(points);
        });

        Long totalScore = submission.getQuiz().getQuestions().stream().mapToLong(Question::getPoints).sum();
        Double percentageScore = (finalScore.doubleValue() / totalScore.doubleValue()) * 100;

        submissionScoreService.save(SubmissionScore.builder()
                    .submission(submission)
                    .totalScore(totalScore)
                    .finalScore(finalScore.get())
                    .percentageScore(percentageScore)
                    .build());
    }
}
