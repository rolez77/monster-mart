package com.rolez.backend.cards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository  extends JpaRepository<Card, Integer> {
    Optional<Card> existsCardByName(String name);
    Optional<Card> findByName(String name);
}