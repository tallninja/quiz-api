package com.example.quizapi.service.question;


import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Quiz;
import com.example.quizapi.repository.QuestionRepository;
import com.example.quizapi.service.quiz.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizService quizService;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            QuizService quizService
    ) {
        this.questionRepository = questionRepository;
        this.quizService = quizService;
    }

    @Override
    public Question save(Question question) {
        try {
            Quiz quiz = quizService.findById(question.getQuiz().getId());
            question.setQuiz(quiz);
            Question _question = questionRepository.save(question);

            log.info("Question " + _question.getId() + " was created.");
            return _question;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Question> findAll(
            int page,
            int size,
            String sortDirection,
            String sort
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, size, Sort.Direction.fromString(sortDirection), sort);
        return questionRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Question " + id + "was not found.");
                    return new NoSuchElementException("Question was not found");
                }
        );
    }

    @Override
    public Question update(Long id, Question question) {
        Question _question = questionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Question " + id + "was not found.");
                    return new NoSuchElementException("Question was not found");
                }
        );
        _question.setTitle(question.getTitle());
        _question.setPoints(question.getPoints());
        _question.setDescription(question.getDescription());
        log.info("Question " + id + " was updated.");
        return questionRepository.save(_question);
    }

    @Override
    public Question delete(Long id) {
        Question _question = questionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Question " + id + "was not found.");
                    return new NoSuchElementException("Question was not found");
                }
        );
        questionRepository.delete(_question);

        log.info("Question " + id + " was deleted.");
        return _question;
    }

    @Override
    public List<Question> findByQuiz(
            Quiz quiz,
            int page,
            int size,
            String sort,
            String sortDirection
    ) {
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.fromString(sortDirection),
                sort);
        return questionRepository.findByQuiz(quiz, pageRequest);
    }
}
