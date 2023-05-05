package com.example.quizapi.validators;

import com.example.quizapi.annotations.QuizDuration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizDurationValidator implements ConstraintValidator<QuizDuration, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        Pattern pattern = Pattern.compile("(\\d+)h:(\\d+)m:(\\d+)s");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            if (matcher.groupCount() != 3) return false;

            int hours = Integer.parseInt(matcher.group(1));
            int minutes = Integer.parseInt(matcher.group(2));
            int seconds = Integer.parseInt(matcher.group(3));

            if (hours < 0 || hours >= 24) return false;
            if (minutes < 0 || minutes >= 60) return false;
            return seconds >= 0 && seconds < 60;
        } else {
            return false;
        }
    }
}
