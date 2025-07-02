package com.parking.service;

import java.util.Optional;

import com.parking.model.User;

public interface UserService {
    void saveUser(User user);
    Optional<User> findByEmail(String email);  


}
