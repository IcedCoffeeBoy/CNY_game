package com.dsta.CNYBackend.poll;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "poll_choice")
@SequenceGenerator(name = "pollChoiceIdSeq", sequenceName = "poll_choice_id_seq", allocationSize = 1)
public class PollChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "poll_choice_id_seq")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Column(name = "choice")
    private Long Choice;

    public PollChoice(Poll poll, Long choice) {
        this.poll = poll;
        Choice = choice;
    }

    public PollChoice() {
    }

    public Long getChoice() {
        return Choice;
    }

    public void setChoice(Long choice) {
        Choice = choice;
    }

    @Override
    public int hashCode() {
        return getChoice().intValue();
    }
}
