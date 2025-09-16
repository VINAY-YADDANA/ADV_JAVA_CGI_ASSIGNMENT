package com.example.university.dao;

import com.example.university.model.Registration;
import com.example.university.dto.StudentCourseJoin;
import com.example.university.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {

    @Override
    public boolean registerStudentToCourse(Registration registration) {
        String sql = "INSERT INTO registrations(student_id, course_id) VALUES(?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, registration.getStudentId());
            ps.setInt(2, registration.getCourseId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // If duplicate primary key (already registered), return false instead of crashing
            if (e.getSQLState() != null && (e.getSQLState().startsWith("23"))) {
                return false;
            }
            throw new RuntimeException("Error registering student to course", e);
        }
    }

    @Override
    public List<Registration> getAllRegistrations() {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT student_id, course_id FROM registrations";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Registration(rs.getInt("student_id"), rs.getInt("course_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching registrations", e);
        }
        return list;
    }

    @Override
    public List<StudentCourseJoin> fetchStudentCourseJoin() {
        List<StudentCourseJoin> list = new ArrayList<>();
        String sql = "SELECT s.student_id, s.name, s.academic_year, c.course_id, c.title, c.credits " +
                "FROM registrations r " +
                "JOIN students s ON r.student_id = s.student_id " +
                "JOIN courses c ON r.course_id = c.course_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new StudentCourseJoin(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student-course join", e);
        }
        return list;
    }
}
