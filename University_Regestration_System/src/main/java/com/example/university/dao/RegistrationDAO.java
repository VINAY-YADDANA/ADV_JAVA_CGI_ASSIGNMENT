package com.example.university.dao;

import com.example.university.model.Registration;
import com.example.university.dto.StudentCourseJoin;

import java.util.List;

public interface RegistrationDAO {
    boolean registerStudentToCourse(Registration registration);
    List<Registration> getAllRegistrations();
    List<StudentCourseJoin> fetchStudentCourseJoin();
}
