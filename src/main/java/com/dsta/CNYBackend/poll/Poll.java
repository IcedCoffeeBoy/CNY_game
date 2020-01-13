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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "poll")
@SequenceGenerator(name = "pollIdSeq", sequenceName = "poll_id_seq", allocationSize = 1)
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "poll_id_seq")
    private Long id;

    @Column(name = "question_position")
    private Integer questionPosition;

    @OneToMany(mappedBy = "poll", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PollChoice> choices;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "poll")
    @Fetch(FetchMode.SUBSELECT)
    private Set<PollSummary> pollSummaries;

    public Poll() {
    }

    public Poll(Integer questionPosition, List<Long> choices, Map<Long, Integer> pollSummaryMap) {
        this.questionPosition = questionPosition;
        this.choices = choices.stream().map(choice -> new PollChoice(this, choice)).collect(Collectors.toSet());
        this.pollSummaries = new HashSet<>();
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

    public Set<PollChoice> getChoices() {
        return choices;
    }

    public void setChoices(Set<PollChoice> choices) {
        this.choices = choices;
    }

    public Set<PollSummary> getPollSummaries() {
        return pollSummaries;
    }

    public void setPollSummaries(Set<PollSummary> pollSummaries) {
        this.pollSummaries = pollSummaries;
    }
}
