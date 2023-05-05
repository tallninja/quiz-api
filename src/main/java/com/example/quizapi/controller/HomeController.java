package com.example.quizapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> message = new HashMap<>();
        message.put("message", "Quiz API");
        return ResponseEntity.ok(message);
    }
}
