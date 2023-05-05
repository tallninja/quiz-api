package com.example.quizapi.annotations;

import com.example.quizapi.validators.QuizDurationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = QuizDurationValidator.class)
public @interface QuizDuration {
    // error message
    String message() default  "Invalid quiz duration, must be: ?h:?m:?s";

    // group of constraints
    Class<?>[] groups() default {};

    // additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
