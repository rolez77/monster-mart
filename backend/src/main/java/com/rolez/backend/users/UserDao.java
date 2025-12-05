package com.rolez.backend.users;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectAll();
    Optional<User> selectUserById(Long id);
    void insertUser(User user);
    boolean existsUserByEmail(String email);
    boolean existsUserById(Long id);
    void deleteUserById(Long id);
    void updateUser(User update);
    Optional<User> selectUserByEmail(String email);
    void updateUserProfileImageId(String profileImageUrl, Long id);
}
