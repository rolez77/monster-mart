package com.rolez.backend.users;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class UserListDataAccessService implements UserDao {

    private static final List<User> users;
    static {
        users = Arrays.asList();

        User lebron = new User(
                "LebronUsername",
                1,
                "Lebron",
                "lebron@labubu.com",
                "pass123"
        );
    }
    @Override
    public List<User> selectAll() {
        return users;
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public void insertUser(User user) {
        users.add(user);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsUserById(Integer id) {
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }

    @Override
    public void deleteUserById(Integer id) {
        users.stream().filter(user -> user.getId().equals(id)).findFirst().ifPresent(users::remove);
    }

    @Override
    public void updateUser(User update) {
        users.add(update);
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public void updateUserProfileImageId(String profileImageUrl, Integer id) {
        // TODO DO IT
    }
}
