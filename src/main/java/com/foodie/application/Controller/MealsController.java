package com.foodie.application.Controller;
import java.util.List;

import com.foodie.application.Entity.Meals;
import com.foodie.application.Repository.MealsRepository;
import com.foodie.application.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping(value = "/foodie/meals")
public class MealsController {
    private final UserRepository userRepository;
    MealsRepository mealsRepository;

    public MealsController(MealsRepository mealsRepository, UserRepository userRepository) {
        this.mealsRepository = mealsRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Meals>> getAllMeals()
    {
        var user = ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        System.out.println(user);
        System.out.println(user.getUsername());
        var u = userRepository.findByEmail(user.getUsername());
        System.out.println(u);
        System.out.println(u.getId());
        System.out.println(u.getLogin());
        System.out.println(u.getRole());
        System.out.println(u.getShoppingList());
        return new ResponseEntity<List<Meals>>(mealsRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save")//nie zapisuje sie informacja o tytule
    public ResponseEntity<Meals> createMeals(@RequestBody Meals meals) {
      mealsRepository.save(meals);
      return new ResponseEntity<Meals>(
              meals, HttpStatus.OK
      );
    };
}
