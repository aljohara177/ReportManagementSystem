package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.exceptions.ItemNotFoundException;
import com.example.reportmanagmentsystem.model.Role;
import com.example.reportmanagmentsystem.model.User;
import com.example.reportmanagmentsystem.repository.RoleRepository;
import com.example.reportmanagmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        System.out.println(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ROLE_USER");
        user.setRoles(Arrays.asList(userRole));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userFromDb = userRepository.findById(user.getId());

        if(userFromDb.isPresent()) {
            User userUpdated = userFromDb.get();
            userUpdated.setId(user.getId());
            userUpdated.setName(user.getName());
            userUpdated.setEmail(user.getEmail());
            userUpdated.setMobileNumber(user.getMobileNumber());
            if (user.getPassword() != "") {
                userUpdated.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            } else {
                userUpdated.setPassword(userFromDb.get().getPassword());
            }
            Role userRole = roleRepository.findByRole("ROLE_USER");
            userUpdated.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
            userRepository.save(userUpdated);
            return userUpdated;
        } else {
            throw new ItemNotFoundException(("User does not exist"));
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);

        if (userFromDb.isPresent()) {
            return userFromDb.get();
        } else {
            throw new ItemNotFoundException(("User does not exist"));
        }
    }

    @Override
    public void deleteUser(long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);

        if (userFromDb.isPresent()) {
            this.userRepository.delete(userFromDb.get());
        } else {
            throw  new ItemNotFoundException(("User does not exist"));
        }
    }


}
