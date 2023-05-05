package com.example.quizapi.service.submissionscore;

import com.example.quizapi.domain.Submission;
import com.example.quizapi.domain.SubmissionScore;
import com.example.quizapi.repository.SubmissionScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class SubmissionScoreServiceImpl implements SubmissionScoreService {

    private final SubmissionScoreRepository submissionScoreRepository;

    public SubmissionScoreServiceImpl(SubmissionScoreRepository submissionScoreRepository) {
        this.submissionScoreRepository = submissionScoreRepository;
    }

    @Override
    public Page<SubmissionScore> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return submissionScoreRepository.findAll(pageRequest);
    }

    @Override
    public SubmissionScore findById(Long id) {
        return submissionScoreRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Quiz score " + id + " was not found.");
                    return new NoSuchElementException("Quiz score " + id + " was not found.");
                }
        );
    }

    @Override
    public SubmissionScore findBySubmission(Submission submission) {
        return submissionScoreRepository.findBySubmission(submission).orElseThrow(
                () -> {
                    log.error("Submission " + submission.getId() + " was not found.");
                    return new NoSuchElementException("Submission " + submission.getId() + " was not found.");
                }
        );
    }

    @Override
    public SubmissionScore save(SubmissionScore submissionScore) {
        return submissionScoreRepository.save(submissionScore);
    }

    @Override
    public SubmissionScore update(Long id, SubmissionScore submissionScore) {
        SubmissionScore _submissionScore = submissionScoreRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Quiz score " + id + " was not found.");
                    return new NoSuchElementException("Quiz score " + id + " was not found.");
                }
        );
        _submissionScore.setSubmission(submissionScore.getSubmission());
        _submissionScore.setTotalScore(submissionScore.getTotalScore());
        _submissionScore.setFinalScore(submissionScore.getFinalScore());
        _submissionScore.setPercentageScore(submissionScore.getPercentageScore());
        return submissionScoreRepository.save(_submissionScore);
    }

    @Override
    public SubmissionScore delete(Long id) {
        SubmissionScore _submissionScore = submissionScoreRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Quiz score " + id + " was not found.");
                    return new NoSuchElementException("Quiz score " + id + " was not found.");
                }
        );
        submissionScoreRepository.delete(_submissionScore);
        return _submissionScore;
    }
}
