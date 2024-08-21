package com.foodie.application.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
public class User { //Tabela User - tworzyć wiersze w tabeli, metody związane z userem(różne zmiany, np. zmiana hasła czy też loginu)

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @JsonIgnore
    private List<ShoppingList> shoppingList;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = PasswordEncoderConfig.passwordEncoder().encode(password);
    }
}
