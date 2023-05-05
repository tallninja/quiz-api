package com.example.quizapi.controller;

import com.example.quizapi.domain.Question;
import com.example.quizapi.dto.answer.GetAnswerDto;
import com.example.quizapi.mappers.AnswerMapper;
import com.example.quizapi.mappers.QuestionMapper;
import com.example.quizapi.dto.question.CreateQuestionDto;
import com.example.quizapi.dto.question.GetQuestionDto;
import com.example.quizapi.dto.question.UpdateQuestionDto;
import com.example.quizapi.service.answer.AnswerService;
import com.example.quizapi.service.question.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    public QuestionController(
            QuestionService questionService,
            QuestionMapper questionMapper,
            AnswerService answerService,
            AnswerMapper answerMapper
    ) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetQuestionDto>> getAllQuestions(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") String sortDirection
    ) {
        List<Question> questions = questionService.findAll(page, size, sortDirection, sort);

        return ResponseEntity.ok(
                questions.stream()
                            .map(questionMapper::questionToGetQuestionDto)
                            .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetQuestionDto> getQuestionById(@PathVariable(name = "id") Long id) {
        try {
            Question question = questionService.findById(id);

            return ResponseEntity.ok(questionMapper.questionToGetQuestionDto(question));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GetQuestionDto> createQuestion(
            HttpServletRequest request,
            @Valid @RequestBody CreateQuestionDto question
    ) throws URISyntaxException {
        Question _question = questionService.save(
                questionMapper.createQuestionDtoToQuestion(question)
        );
        URI location = new URI(request.getRequestURL() + "/" + _question.getId());

        return ResponseEntity.created(location).body(
                questionMapper.questionToGetQuestionDto(_question)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetQuestionDto> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuestionDto question
    ) {
        try {
            Question updatedQuestion = questionService.update(id,
                    questionMapper.updateQuestionDtoToQuestion(question)
            );

            return ResponseEntity.ok(
                    questionMapper.questionToGetQuestionDto(updatedQuestion));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetQuestionDto> deleteQuestion(@PathVariable Long id) {
        Question deletedQuestion = questionService.delete(id);
        return ResponseEntity.ok(questionMapper.questionToGetQuestionDto(deletedQuestion));
    }

    @GetMapping("{id}/answers")
    public ResponseEntity<List<GetAnswerDto>> getAnswers(@PathVariable Long id) {
        Question question = questionService.findById(id);
        List<GetAnswerDto> answers = answerService.findByQuestion(question)
                                                .stream()
                                                .map(answerMapper::answerToGetAnswerDto)
                                                .toList();
        return ResponseEntity.ok(answers);
    }
}
