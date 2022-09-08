package com.example.jwtauth.service;

import com.example.jwtauth.entity.auth.Role;
import com.example.jwtauth.entity.auth.User;
import com.example.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder, RoleService roleService) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    public User register(User user){
        Role role = roleService.findByName("ROLE_USER");
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User findByLogin(String login){
        return repository.findByLogin(login);
    }

    public Optional<User> findByLoginAndPassword(String login, String password){
        User user = repository.findByLogin(login);
        if(user != null && encoder.matches(password, user.getPassword())){
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
