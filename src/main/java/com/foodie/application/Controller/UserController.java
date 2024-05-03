package com.foodie.application.Controller;

import com.foodie.application.Entity.User;
import com.foodie.application.Repository.UserRepository;
import com.foodie.application.ValueObject.ExampleUserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController { // Routingi - tutaj znajdują się ścieżki do różnych części aplikacji
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{

        userRepository.save(user);
        return new ResponseEntity<User>(
                user, HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/test")//show only first name and last name example.
    // TODO: Check how to optimize UserRepository to return ExampleUserVO not User (is needed to implement new method in UserRepository interface)
    public ResponseEntity<List<ExampleUserVO>> getAllUsersVOExampleTest()
    {
        List<ExampleUserVO> userList = new ArrayList<ExampleUserVO>();
        List<User> users = userRepository.findAll();
        for (User user:users) {
            userList.add(new ExampleUserVO(user.getFirstName(), user.getLastName()));
        }
        return new ResponseEntity<List<ExampleUserVO>>(userList, HttpStatus.OK);
    }

    @GetMapping("/test2")
    public List<User> getAllUsers2()
    {
        return userRepository.findAll();
    }
}
