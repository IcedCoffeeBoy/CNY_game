package com.dsta.CNYBackend.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    @Query("SELECT a FROM Answer a where a.question.position = ?1 ")
    List<Answer> getAnswersByQuestionPosition(Integer position);

    @Query("SELECT a FROM Answer a where a.user.username = ?1 ")
    List<Answer> getAnswersByUser(String username);
}
