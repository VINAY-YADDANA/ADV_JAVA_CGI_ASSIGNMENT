package com.example.university.dto;

public class StudentCourseJoin {
    private int studentId;
    private String studentName;
    private int year;
    private int courseId;
    private String courseTitle;
    private int credits;

    public StudentCourseJoin(int studentId, String studentName, int year, int courseId, String courseTitle, int credits) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.year = year;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.credits = credits;
    }

    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public int getYear() { return year; }
    public int getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }
    public int getCredits() { return credits; }
}
