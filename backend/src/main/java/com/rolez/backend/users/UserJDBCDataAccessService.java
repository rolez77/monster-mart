package com.rolez.backend.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class UserJDBCDataAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserJDBCDataAccessService(JdbcTemplate jdbcTemplate,  UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> selectAll() {
        var sql = """
                SELECT id, name, email, password, profilePictureUrl
                FROM users
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        var sql = """
                SELECT id, name, email, password, profilePictureUrl
                FROM users
                WHERE id = ?
                """;
        return jdbcTemplate.query(
                sql, userRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertUser(User user) {
        var sql = """
                INSERT INTO users(name, email, password)
                VALUES (?, ?, ?)
                  """;
        int result = jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
        System.out.println("insertUser result " + result);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsUserById(Integer id) {
        return false;
    }

    @Override
    public void deleteUserById(Integer id) {

    }

    @Override
    public void updateUser(User update) {

    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void updateUserProfileImageId(String profileImageUrl, Integer id) {

    }
}
