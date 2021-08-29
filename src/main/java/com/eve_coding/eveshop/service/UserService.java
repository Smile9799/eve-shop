package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.Role;
import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.repository.RoleRepository;
import com.eve_coding.eveshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveUser(User user){
        Set<Role> roles = (Set<Role>) roleRepository.findAll();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(roles);
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }
}
