package com.rolez.backend.cards;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rolez.backend.users.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name ="cards")

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Example, Charizard, MegaEx, 676767.41, *image*, mint
    private String name;
    private String set;
    private Double price;
    private String imageUrl;
    private String condition; //perfect near perfect, rugged

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
