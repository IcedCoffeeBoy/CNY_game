package com.dsta.CNYBackend.answer;

import com.dsta.CNYBackend.question.Question;
import com.dsta.CNYBackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer save(Question question, Long choice, User user) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setChoice(choice);
        answer.setUser(user);
        return this.answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionPosition(Integer position){
        return answerRepository.getAnswersByQuestionPosition(position);
    }
}
