package com.dsta.CNYBackend.poll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "poll_summary")
@SequenceGenerator(name = "pollSummaryIdSeq", sequenceName = "poll_summary_id_seq", allocationSize = 1)
public class PollSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "poll_summary_id_seq")
    private Long id;


}
