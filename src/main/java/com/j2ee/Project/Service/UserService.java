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
        try {
            Optional<User> optionalUser = userRepo.findById(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get(); // Get the User object from the Optional
            } else {
                throw new RuntimeException("User not found for id :: " + id); // Handle the case where the user is not found
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the user: " + e.getMessage(), e);
        }
    }




    // UPDATES existing user
    public void updateUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

}
