package com.example.quizapi.service.answer;

import com.example.quizapi.domain.Answer;
import com.example.quizapi.domain.Question;
import com.example.quizapi.repository.AnswerRepository;
import com.example.quizapi.service.question.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Override
    public Answer save(Answer answer) {
        try {
            Question question = questionService.findById(answer.getQuestion().getId());
            answer.setQuestion(question);
            Answer _answer = answerRepository.save(answer);

            log.info("Answer " + _answer.getId() + " was created.");
            return answerRepository.save(answer);
        }
        catch (NoSuchElementException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Answer> findAll(
            int page,
            int size,
            String sortDirection,
            String sort
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, size, Sort.Direction.fromString(sortDirection), sort
        );
        return answerRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Answer not found")
        );
    }

    @Override
    public Answer update(Long id, Answer answer) {
        Answer _answer = answerRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Answer " + id + " was not found.");
                    return new NoSuchElementException("Answer not found");
                }
        );
        _answer.setTitle(answer.getTitle());
        _answer.setCorrect(answer.isCorrect());

        log.info("Answer " + _answer.getId() + " was updated.");
        return answerRepository.save(_answer);
    }

    @Override
    public Answer delete(Long id) {
        Answer _answer = answerRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Answer " + id + " was not found.");
                    return new NoSuchElementException("Answer not found");
                }
        );
        answerRepository.delete(_answer);

        log.info("Answer " + id + " was deleted.");
        return _answer;
    }

    @Override
    public List<Answer> findByQuestion(Question question) {
        return answerRepository.findByQuestion(question);
    }
}
