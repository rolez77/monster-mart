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
                SELECT id, name, username, email, password, profilePictureUrl
                FROM users
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        var sql = """
                SELECT id, name, username, email, password, profilePictureUrl
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
                INSERT INTO users(name, username, email, password)
                VALUES (?, ?, ?, ?)
                  """;
        int result = jdbcTemplate.update(
                sql,
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
        System.out.println("insertUser result " + result);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        var sql = """
                SELECT COUNT(id)
                FROM users
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsUserById(Integer id) {
        var sql = """
                SELECT COUNT(id)
                FROM users
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteUserById(Integer id) {
        var sql = """
                DELETE
                FROM users
                WHERE id = ?
                """;
        int res =  jdbcTemplate.update(sql, id);
        System.out.println("deleteUser result " + res);
    }

    @Override
    public void updateUser(User update) {
        if(update.getName() != null) {
            String sql = "UPDATE user SET name = ? WHERE id = ?";
            int res = jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("updateUser result " + res);
        }
        if(update.getUsername() != null) {
            String sql = "UPDATE users SET username = ? WHERE id = ?";
            int res = jdbcTemplate.update(sql, update.getUsername(), update.getId());
            System.out.println("updateUser result " + res);
        }
        if(update.getEmail() != null) {
            String sql = "UPDATE users SET email = ? WHERE id = ?";
            int res = jdbcTemplate.update(sql, update.getEmail(), update.getId());
            System.out.println("updateUser result " + res);
        }
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        var sql = """
                SELECT id, name, username, email, password, profilePictureUrl
                FROM users
                WHERE email = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, email).stream().findFirst();
    }

    @Override
    public void updateUserProfileImageId(String profileImageUrl, Integer id) {

        var sql = """
                UPDATE users
                SET profilePictureUrl = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, profileImageUrl, id);
    }
}
