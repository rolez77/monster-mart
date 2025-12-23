package com.rolez.backend.cards;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rolez.backend.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Data
@Entity
@Table (name ="cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Example, Charizard, MegaEx, 676767.41, *image*, mint
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String set;

    @Column(nullable = false)
    private Double price;

    @Column
    private String imageId;

    @Column(nullable = false)
    private String condition; //perfect near perfect, rugged

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Card(){}

    public Card(String name, String set, Double price, String imageId, String condition, User user) {
        this.name = name;
        this.set = set;
        this.price = price;
        this.imageId = imageId;
        this.condition = condition;
        this.user = user;
    }

    public Card(String name, String set, double price, String imageId, String condition, Optional<User> user) {
        this.name = name;
        this.set = set;
        this.price = price;
        this.imageId = imageId;
        this.condition = condition;
        this.user = user.orElse(null);
    }
}


