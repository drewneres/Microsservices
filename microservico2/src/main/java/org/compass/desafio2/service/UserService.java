package org.compass.desafio2.service;

import lombok.AllArgsConstructor;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final JsonPlaceholderClient jsonPlaceholderClient;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return jsonPlaceholderClient.getAllUsers();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return jsonPlaceholderClient.getUserById(id);
    }
}
