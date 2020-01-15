package com.dsta.CNYBackend.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

import javax.transaction.Transactional;
import java.io.IOException;
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

    public List<Question> getAllQuestions() {
        List<Question> questions = this.questionRepository.findAll();
        return questions;
    }

    public Single<Question> getQuestionByPositionObservable(Integer position) {
        return Single.create(singleSubscriber -> {
            List<Question> questions = this.questionRepository.getQuestionsByPosition(position);
            if (questions.size() != 0) {
                singleSubscriber.onError(new IOException("Not found"));
            } else {
                singleSubscriber.onSuccess(questions.get(0));
            }
        });
    }

    public Single<List<Question>> getAllQuestionsObservable() {
        return Single.create(singleSubscriber -> {
            List<Question> questions = this.questionRepository.findAll();
            if (questions.size() > 0) {
                singleSubscriber.onSuccess(questions);
            } else {
                singleSubscriber.onError(new IOException("Not found"));
            }
        });
    }
}
