package com.example.university.model;

import java.util.Objects;

public class Course {
    private int courseId;
    private String title;
    private int credits;

    public Course() { }

    public Course(int courseId, String title, int credits) {
        this.courseId = courseId;
        this.title = title;
        this.credits = credits;
    }

    public Course(String title, int credits) {
        this.title = title;
        this.credits = credits;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                '}';
    }
}
