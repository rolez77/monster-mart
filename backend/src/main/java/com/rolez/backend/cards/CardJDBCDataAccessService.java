package com.rolez.backend.cards;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("cardJdbc")
public class CardJDBCDataAccessService implements CardDao {
    private final JdbcTemplate jdbcTemplate;
    private final CardRowMapper cardRowMapper;

    public CardJDBCDataAccessService(JdbcTemplate jdbcTemplate, CardRowMapper cardRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.cardRowMapper = cardRowMapper;
    }


    @Override
    public List<Card> selectAll() {
        var sql = """
                SELECT id, name, "set", condition, price,image_id, user_id
                FROM cards
                """;
        return jdbcTemplate.query(sql, cardRowMapper);
    }

    @Override
    public Optional<Card> selectCardById(Integer id) {
        var sql = """
                SELECT id, name, "set", condition, price,image_id, user_id
                FROM cards
                WHERE id = ?
                """;
        return jdbcTemplate.query(
                sql, cardRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public void addCard(Card card) {
        var sql = """
                INSERT INTO CARDS(name, "set", condition, price, image_id, user_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql,
                card.getName(),
                card.getSet(),
                card.getCondition(),
                card.getPrice()
                ,card.getImageId(),
                card.getUser().getId());
        System.out.println("Insert result: " + result);
    }

    @Override
    public boolean existsCardById(Integer id) {
        var sql = """
                SELECT COUNT(id)
                FROM cards
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteCardById(Integer id) {
        var sql = """
                DELETE
                FROM cards
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("Delete result: " + result);
    }

    @Override
    public void updateCard(Card update) {
        if(update.getName() != null) {
            String sql = "UPDATE cards SET name = ? WHERE id = ?";
            int res =  jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("update result " + res);
        }
        if(update.getSet() != null) {
            String sql = "UPDATE cards SET set = ? WHERE id = ?";
            int res =  jdbcTemplate.update(sql, update.getSet(), update.getId());
            System.out.println("update result " + res);
        }
        if(update.getCondition() != null) {
            String sql = "UPDATE cards SET condition = ? WHERE id = ?";
            int res =  jdbcTemplate.update(sql, update.getCondition(), update.getId());
            System.out.println("update result " + res);
        }
        if(update.getPrice() != null) {
            String sql = "UPDATE cards SET price = ? WHERE id = ?";
            int res =  jdbcTemplate.update(sql, update.getPrice(), update.getId());
            System.out.println("update result " + res);
        }
    }

    @Override
    public Optional<Card> selectCardByName(String name) {
        var sql = """
                SELECT card_id, card_name, card_set, card_condition, card_price
                FROM cards
                WHERE card_name = ?
                """;
        return jdbcTemplate.query(sql, cardRowMapper, name).stream().findFirst();
    }
}
