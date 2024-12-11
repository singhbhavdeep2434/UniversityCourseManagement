package com.j2ee.Project.Controller;

import com.j2ee.Project.Model.Course;
import com.j2ee.Project.Model.Enrollment;
import com.j2ee.Project.Model.User;
import com.j2ee.Project.Repo.CourseRepository;
import com.j2ee.Project.Repo.EnrollmentRepository;
import com.j2ee.Project.Repo.UserRepository;
import com.j2ee.Project.Service.EnrollmentService;
import com.j2ee.Project.Service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// @CrossOrigin("http://localhost:3000")
// @CrossOrigin("http://localhost:5173")
public class EnrollmentController {

    @Autowired
    EnrollmentRepository enrollmentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CourseRepository courseRepo;



    @Autowired
    JwtService jwtService;

    @Autowired
    EnrollmentService enrollmentService;

    @GetMapping("enrollments")
    public List<Enrollment> getAllEnrollments() {    // READ all enrollments
        return enrollmentService.getAllEnrollments();
    }


    @PutMapping("enrollment")                  // UPDATE
    public Enrollment updateEnrollment(@RequestBody Enrollment enrollment) { // UPDATE
        enrollmentService.updateEnrollment(enrollment);
        return enrollmentService.getEnrollment(enrollment.getId());
    }

    @PutMapping("enrollment/{id}")
    public Enrollment updateEnrollment(@PathVariable int id, @RequestBody Enrollment updatedEnrollment) {
        // Fetch the existing enrollment from the database
        Enrollment existingEnrollment = enrollmentService.getEnrollment(id);

        if (existingEnrollment == null) {
            throw new EntityNotFoundException("Enrollment with ID " + id + " not found");
        }

        // Update the fields in the existing enrollment
        existingEnrollment.setGrade(updatedEnrollment.getGrade());
//        existingEnrollment.setCourse(updatedEnrollment.getCourse());
//        existingEnrollment.setStudent(updatedEnrollment.getStudent());

        // Save the updated enrollment
        return enrollmentService.updateEnrollment(existingEnrollment);
    }


    @GetMapping("enrollment/{enrollmentId}")  // SEARCH BY ID
    public Enrollment getEnrollment(@PathVariable("enrollmentId") int id) {
        return enrollmentService.getEnrollment(id);
    }

    @DeleteMapping("enrollment/{enrollmentId}")  // DELETE enrollment
    public String deleteEnrollment(@PathVariable("enrollmentId") int id) {
        enrollmentService.deleteEnrollment(id);
        return "Enrollment Deleted";
    }


    // Methods to handle enrollments with studentId and CourseId

    // Endpoint to enroll a student in a course
//    @PostMapping("enrollment")                         // CREATE
//    public Enrollment enrollStudent(@RequestBody Enrollment enrollment) {
//        return enrollmentService.enrollStudentInCourse(enrollment);
//    }

    @PostMapping("enrollment")                         // CREATE
    public Enrollment enrollStudent(@RequestBody Enrollment enrollment) {
        // Get the student and course from the database using the IDs
        User student = userRepo.findById(enrollment.getStudent().getId());

        Optional<Course> course = courseRepo.findById(enrollment.getCourse().getId());


        // Create a new Enrollment object
        Enrollment newEnrollment = new Enrollment(student, course, enrollment.getGrade());

        // Save the enrollment to the database
        return enrollmentRepo.save(newEnrollment);
    }



    @PostMapping("enroll")
    public Enrollment enroll(@RequestBody Course course, @RequestHeader("Authorization") String token) {
        return enrollmentService.enrollInCourse(course, token);

    }

    @GetMapping("/my-enrollments")
    public List<Enrollment> getUserEnrollments(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUserName(token);
        return enrollmentService.getEnrollmentsByUsername(username);
    }




//    @PostMapping("enrollment")
//    public Enrollment addEnrollment(@RequestBody Enrollment enrollment) {  // CREATE
//        enrollmentService.addEnrollment(enrollment);
//        return enrollmentService.getEnrollment(enrollment.getId());
//    }


    // Endpoint to get enrollments by student ID
    @GetMapping("enrollments/student/{studentId}")          // Returns enrollment by student ID
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable int studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    // Endpoint to get enrollments by course ID
    @GetMapping("enrollments/course/{courseId}")          // Returns enrollment by course ID
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }

}


