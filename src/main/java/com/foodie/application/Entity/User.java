package com.foodie.application.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User { //Tabela User - tworzyć wiersze w tabeli, metody związane z userem(różne zmiany, np. zmiana hasła czy też loginu)

    @GeneratedValue
    @Id
    private Long id;
    @Column
    private String login;
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    @JsonIgnore
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
}
