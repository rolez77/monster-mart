package com.rolez.backend.cards;

import com.rolez.backend.users.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
//String name, String set, double price, String imageId, String condition, Optional<User> user
public class CardRowMapper implements RowMapper<Card> {
    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        Card card = new Card();

        card.setId(rs.getInt("card_id"));
        card.setName(rs.getString("card_name"));
        card.setSet(rs.getString("card_set"));
        card.setCondition(rs.getString("card_condition"));
        card.setPrice(rs.getDouble("card_price"));

        int userId = rs.getInt("user_id");

        if(!rs.wasNull()) {
            User user = new User();
            user.setId(userId);
            card.setUser(user);
        }
        return card;
    }
}
