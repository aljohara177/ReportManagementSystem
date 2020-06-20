package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long userId);

    void deleteUser(long userId);

    User findUserByUsername(String username) ;
    User findUserByEmail(String email) ;


}
