package com.j2ee.Project.Controller;

import com.j2ee.Project.Model.Course;
import com.j2ee.Project.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin("http://localhost:5173")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("courses")
    public List<Course> getAllCourses() {    // READ
        return courseService.returnCourses();
    }

    @PostMapping("course")
    public Course addCourse(@RequestBody Course course) {      // CREATE
        courseService.addCourse(course);
        return courseService.getCourse(course.getId());
    }

    @PutMapping("course")
    public Course updateCourse(@RequestBody Course course) {      // UPDATE
        courseService.updateCourse(course);
        return courseService.getCourse(course.getId());
    }

    @GetMapping("course/{courseId}")                 // SEARCH BY ID
    public Course getCourse(@PathVariable("courseId") int id) {
        return courseService.getCourse(id);
    }

    @DeleteMapping("course/{courseId}")            // DELETE course
    public String deleteCourse(@PathVariable("courseId") int id) {
        courseService.deleteCourse(id);
        return "Course Deleted";
    }
}
