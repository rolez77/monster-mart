package com.rolez.backend.users;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String profilePictureUrl;
    private String name;
    private String username;
    private String password;
    private String email;
}
