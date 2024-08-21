package com.foodie.application.Controller;

import com.foodie.application.Entity.User;
import com.foodie.application.Repository.UserRepository;
import com.foodie.application.ValueObject.CreateUserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final PasswordEncoder passwordEncoder; // Routingi - tutaj znajdują się ścieżki do różnych części aplikacji
    UserRepository userRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public ResponseEntity<User> createUser(@RequestBody CreateUserVO userVO) throws Exception{
        var login = userVO.getLogin();
        var email = userVO.getEmail();
        var password = userVO.getPassword();
        User user = new User(login, email, password);
        userRepository.save(user);

        System.out.println("ok");

        return new ResponseEntity<User>(
                user, HttpStatus.OK
        );
    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) throws Exception{
        System.out.println(user);
        var emailAndPassword = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        System.out.println(emailAndPassword);
//        boolean email = user.getEmail();
//        boolean password = user.getPassword();
//        if(userRepository.findByEmailAndPassword(email, password)) {
//            return new ResponseEntity<User>(
//                user, HttpStatus.OK
//        );
//        }
//        return new ResponseEntity<User>(
//                user, HttpStatus.NOT_FOUND
//        );
//        userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
//        String email = user.getEmail();
//        String password = user.getPassword();
        return new ResponseEntity<User>(
                user, HttpStatus.OK
        );
    }

    @GetMapping("/name")
    public ResponseEntity<String> getUserName(){
      var securityUserContext = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
              .getAuthentication()
              .getPrincipal());
      var user = userRepository.findByEmail(securityUserContext.getUsername());
      String name = user.getLogin();
      return new ResponseEntity<String>(name, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

//    @GetMapping("/test")//show only first name and last name example.
//    // TODO: Check how to optimize UserRepository to return ExampleUserVO not User (is needed to implement new method in UserRepository interface)
//    public ResponseEntity<List<ExampleUserVO>> getAllUsersVOExampleTest()
//    {
//        List<ExampleUserVO> userList = new ArrayList<ExampleUserVO>();
//        List<User> users = userRepository.findAll();
//        for (User user:users) {
//            userList.add(new ExampleUserVO(user.getFirstName(), user.getLastName()));
//        }
//        return new ResponseEntity<List<ExampleUserVO>>(userList, HttpStatus.OK);
//    }

    @GetMapping("/test2")
    public List<User> getAllUsers2()
    {
        return userRepository.findAll();
    }
}
