package com.dsta.CNYBackend.poll;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "poll")
    @Fetch(FetchMode.JOIN)
    private List<PollSummary> pollSummaries;

    public Poll() {
    }

    public Poll(Integer questionPosition, Long choice, Map<Long, Integer> pollSummaryMap) {
        this.questionPosition = questionPosition;
        this.choice = choice;
        this.pollSummaries = new ArrayList<>();

        for (Long selectedChoice : pollSummaryMap.keySet()) {
            this.pollSummaries.add(new PollSummary(this, selectedChoice, pollSummaryMap.get(selectedChoice)));
        }
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

    public List<PollSummary> getPollSummaries() {
        return pollSummaries;
    }

    public void setPollSummaries(List<PollSummary> pollSummaries) {
        this.pollSummaries = pollSummaries;
    }
}
