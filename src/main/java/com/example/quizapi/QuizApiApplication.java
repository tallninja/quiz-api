package com.example.quizapi;

import com.example.quizapi.domain.Answer;
import com.example.quizapi.repository.*;
import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Quiz;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class QuizApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedDbData(
			QuizRepository quizRepository,
			QuestionRepository questionRepository,
			AnswerRepository answerRepository,
			SubmittedAnswersRepository submittedAnswersRepository,
			SubmissionRepository submissionRepository,
			SubmissionScoreRepository submissionScoreRepository
	) {
		return  (args) -> {
			submissionScoreRepository.deleteAll();
			answerRepository.deleteAll();
			submittedAnswersRepository.deleteAll();
			submissionRepository.deleteAll();
			questionRepository.deleteAll();
			quizRepository.deleteAll();

			List<Quiz> quizzes = new ArrayList<>();
			quizzes.add(Quiz.builder().title("Test Quiz 1").duration("2h:30m:0s").build());
			quizzes.add(Quiz.builder().title("Test Quiz 2").duration("2h:30m:0s").build());
			quizRepository.saveAll(quizzes);

			List<Question> questions = new ArrayList<>();
			questions.add(Question.builder().title("Test Question 1").quiz(quizzes.get(0)).points(5).build());
			questions.add(Question.builder().title("Test Question 2").quiz(quizzes.get(0)).points(5).build());
			questions.add(Question.builder().title("Test Question 1").quiz(quizzes.get(1)).points(5).build());
			questions.add(Question.builder().title("Test Question 2").quiz(quizzes.get(1)).points(5).build());
			questionRepository.saveAll(questions);

			List<Answer> answers = new ArrayList<>();
			answers.add(Answer.builder().title("Test Answer 1").correct(true).question(questions.get(0)).build());
			answers.add(Answer.builder().title("Test Answer 2").correct(false).question(questions.get(0)).build());
			answers.add(Answer.builder().title("Test Answer 3").correct(false).question(questions.get(0)).build());
			answers.add(Answer.builder().title("Test Answer 4").correct(true).question(questions.get(0)).build());
			answerRepository.saveAll(answers);
		};
	}

}
