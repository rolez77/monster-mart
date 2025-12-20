package com.rolez.backend.users;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectAll();
    Optional<User> selectUserById(Integer id);
    void insertUser(User user);
    boolean existsUserByEmail(String email);
    boolean existsUserById(Integer id);
    void deleteUserById(Integer id);
    void updateUser(User update);
    Optional<User> selectUserByEmail(String email);
    void updateUserProfileImageId(String profileImageUrl, Integer id);

}
