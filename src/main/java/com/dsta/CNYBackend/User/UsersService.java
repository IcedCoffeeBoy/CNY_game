package com.dsta.CNYBackend.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    public Boolean checkExistingUser(String username) {
        User user = new User();
        user.setUsername(username);
        Optional<User> exist = this.userRepository.findOne(Example.of(user));
        return exist.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found"));
        }
    }
}
