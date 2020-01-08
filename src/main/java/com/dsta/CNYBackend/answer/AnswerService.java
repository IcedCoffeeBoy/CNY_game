package com.dsta.CNYBackend.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer save(Answer answer) {
        return this.answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionPosition(Integer position){
        return answerRepository.getAnswersByQuestionPosition(position);
    }
}
