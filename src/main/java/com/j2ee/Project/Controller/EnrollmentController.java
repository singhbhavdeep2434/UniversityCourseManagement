package com.j2ee.Project.Controller;

import com.j2ee.Project.Model.Enrollment;
import com.j2ee.Project.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
// @CrossOrigin("http://127.0.0.1:5500")
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @GetMapping("enrollments")
    public List<Enrollment> getAllEnrollments() {    // READ all enrollments
        return enrollmentService.getAllEnrollments();
    }


//    @PostMapping("enrollment")
//    public Enrollment addEnrollment(@RequestBody Enrollment enrollment) {  // CREATE
//        enrollmentService.addEnrollment(enrollment);
//        return enrollmentService.getEnrollment(enrollment.getId());
//    }

    @PutMapping("enrollment")
    public Enrollment updateEnrollment(@RequestBody Enrollment enrollment) { // UPDATE
        enrollmentService.updateEnrollment(enrollment);
        return enrollmentService.getEnrollment(enrollment.getId());
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
    @PostMapping("enrollment")
    public Enrollment enrollStudent(@RequestBody Enrollment enrollment) {
        return enrollmentService.enrollStudentInCourse(enrollment);
    }

    // Endpoint to get enrollments by student ID
    @GetMapping("enrollments/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable int studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    // Endpoint to get enrollments by course ID
    @GetMapping("enrollments/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }

}


