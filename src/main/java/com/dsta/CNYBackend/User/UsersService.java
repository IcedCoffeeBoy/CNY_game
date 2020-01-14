package com.dsta.CNYBackend.user;


import com.dsta.CNYBackend.answer.Answer;
import com.dsta.CNYBackend.game.model.Participant;
import com.dsta.CNYBackend.user.model.UserRank;
import com.dsta.CNYBackend.user.model.UserResponse;
import com.dsta.CNYBackend.user.model.UserScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsersService implements UserDetailsService {
    private UserRepository userRepository;
    private EntityManager em;

    @Autowired
    public UsersService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.em = entityManager;
    }

    public User createUser(String username) {
        User newUser = new User();
        newUser.setUsername(username);
        User saved = this.userRepository.save(newUser);
        return saved;
    }

    public User updateUser(User user) {
        User saved = this.userRepository.save(user);
        return saved;
    }

    public Boolean checkExistingUser(String username) {
        User user = new User();
        user.setUsername(username);
        Optional<User> exist = this.userRepository.findOne(Example.of(user));
        return exist.isPresent();
    }

    public User loadUserObjectByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UserRank getUserRank(User user) {
        List<UserScore> scores = this.getUserScores(50);
        int length = scores.size();
        int rank = -1;
        for (int i = 0; i < length; i++) {
            if (user.getUsername().equals(scores.get(i).getUsername())) {
                rank = i + 1;
                break;
            }
        }
        return new UserRank(user.getUsername(), rank, user.getScore());
    }

    public UserScore getUserScore(String username) {
        User user = this.loadUserObjectByUsername(username);
        return new UserScore(user.getUsername(), user.getScore());
    }

    public List<UserScore> getUserScores(int size) {
        List<UserScore> scores = this.userRepository.findOrderByScore(PageRequest.of(0, size));
        return scores;
    }

    public List<Participant> getAllParticipants() {
        return this.userRepository.findAllParticipants(PageRequest.of(0, 100));
    }

    public List<UserResponse> getAllUserResponse(String username) {
        User user = this.userRepository.findByUsername(username).get();
        List<Answer> answers = user.getAnswers();
        List<UserResponse> responses = answers.stream()
                .map(answer -> new UserResponse(answer.getQuestion().getPosition(), answer.getChoice()))
                .collect(Collectors.toList());
        return responses;
    }

    public void flush() {
        this.em.flush();
    }


}
