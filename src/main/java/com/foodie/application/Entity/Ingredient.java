package com.foodie.application.Entity;
import jakarta.persistence.*;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String amount;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}