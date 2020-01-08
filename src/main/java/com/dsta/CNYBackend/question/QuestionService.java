package com.dsta.CNYBackend.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    public Question getQuestionByPosition(Integer position) {
        List<Question> questions = this.questionRepository.getQuestionsByPosition(position);
        return questions.get(0);
    }
}
