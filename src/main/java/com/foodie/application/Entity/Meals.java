package com.foodie.application.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "meals")
public class Meals {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    @Column
    private String title;
}
