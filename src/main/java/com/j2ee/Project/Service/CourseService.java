package com.j2ee.Project.Service;
import com.j2ee.Project.Model.Course;
import com.j2ee.Project.Repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {


    @Autowired
    CourseRepository courseRepo;


    // READ all courses and return
    public List<Course> returnCourses() {
        return courseRepo.findAll();
    }

    // CREATE new course
    public void addCourse(Course course) {
        courseRepo.save(course);
    }

    // FETCH course by id

    public Course getCourse(int id) {
        try {
            Optional<Course> optionalCourse = courseRepo.findById(id);
            if (optionalCourse.isPresent()) {
                return optionalCourse.get(); // Get the course object from the Optional
            } else {
                throw new RuntimeException("course not found for id :: " + id); // Handle the case where the course is not found
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the course: " + e.getMessage(), e);
        }
    }

    // UPDATES existing course
    public void updateCourse(Course course) {
        courseRepo.save(course);
    }

    public void deleteCourse(int id) {
        courseRepo.deleteById(id);
    }

}
