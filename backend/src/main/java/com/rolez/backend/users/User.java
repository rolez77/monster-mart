package com.rolez.backend.users;


import com.rolez.backend.cards.Card;
import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table (name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String profilePictureUrl;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> cards;

    @Override
    public String getUsername(){
        return email;
    }

    public String getHandle(){
        return username;
    }


    public User(String username, Integer id, String name, String email,  String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.username = username;

    }
    public User(String username, Integer id, String name, String email,  String password, String profilePictureUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
        this.id = id;
        this.username = username;
    }
    public User() {

    }

    public User(String username, String name, String email,  String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
