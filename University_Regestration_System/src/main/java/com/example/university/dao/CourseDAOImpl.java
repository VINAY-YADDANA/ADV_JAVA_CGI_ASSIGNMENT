package com.example.university.dao;

import com.example.university.model.Course;
import com.example.university.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public int addCourse(Course course) {
        String sql = "INSERT INTO courses(title, credits) VALUES(?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getTitle());
            ps.setInt(2, course.getCredits());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    course.setCourseId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding course", e);
        }
        return -1;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT course_id, title, credits FROM courses";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Course(rs.getInt("course_id"), rs.getString("title"), rs.getInt("credits")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching courses", e);
        }
        return list;
    }

    @Override
    public Course findById(int id) {
        String sql = "SELECT course_id, title, credits FROM courses WHERE course_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course(rs.getInt("course_id"), rs.getString("title"), rs.getInt("credits"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding course", e);
        }
        return null;
    }
}
