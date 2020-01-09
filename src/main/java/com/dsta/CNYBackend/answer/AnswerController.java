package com.dsta.CNYBackend.answer;

import com.dsta.CNYBackend.question.Question;
import com.dsta.CNYBackend.question.QuestionService;
import com.dsta.CNYBackend.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private AnswerService answerService;
    private QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @ApiOperation(value = "Submit choice for question(position). Requires JWT token header")
    @PostMapping(value = "/submit/{position}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> submitAnswer(
            @PathVariable("position") Integer position,
            @RequestBody Choice choice,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "User is not authenticated");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
        User user = (User) authentication.getPrincipal();
        Question question = this.questionService.getQuestionByPosition(position);
        Answer save = this.answerService.put(question, choice.getChoice(), user);
        Map<String, String> sucesss = new HashMap<>();
        sucesss.put("success", "Answer has been recorded");
        sucesss.put("choice", save.getChoice().toString());
        return ResponseEntity.ok(sucesss);
    }

}
