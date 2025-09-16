package com.example.university.model;

import java.util.Objects;

public class Student {
    private int studentId;
    private String name;
    private int year;

    public Student() { }

    public Student(int studentId, String name, int year) {
        this.studentId = studentId;
        this.name = name;
        this.year = year;
    }

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
