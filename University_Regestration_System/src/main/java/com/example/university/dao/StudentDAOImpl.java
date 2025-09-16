package com.example.university.dao;

import com.example.university.model.Student;
import com.example.university.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public int addStudent(Student student) {
        String sql = "INSERT INTO students(name, academic_year) VALUES(?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getYear());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    student.setStudentId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding student", e);
        }
        return -1;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT student_id, name, academic_year FROM students";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Student(rs.getInt("student_id"), rs.getString("name"), rs.getInt("academic_year")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students", e);
        }
        return list;
    }

    @Override
    public Student findById(int id) {
        String sql = "SELECT student_id, name, academic_year FROM students WHERE student_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getInt("student_id"), rs.getString("name"), rs.getInt("academic_year"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding student", e);
        }
        return null;
    }
}
