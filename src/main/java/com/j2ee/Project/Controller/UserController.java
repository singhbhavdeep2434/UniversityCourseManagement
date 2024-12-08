package com.j2ee.Project.Controller;
import com.j2ee.Project.Model.User;
import com.j2ee.Project.Service.JwtService;
import com.j2ee.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
// @CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("signout")
    public String signout(@RequestHeader("Authorization") String authHeader) {

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            if(jwtService.signout(token)) {
                return "Signout Successful";
            }
            else
                return "Signout Failed";
        }
        else
            return "Signout Failed";
    }

    @PostMapping("register")
    public User user(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody User user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        else
            return "Login Failed";

    }

    @GetMapping("users")
    public List<User> getAllUsers() {    // READ
        return userService.returnUser();
    }

    @GetMapping("students")
    public List<User> getAllStudents() {    // READ filter for STUDENTS
        return userService.returnStudents();
    }


    @PostMapping("user")
    public User addUser(@RequestBody User user) {      // CREATE
        userService.addUser(user);
        return userService.getUser(user.getId());
    }

    @PutMapping("user")
    public User updateUser(@RequestBody User user) {      // UPDATE
        userService.updateUser(user);
        return userService.getUser(user.getId());
    }

    @GetMapping("user/{userId}")                 // SEARCH BY ID
    public User getUser(@PathVariable("userId") int id) {
        return userService.getUser(id);
    }

    @DeleteMapping("user/{userId}")            // DELETE user
    public String deleteUser(@PathVariable("userId") int id) {
        userService.deleteUser(id);
        return "User Deleted";
    }
}

