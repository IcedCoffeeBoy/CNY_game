package com.dsta.CNYBackend.game;

import com.dsta.CNYBackend.answer.Answer;
import com.dsta.CNYBackend.answer.AnswerService;
import com.dsta.CNYBackend.poll.Poll;
import com.dsta.CNYBackend.poll.PollService;
import com.dsta.CNYBackend.question.Question;
import com.dsta.CNYBackend.question.QuestionService;
import com.dsta.CNYBackend.user.User;
import com.dsta.CNYBackend.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GameUtil {
    private UsersService usersService;
    private AnswerService answerService;
    private QuestionService questionService;
    private PollService pollService;

    @Autowired
    public GameUtil(UsersService usersService, AnswerService answerService, QuestionService questionService, PollService pollService) {
        this.usersService = usersService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.pollService = pollService;
    }

    public int getTotalNumberOfQuestions() {
        List<Question> questions = this.questionService.getAllQuestions();
        return questions.size();
    }

    public void addPointsToWinner(Integer position) {
        List<Answer> answers = answerService.getAnswersByQuestionPosition(position);
        Long choice = getPoll(answers);
        this.pollService.save(new Poll(position, choice));
        List<Answer> filter = filterAnswerByChoice(answers, choice);
        List<Answer> filterAndSort = sortAnswerByDate(filter);
        int point = 200;
        int falloff = point / (filterAndSort.size() + 1);
        for (Answer answer : filterAndSort) {
            User user = answer.getUser();
            Integer score = user.getScore();
            if (score == null) {
                score = 0;
            }
            user.setScore(score + point);
            this.usersService.updateUser(user);
            point = point - falloff;
        }
    }

    private List<Answer> sortAnswerByDate(List<Answer> answers) {
        Collections.sort(answers, Comparator.comparing(Answer::getCreateAt)
        );
        for (Answer answer : answers) {
            System.out.println(answer.toString());
        }
        return answers;
    }


    private List<Answer> filterAnswerByChoice(List<Answer> answers, Long choice) {
        List<Answer> filter = answers.stream()
                .filter(answer -> answer.getChoice().intValue() == choice.intValue())
                .collect(Collectors.toList());
        return filter;
    }

    private Long getPoll(List<Answer> answers) {
        Map<Long, Integer> scores = new HashMap<>();
        for (Answer answer : answers) {
            Long choice = answer.getChoice();
            if (scores.containsKey(choice)) {
                Integer score = scores.get(choice);
                Integer newScore = score + 1;
                scores.put(choice, newScore);
            } else {
                Integer score = 1;
                scores.put(choice, score);
            }
        }
        return getMaxFromMap(scores);
    }

    private Long getMaxFromMap(Map<Long, Integer> map) {
        Long choice = null;
        Integer max = 0;
        for (Long key : map.keySet()) {
            Integer value = map.get(key);
            if (value.compareTo(max) > 0) {
                max = value;
                choice = key;
            }
        }
        return choice;
    }

}
