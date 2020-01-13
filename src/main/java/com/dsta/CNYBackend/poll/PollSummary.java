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
@Table(name = "poll_summary")
@SequenceGenerator(name = "pollSummaryIdSeq", sequenceName = "poll_summary_id_seq", allocationSize = 1)
public class PollSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "poll_summary_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Column(name = "choice")
    private Long Choice;

    @Column(name = "number")
    private Integer number;

    public PollSummary() {
    }

    public PollSummary(Poll poll, Long choice, Integer number) {
        this.poll = poll;
        Choice = choice;
        this.number = number;
    }

    public Long getChoice() {
        return Choice;
    }

    public void setChoice(Long choice) {
        Choice = choice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
