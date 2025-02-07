package org.compass.desafio2.service;

import lombok.AllArgsConstructor;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.User;
import org.compass.desafio2.exception.NotFoundException;
import org.compass.desafio2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final JsonPlaceholderClient jsonPlaceholderClient;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User", String.valueOf(id))
        );
    }
}
