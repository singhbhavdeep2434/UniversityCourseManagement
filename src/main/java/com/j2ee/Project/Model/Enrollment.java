package com.j2ee.Project.Model;


import com.j2ee.Project.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Data
@AllArgsConstructor
@Table(name = "enrollments")
@Component
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String grade;

    public Enrollment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    // Custom constructor for creating enrollment without an ID
    public Enrollment(User student, Course course, String grade) {
        if (student.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Only students can enroll in courses.");
        }
        this.student = student;
        this.course = course;
        this.grade = grade;
    }


}
