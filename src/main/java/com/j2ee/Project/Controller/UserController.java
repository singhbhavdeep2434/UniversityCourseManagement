package com.j2ee.Project.Controller;
import com.j2ee.Project.Model.User;
import com.j2ee.Project.Repo.UserRepository;
import com.j2ee.Project.Service.JwtService;
import com.j2ee.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


@RestController
// @CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    UserService userService;



    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

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

//    @PostMapping("login")
//    public String login(@RequestBody User user) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        if(authentication.isAuthenticated())
//            return jwtService.generateToken(user.getUsername());
//        else
//            return "Login Failed";
//
//    }


    // NEW
//    @PostMapping("login")
//    public String login(@RequestBody User user) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            // Fetch the user's role
//            String role = userService.getUserRole(user.getUsername()); // Example: Fetch the role from your service
//
//            // Generate the token with the role included
//            return jwtService.generateTokenWithRole(user.getUsername(), role);
//        } else {
//            return "Login Failed";
//        }
//    }

    // NEW NEW
    @PostMapping("login")
    public Map<String, String> login(@RequestBody User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
//            String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming role is granted authority
            String role = userService.getUserRole(user.getUsername());
            String token = jwtService.generateTokenWithRole(user.getUsername(), role);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);

            return response;
        } else {
            throw new RuntimeException("Login Failed");
        }
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
//        userService.addUser(user);
        return userService.saveUser(user);
//        return userService.getUser(user.getId());
    }

//    @PutMapping("user")
//    public User updateUser(@RequestBody User user) {      // UPDATE
//        return userService.updateUser(user);
////        return userService.getUser(user.getId());
//    }

    @PutMapping("user/{userId}")
    public User updateUser(@PathVariable("userId") int id, @RequestBody User user) {      // UPDATE
        return userService.updateUser(id, user);
//        return userService.getUser(user.getId());
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

    @PostMapping("/login-as-user")
    public ResponseEntity<String> loginAsUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");

        // Find the user by username
        User user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate a JWT token for the user
        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
    }

}

