package com.parking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parking.model.User;
import com.parking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordencoder;

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        repo.save(user);
    }

    @Override
    public Optional<User>findByEmail(String email) {
        return Optional.ofNullable(repo.findByEmail(email));  // ✅ Using email
    }
}