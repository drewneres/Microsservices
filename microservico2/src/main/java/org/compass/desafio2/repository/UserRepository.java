package org.compass.desafio2.repository;

import org.compass.desafio2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
