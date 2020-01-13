package com.dsta.CNYBackend.answer;

import com.dsta.CNYBackend.question.Question;
import com.dsta.CNYBackend.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@SequenceGenerator(name = "answerIdSeq", sequenceName = "answer_id_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "answer_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "choice")
    private Long choice;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createAt;

    public Answer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getChoice() {
        return choice;
    }

    public void setChoice(Long choice) {
        this.choice = choice;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", user=" + user +
                ", question=" + question +
                ", choice=" + choice +
                ", createAt=" + createAt +
                '}';
    }
}
