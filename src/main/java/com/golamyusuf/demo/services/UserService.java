package com.golamyusuf.demo.services;

import com.golamyusuf.demo.dtos.UserRequest;
import com.golamyusuf.demo.dtos.UserResponse;
import com.golamyusuf.demo.entities.User;
import com.golamyusuf.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

  /*  @Autowired
    private PasswordEncoder passwordEncoder;*/


    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password((userRequest.getPassword()))
                .kingdom(userRequest.getKingdom())
                .build();
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::mapToResponse).orElse(null);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserByUsername(String username) {
         User  user = userRepository.findByUsername(username);
        return  mapToResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword((userRequest.getPassword()));
            user.setKingdom(userRequest.getKingdom());
            User updatedUser = userRepository.save(user);
            return mapToResponse(updatedUser);
        } else {
            return null;
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .kingdom(user.getKingdom())
                .build();
    }

//    public UserResponse authenticateUser(UserRequest userRequest) {
//        User user = userRepository.findByUsername(userRequest.getUsername());
//
//        if (user != null && passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
//            return new UserResponse(user.getId(), user.getUsername(), user.getKingdom());
//        } else {
//            throw new RuntimeException("Invalid username or password");
//        }
//    }

    private final Set<String> activeUsers = Collections.synchronizedSet(new HashSet<>());

    public Set<String> getActiveUsers() {
        return activeUsers;
    }

    public void addUser(String username) {
        activeUsers.add(username);
    }

    public void removeUser(String username) {
        activeUsers.remove(username);
    }

    private final Set<String> connectedUsers = new HashSet<>();

    public Set<String> getConnectedUsers() {
        return new HashSet<>(connectedUsers); // Return a copy to avoid external modification
    }
}

