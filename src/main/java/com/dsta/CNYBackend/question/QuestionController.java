package com.dsta.CNYBackend.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/question")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

//    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
//        Question saved = this.questionService.createQuestion(question);
//        return ResponseEntity.ok(saved);
//    }

    @GetMapping(value = "/{position}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> getQuestion(@PathVariable("position") Integer position) {
        Question question = this.questionService.getQuestionByPosition(position);
        return ResponseEntity.ok(question);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = this.questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping(value = "/all-normal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Question>> getAllQuestionsNormal() {
        List<Question> questions = this.questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

}
