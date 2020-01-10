package com.dsta.CNYBackend.user;

import com.dsta.CNYBackend.game.model.Participant;
import com.dsta.CNYBackend.user.model.UserScore;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT new com.dsta.CNYBackend.user.model.UserScore(u.username,u.score) from User u ORDER BY u.score DESC")
    public List<UserScore> findOrderByScore(Pageable pageable);

    @Query("SELECT new com.dsta.CNYBackend.game.model.Participant(u.username) from User u")
    public List<Participant> findAllParticipants(Pageable pageable);

}
