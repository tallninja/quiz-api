package com.example.quizapi.controller;

import com.example.quizapi.domain.Quiz;
import com.example.quizapi.domain.Submission;
import com.example.quizapi.domain.SubmissionScore;
import com.example.quizapi.dto.submission.CreateSubmissionDto;
import com.example.quizapi.dto.submission.GetSubmissionDto;
import com.example.quizapi.dto.submissionscore.GetSubmissionScoreDto;
import com.example.quizapi.mappers.SubmissionMapper;
import com.example.quizapi.mappers.SubmissionScoreMapper;
import com.example.quizapi.service.submission.SubmissionService;
import com.example.quizapi.service.submissionscore.SubmissionScoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("submissions")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final SubmissionMapper submissionMapper;
    private final SubmissionScoreService submissionScoreService;
    private final SubmissionScoreMapper submissionScoreMapper;

    public SubmissionController(
            SubmissionService submissionService,
            SubmissionMapper submissionMapper,
            SubmissionScoreService submissionScoreService,
            SubmissionScoreMapper submissionScoreMapper
    ) {
        this.submissionService = submissionService;
        this.submissionMapper = submissionMapper;
        this.submissionScoreService = submissionScoreService;
        this.submissionScoreMapper = submissionScoreMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, URI>> save(
            HttpServletRequest request,
            @Valid @RequestBody CreateSubmissionDto submissionDto
    ) {
        Submission submission = submissionMapper.createSubmissionDtoToSubmission(submissionDto);
        Submission _submission = submissionService.save(submission);
        URI submissionLocation = URI.create(request.getRequestURL() + "/" + _submission.getId());
        URI submissionScoreLocation = URI.create(request.getRequestURL() + "/" + _submission.getId() + "/score");
        Map<String, URI> response = new HashMap<>();
        response.put("submission", submissionLocation);
        response.put("score", submissionScoreLocation);
        return ResponseEntity.created(submissionLocation).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<GetSubmissionDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<Submission> pagedSubmissions = submissionService.findAll(
                page, size, sort, sortDirection
        );
        List<GetSubmissionDto> submissions = submissionService.findAll(page, size, sort, sortDirection)
                .stream().map(submissionMapper::submissionToGetSubmissionDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(submissions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSubmissionDto> findById(@PathVariable Long id) {
        GetSubmissionDto submission = submissionMapper.submissionToGetSubmissionDto(
                submissionService.findById(id));
        return ResponseEntity.ok(submission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetSubmissionDto> delete(@PathVariable Long id) {
        GetSubmissionDto submission = submissionMapper.submissionToGetSubmissionDto(
                submissionService.delete(id));
        return ResponseEntity.ok(submission);
    }

    @GetMapping("{id}/score")
    public ResponseEntity<GetSubmissionScoreDto> getSubmissionScore(@PathVariable Long id) {
        Submission submission = submissionService.findById(id);
        SubmissionScore submissionScore = submissionScoreService.findBySubmission(submission);
        return ResponseEntity.ok(submissionScoreMapper
                .submissionScoreToGetSubmissionScoreDto(submissionScore));
    }
}
