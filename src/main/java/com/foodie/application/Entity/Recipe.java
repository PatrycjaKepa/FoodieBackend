package com.foodie.application.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int servings;
    private int duration;
    private String ingredients;
    private String steps;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}