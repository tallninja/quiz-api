package com.example.quizapi.service.quiz;

import com.example.quizapi.domain.Quiz;
import com.example.quizapi.repository.QuizRepository;
import com.example.quizapi.service.quiz.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz save(Quiz quiz) {
        Quiz _quiz = quizRepository.save(quiz);

        log.info("Quiz " + _quiz.getId() + " was created.");
        return _quiz;
    }

    public List<Quiz> findAll(int page, int size, String sortDirection, String sort) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection), sort);
        return quizRepository.findAll(pageRequest).getContent();
    }

    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Quiz " + id + " was not found");
                    return new NoSuchElementException("Quiz was not found");
                }
        );
    }

    public Quiz update(Long id, Quiz quiz) {
        Quiz _quiz = quizRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Quiz " + id + " was not found");
                    return  new NoSuchElementException("Quiz was not found");
                }
        );
        _quiz.setTitle(quiz.getTitle());
        _quiz.setDuration(quiz.getDuration());
        _quiz.setDescription(quiz.getDescription());
        log.info("Quiz " + _quiz.getId() + " was updated.");
        return quizRepository.save(_quiz);
    }

    public Quiz delete(Long id) {
        Quiz _quiz = quizRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Quiz " + id + "not found");
                    return new NoSuchElementException("Quiz was not found");
                }
        );
        quizRepository.delete(_quiz);

        log.info("Quiz " + id + "was deleted");
        return _quiz;
    }
}
