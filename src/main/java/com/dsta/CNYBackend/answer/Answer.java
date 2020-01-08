package com.dsta.CNYBackend.answer;

import com.dsta.CNYBackend.question.Question;
import com.dsta.CNYBackend.user.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createAt;
}
