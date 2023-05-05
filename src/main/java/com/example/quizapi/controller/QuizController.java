package com.example.quizapi.controller;

import com.example.quizapi.domain.Quiz;
import com.example.quizapi.dto.question.GetQuestionDto;
import com.example.quizapi.mappers.QuestionMapper;
import com.example.quizapi.mappers.QuizMapper;
import com.example.quizapi.dto.quiz.CreateQuizDto;
import com.example.quizapi.dto.quiz.GetQuizDto;
import com.example.quizapi.dto.quiz.UpdateQuizDto;
import com.example.quizapi.service.question.QuestionService;
import com.example.quizapi.service.quiz.QuizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("quizzes")
public class QuizController {
    private final QuizService quizService;
    private final QuizMapper quizMapper;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuizController(
            QuizService quizService,
            QuizMapper quizMapper,
            QuestionService questionService,
            QuestionMapper questionMapper
    ) {
        this.quizService = quizService;
        this.quizMapper = quizMapper;
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetQuizDto>> getAllQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") String sortDirection
    ) {
        List<Quiz> quizzes = quizService.findAll(page, size, sortDirection, sort);

        return ResponseEntity.ok(quizzes.stream()
                                        .map(quizMapper::quizToGetQuizDto)
                                        .collect((Collectors.toList())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetQuizDto> getQuizById(@PathVariable Long id) {
        try {
            Quiz quiz = quizService.findById(id);

            return ResponseEntity.ok(quizMapper.quizToGetQuizDto(quiz));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GetQuizDto> createQuiz(
            HttpServletRequest request,
            @Valid @RequestBody CreateQuizDto quiz
    ) throws URISyntaxException {
        Quiz _quiz = quizService.save(quizMapper.createQuizDtoToQuiz(quiz));
        URI location = new URI(request.getRequestURL() + "/" + _quiz.getId());

        return ResponseEntity.created(location).body(quizMapper.quizToGetQuizDto(_quiz));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetQuizDto> updateQuiz(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuizDto quiz) {
        try {
            Quiz updatedQuiz = quizService.update(id, quizMapper.updateQuizDtoToQuiz(quiz));

            return ResponseEntity.ok(quizMapper.quizToGetQuizDto((updatedQuiz)));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetQuizDto> deleteQuiz(@PathVariable Long id) {
        try {
            Quiz deletedQuiz = quizService.delete(id);

            return ResponseEntity.ok(quizMapper.quizToGetQuizDto((deletedQuiz)));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/questions")
    public ResponseEntity<List<GetQuestionDto>> getQuizQuestions(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "ASC", name = "sort_direction") String sortDirection
    ) {
        Quiz quiz = quizService.findById(id);
        List<GetQuestionDto> questions = questionService.findByQuiz(quiz, page, size, sort, sortDirection)
                .stream()
                .map(questionMapper::questionToGetQuestionDto)
                .toList();
        return ResponseEntity.ok(questions);
    }
}
