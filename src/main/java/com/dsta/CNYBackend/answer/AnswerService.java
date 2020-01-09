package com.dsta.CNYBackend.answer;

import com.dsta.CNYBackend.question.Question;
import com.dsta.CNYBackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public Answer put(Question question, Long choice, User user) {
        Answer answer;
        Optional<Answer> exist = this.checkForExistingAnswer(question, user);
        if (exist.isPresent()) {
            answer = exist.get();
        } else {
            answer = new Answer();
            answer.setQuestion(question);
            answer.setUser(user);
        }
        answer.setChoice(choice);
        Answer save = this.answerRepository.save(answer);
        return save;
    }

    public Optional<Answer> checkForExistingAnswer(Question question, User user) {
        Answer example = new Answer();
        example.setQuestion(question);
        example.setUser(user);
        Optional<Answer> exist = this.answerRepository.findOne(Example.of(example));
        return exist;
    }

    public List<Answer> getAnswersByQuestionPosition(Integer position) {
        return answerRepository.getAnswersByQuestionPosition(position);
    }
}
