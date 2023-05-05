package com.example.quizapi.controller;

import com.example.quizapi.service.answer.AnswerService;
import com.example.quizapi.dto.answer.CreateAnswerDto;
import com.example.quizapi.dto.answer.GetAnswerDto;
import com.example.quizapi.dto.answer.UpdateAnswerDto;
import com.example.quizapi.domain.Answer;
import com.example.quizapi.mappers.AnswerMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("answers")
public class AnswerController {


    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerMapper answerMapper;

    @GetMapping
    public ResponseEntity<List<GetAnswerDto>> getAllAnswers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") String sortDirection
    ) {
        List<Answer> answers = answerService.findAll(page, size, sortDirection, sort);

        return ResponseEntity.ok(
                answers.stream().map(
                        answer -> answerMapper.answerToGetAnswerDto(answer)
                ).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAnswerDto> getAnswerById(@PathVariable(name = "id") Long id) {
        try {
            Answer answer = answerService.findById(id);

            return ResponseEntity.ok(answerMapper.answerToGetAnswerDto(answer));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GetAnswerDto> createAnswer(
            HttpServletRequest request,
            @Valid @RequestBody CreateAnswerDto answer
    ) throws URISyntaxException {
        try {
            Answer _answer = answerService.save(answerMapper.createAnswerDtoToAnswer(answer));
            URI location = new URI(request.getRequestURL() + "/" + _answer.getId());

            return ResponseEntity.created(location).body(
                    answerMapper.answerToGetAnswerDto(_answer));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetAnswerDto> updateAnswer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAnswerDto answer
    ) {
        try {
            Answer updatedAnswer = answerService.update(id, answerMapper.updateAnswerDtoToAnswer(answer));

            return ResponseEntity.ok(answerMapper.answerToGetAnswerDto((updatedAnswer)));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetAnswerDto> deleteAnswer(@PathVariable Long id) {
        try {
            Answer deletedAnswer = answerService.delete(id);

            return ResponseEntity.ok(answerMapper.answerToGetAnswerDto(deletedAnswer));
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
