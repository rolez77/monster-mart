package com.rolez.backend.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsUserByEmail(String email);
    int updateProfileImageId(String profileImageId, Integer customerId);
}
