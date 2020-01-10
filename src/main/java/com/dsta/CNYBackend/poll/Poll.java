package com.dsta.CNYBackend.poll;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "poll")
@SequenceGenerator(name = "pollIdSeq", sequenceName = "poll_id_seq", allocationSize = 1)
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "poll_id_seq")
    private Long id;

    @Column(name = "question_position")
    private Integer questionPosition;


    @Column(name = "choice")
    private Long choice;

    public Poll() {
    }

    public Poll(Integer questionPosition, Long choice) {
        this.questionPosition = questionPosition;
        this.choice = choice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuestionPosition() {
        return questionPosition;
    }

    public void setQuestionPosition(Integer questionPosition) {
        this.questionPosition = questionPosition;
    }

    public Long getChoice() {
        return choice;
    }

    public void setChoice(Long choice) {
        this.choice = choice;
    }
}
