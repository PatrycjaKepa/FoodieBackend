package com.foodie.application.Controller;

import com.foodie.application.Entity.ShoppingList;
import com.foodie.application.Repository.ShoppingListRepository;
import com.foodie.application.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/shopping-list")
public class ShoppingListController {
    ShoppingListRepository shoppingListRepository;
    UserRepository userRepository;

    public ShoppingListController(ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) {
        var securityUserContext = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        var u = userRepository.findByEmail(securityUserContext.getUsername());
        shoppingList.setUser(u);
        shoppingListRepository.save(shoppingList);
        return new ResponseEntity<ShoppingList>(
                shoppingList, HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<?> getAllItems(){
        var securityUserContext = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        var user = userRepository.findByEmail(securityUserContext.getUsername());
        var shoppingList = shoppingListRepository.findByUserId(user.getId());
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShoppingListItem(@PathVariable("id") Long id) {
        var securityUserContext = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        var user = userRepository.findByEmail(securityUserContext.getUsername());
        var shoppingList = shoppingListRepository.findByUserId(user.getId());
        var itemToDelete = shoppingList.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        if (itemToDelete.isPresent()) {
            shoppingListRepository.delete(itemToDelete.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
