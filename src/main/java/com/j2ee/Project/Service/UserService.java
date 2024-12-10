package com.j2ee.Project.Service;

import com.j2ee.Project.Model.User;
import com.j2ee.Project.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return userRepo.save(user);
    }


    // READ all users and return
    public List<User> returnUser() {
        return userRepo.findAll();
    }

    // READ all users and return
    public List<User> returnStudents() {
//         List<User> out = userRepo.findByRole(Role.STUDENT);
//         return out;
        return userRepo.findStudents();
    }


    // CREATE new User
    public void addUser(User user) {
        userRepo.save(user);
    }

    // FETCH user by id
//    public User getUser(int id) {
//        return userRepo.findById(id);
//    }


    public User getUser(int id) {

        return userRepo.findById(id);
//        try {
//            Optional<User> optionalUser = userRepo.findById(id);
//            if (optionalUser.isPresent()) {
//                return optionalUser.get(); // Get the User object from the Optional
//            } else {
//                throw new RuntimeException("User not found for id :: " + id); // Handle the case where the user is not found
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while retrieving the user: " + e.getMessage(), e);
//        }
    }




    public User updateUser(int id, User editedUser){
        // Find the existing user by ID
        User user = userRepo.findById(id);

//        if (user == null) {
//            throw new Exception("User not found with id: " + id);
//        }

        // Update the existing user with the editedUser details
        user.setName(editedUser.getName());
        user.setUsername(editedUser.getUsername());
        user.setEmail(editedUser.getEmail());
        user.setRole(editedUser.getRole());
        // Add more fields to update as needed

        // Save the updated user
        return userRepo.save(user);
    }


    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    public String getUserRole(String username) {
        User user = userRepo.findByUsername(username);
        return String.valueOf(user.getRole());
    }
}
