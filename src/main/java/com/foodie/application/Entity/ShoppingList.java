package com.foodie.application.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping-list")
public class ShoppingList {

        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Id
        private Long id;
        @Column
        private String title;
        @Column
        private boolean isDone;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonIgnore
        private User user;
    }
