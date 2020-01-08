package com.dsta.CNYBackend.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q where q.position = ?1")
    List<Question> getQuestionsByPosition(Integer position);
}
