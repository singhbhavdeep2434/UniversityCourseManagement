package com.j2ee.Project.Controller;
import com.j2ee.Project.Model.User;
import com.j2ee.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin("http://localhost:3000")
@CrossOrigin("http://127.0.0.1:5500")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users")
    public List<User> getAllUsers() {    // READ
        return userService.returnUser();
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

