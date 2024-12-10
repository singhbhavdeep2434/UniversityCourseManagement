package com.j2ee.Project.Service;

//public class CourseNotFoundException extends X {
//    public CourseNotFoundException(String courseNotFound) {
//    }
//}
//
//
//package com.j2ee.Project.Exception;  // Make sure this matches your project structure

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message) {
        super(message);
    }
}
