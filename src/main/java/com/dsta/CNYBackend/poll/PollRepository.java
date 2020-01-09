package com.dsta.CNYBackend.poll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PollRepository  extends JpaRepository<Poll,Long> {
    @Query("SELECT p FROM  Poll p WHERE p.questionPosition=?1")
    public List<Poll> getPollByPosition(Integer position);
}
