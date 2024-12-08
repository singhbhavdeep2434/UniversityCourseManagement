package com.j2ee.Project.Service;

import com.j2ee.Project.Enum.Role;

import com.j2ee.Project.Model.Enrollment;
import com.j2ee.Project.Repo.CourseRepository;
import com.j2ee.Project.Repo.EnrollmentRepository;
import com.j2ee.Project.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    UserRepository userRepo;

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
    public Enrollment updateEnrollment(Enrollment enrollment) {
        // Ensure that the enrollment exists
        getEnrollment(enrollment.getId());
        return enrollmentRepo.save(enrollment);
    }

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
}
