package com.foodie.application.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodie.application.Service.Configuration.PasswordEncoderConfig;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String login;
    @Column
    private String email;
    @Column
    @JsonIgnore
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<ShoppingList> shoppingList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Recipe> recipes;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = PasswordEncoderConfig.passwordEncoder().encode(password);
    }
}