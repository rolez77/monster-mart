package com.rolez.backend.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsUserByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.profilePictureUrl = ?1 WHERE u.id = ?2")
    int updateProfileImageId(String profileImageId, Integer customerId);
}
