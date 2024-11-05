package com.j2ee.Project.Repo;

import com.j2ee.Project.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByStudentId(int studentId); // Method to find enrollments by student ID
    List<Enrollment> findByCourseId(int courseId);   // Method to find enrollments by course ID

}
