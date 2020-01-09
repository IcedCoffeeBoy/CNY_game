package com.dsta.CNYBackend.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsersService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username) {
        User newUser = new User();
        newUser.setUsername(username);
        User saved = this.userRepository.save(newUser);
        return saved;
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
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

    public List<UserScore> getAllUserScore() {
        List<User> users = this.userRepository.findAll();
        users.sort((user1, user2) -> user2.getScore() - user1.getScore());
        List<UserScore> userScores = users.stream().map(user -> new UserScore(user.getUsername(), user.getScore())).collect(Collectors.toList());
        return userScores;
    }

}
