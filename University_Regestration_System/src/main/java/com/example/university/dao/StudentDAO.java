package com.example.university.dao;

import com.example.university.model.Student;
import java.util.List;

public interface StudentDAO {
    int addStudent(Student student);
    List<Student> getAllStudents();
    Student findById(int id);
}
