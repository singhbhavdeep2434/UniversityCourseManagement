package com.j2ee.Project.Repo;

import com.j2ee.Project.Enum.Role;
import com.j2ee.Project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

     @Query("SELECT u FROM User u WHERE u.role = com.j2ee.Project.Enum.Role.STUDENT")
     List<User> findStudents();

     User findByUsername(String username);
     User findById(int id);

    // List<User> findByRole(Role role);
}
