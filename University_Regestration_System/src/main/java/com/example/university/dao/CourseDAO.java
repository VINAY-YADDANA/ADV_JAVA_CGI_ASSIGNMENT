package com.example.university.dao;

import com.example.university.model.Course;
import java.util.List;

public interface CourseDAO {
    int addCourse(Course course);
    List<Course> getAllCourses();
    Course findById(int id);
}
