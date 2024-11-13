package com.foodie.application.Repository;

import com.foodie.application.Entity.Recipe;
import com.foodie.application.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUser(User user);
    Optional<Recipe> findByIdAndUser(Long id, User user);
}
