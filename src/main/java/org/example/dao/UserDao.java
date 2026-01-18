package org.example.dao;

import org.example.datamodel.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static RowMapper<User> ROW_MAPPER = ((rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("username")));

    //C
    public int createUser(User user) {
        return jdbcTemplate.update(
                "INSERT INTO users (id, username) VALUES (?,?)",
                user.getId(), user.getUsername()
        );
    }

    //R
    public Optional<User> getUser(long id) {
        List<User> users = jdbcTemplate.query(
                "SELECT id, username FROM users WHERE id = ?",
                ROW_MAPPER,
                id
        );
        return users.stream().findFirst();
    }

    //R
    public List<User> getAllUsers() {
        return jdbcTemplate.query(
                "SELECT id, username FROM users ",
                ROW_MAPPER
        );
    }

    //U
    public int updateUser(User user) {
        return jdbcTemplate.update(
                "UPDATE users SET username=? WHERE id =  ?",
                user.getUsername(), user.getId()
        );
    }

    //D
    public int deleteUser(long id) {
        return jdbcTemplate.update(
                "DELETE FROM users WHERE id =  ?",
                id
        );
    }

}
