package com.foodie.application.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredient {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipeId", nullable = false)

    @JsonIgnore
    private Recipe recipe;

    public Ingredient(String name) {
        this.name = name;
    }
}
