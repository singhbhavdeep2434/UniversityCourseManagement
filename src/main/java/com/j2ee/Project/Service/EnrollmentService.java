package com.j2ee.Project.Service;

import com.j2ee.Project.Enum.Role;

import com.j2ee.Project.Model.Course;
import com.j2ee.Project.Model.Enrollment;
import com.j2ee.Project.Model.User;
import com.j2ee.Project.Repo.CourseRepository;
import com.j2ee.Project.Repo.EnrollmentRepository;
import com.j2ee.Project.Repo.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;

    @Value("${jwt.secret}")
    String SECRET;

    @Autowired
    CourseRepository courseRepo;

    User user;

    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepo) {
        this.enrollmentRepo = enrollmentRepo;
    }

    // Create a new enrollment
    public Enrollment addEnrollment(Enrollment enrollment) {
        if(enrollment.getStudent().getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Only students can enroll in courses");
        }
        return enrollmentRepo.save(enrollment);
    }

    // Retrieve all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepo.findAll();
    }

    // Retrieve an enrollment by ID
    public Enrollment getEnrollment(int enrollmentId) {
        return enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    // Update an existing enrollment
//    public Enrollment updateEnrollment(Enrollment enrollment) {
//        // Ensure that the enrollment exists
//        getEnrollment(enrollment.getId());
//        return enrollmentRepo.save(enrollment);
//    }

    // Delete an enrollment
    public void deleteEnrollment(int enrollmentId) {
        enrollmentRepo.deleteById(enrollmentId);
    }

    // NEED TO BE SET IN THE CONTROLLER: Methods to get enrollments by student or course

    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        try {
            List<Enrollment> enrollments = enrollmentRepo.findByStudentId(studentId);
            if (enrollments.isEmpty()) {
                throw new RuntimeException("No enrollments found for student with ID: " + studentId);
            }
            return enrollments;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving enrollments for student with ID: " + studentId, e);
        }
    }

    public List<Enrollment> getEnrollmentsByCourse(int courseId) {
        try {
            List<Enrollment> enrollments = enrollmentRepo.findByCourseId(courseId);
            if (enrollments.isEmpty()) {
                throw new RuntimeException("No enrollments found for course with ID: " + courseId);
            }
            return enrollments;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving enrollments for course with ID: " + courseId, e);
        }

        // Update enrollment grade
//    public void updateEnrollmentGrade(int enrollmentId, String grade) {
//        Enrollment enrollment = getEnrollment(enrollmentId);
//        enrollment.setGrade(grade);
//        enrollmentRepo.save(enrollment);
//    }
    }

    public Enrollment enrollStudentInCourse(Enrollment enrollment) {
        return enrollmentRepo.save(enrollment);
    }

    public Enrollment enrollInCourse(Course course, String token) {
        // Get the logged-in user (from JWT token)
        String username = getUsernameFromToken(token); // Decoding JWT token to extract the username or userId

        User loggedInUser = userRepo.findByUsername(username);

        // Enroll the user in the course
        // Course cou = courseRepo.findById(course.getId());

        Course cou = courseRepo.findById(course.getId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));



        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(loggedInUser);
        enrollment.setCourse(cou);
        enrollment.setGrade(" "); // or set it based on business logic

        return enrollmentRepo.save(enrollment); // Save enrollment to database

        // Return the enrollment object
        // return enrollment;

    }

    private String getUsernameFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove "Bearer " prefix
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)  // Using the JwtService's secret key
                    .build().parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();  // Username or user ID typically
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }


    public List<Enrollment> getEnrollmentsByUsername(String username) {
        int studentId = userRepo.findByUsername(username).getId();
        return enrollmentRepo.findByStudentId(studentId);
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {
        Enrollment existingEnrollment = enrollmentRepo.findById(enrollment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + enrollment.getId()));

        // Update the existing enrollment with new values
        existingEnrollment.setGrade(enrollment.getGrade());
//        existingEnrollment.setCourse(enrollment.getCourse());
//        existingEnrollment.setStudent(enrollment.getStudent());

        return enrollmentRepo.save(existingEnrollment);
    }

}
