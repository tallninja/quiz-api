package com.example.quizapi;

import com.example.quizapi.domain.Answer;
import com.example.quizapi.domain.Question;
import com.example.quizapi.domain.Quiz;
import com.example.quizapi.domain.User;
import com.example.quizapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitDbData implements CommandLineRunner {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SubmittedAnswersRepository submittedAnswersRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubmissionScoreRepository submissionScoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        submissionScoreRepository.deleteAll();
        answerRepository.deleteAll();
        submittedAnswersRepository.deleteAll();
        submissionRepository.deleteAll();
        questionRepository.deleteAll();
        quizRepository.deleteAll();

        List<User> users = createUsers();
        List<Quiz> quizzes = createQuizzes();
        List<Question> questions = createQuestions(quizzes);
        List<Answer> answers = createAnswers(questions);
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                        .firstName("Ernest")
                        .lastName("Wambua")
                        .email("admin@quizapi.com")
                        .password(passwordEncoder.encode("password123"))
                        .roles("USER,MANAGER,ADMIN")
                        .build());
        users.add(User.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("manager@quizapi.com")
                        .password(passwordEncoder.encode("password123"))
                        .roles("USER,MANAGER")
                        .build());
        users.add(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("user@quizapi.com")
                        .password(passwordEncoder.encode("password123"))
                        .roles("USER")
                        .build());
        return userRepository.saveAll(users);
    }

    private List<Quiz> createQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(Quiz.builder()
                        .title("Test Quiz 1")
                        .duration("3h:30m:0s")
                        .build());
        quizzes.add(Quiz.builder()
                        .title("Test Quiz 2")
                        .duration("2h:30m:0s")
                        .build());
        quizzes.add(Quiz.builder()
                        .title("Test Quiz 3")
                        .duration("1h:30m:0s")
                        .build());
        return quizRepository.saveAll(quizzes);
    }

    private List<Question> createQuestions(List<Quiz> quizzes) {
        List<Question> questions = new ArrayList<>();
        questions.add(Question.builder()
                        .title("Test Question 1")
                        .quiz(quizzes.get(0))
                        .points(5)
                        .build());
        questions.add(Question.builder()
                        .title("Test Question 2")
                        .quiz(quizzes.get(0))
                        .points(5)
                        .build());
        questions.add(Question.builder()
                        .title("Test Question 1")
                        .quiz(quizzes.get(1))
                        .points(5)
                        .build());
        questions.add(Question.builder()
                        .title("Test Question 2")
                        .quiz(quizzes.get(1))
                        .points(5)
                        .build());
        return questionRepository.saveAll(questions);
    }

    private List<Answer> createAnswers(List<Question> questions) {
        List<Answer> answers = new ArrayList<>();
        answers.add(Answer.builder()
                        .title("Test Answer 1")
                        .correct(true)
                        .question(questions.get(0))
                        .build());
        answers.add(Answer.builder()
                        .title("Test Answer 2")
                        .correct(false)
                        .question(questions.get(0))
                        .build());
        answers.add(Answer.builder()
                        .title("Test Answer 3")
                        .correct(false)
                        .question(questions.get(0))
                        .build());
        answers.add(Answer.builder()
                        .title("Test Answer 4")
                        .correct(true)
                        .question(questions.get(0))
                        .build());
        return answerRepository.saveAll(answers);
    }
}
