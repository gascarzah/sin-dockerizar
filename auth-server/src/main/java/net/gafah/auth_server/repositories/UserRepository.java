package net.gafah.auth_server.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gafah.auth_server.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}