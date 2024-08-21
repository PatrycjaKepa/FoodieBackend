package com.foodie.application.Repository;

import com.foodie.application.Entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    ShoppingList findByTitle(String title);
    List<ShoppingList> findByUserId(Long userId);
}
