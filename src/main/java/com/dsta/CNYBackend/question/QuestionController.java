package com.dsta.CNYBackend.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;
import rx.schedulers.Schedulers;

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
    public Single<ResponseEntity<Question>> getQuestion(@PathVariable("position") Integer position) {
        return this.questionService.getQuestionByPositionObservable(position)
                .subscribeOn(Schedulers.io())
                .map(question -> ResponseEntity.ok(question))
                .onErrorReturn(error -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<List<Question>>> getAllQuestions() {
        return this.questionService.getAllQuestionsObservable()
                .subscribeOn(Schedulers.io())
                .map(questions -> ResponseEntity.ok(questions))
                .onErrorReturn(error -> ResponseEntity.notFound().build());
    }
}
