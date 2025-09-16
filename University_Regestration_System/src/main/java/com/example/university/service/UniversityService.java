package com.example.university.service;

import com.example.university.dao.*;
import com.example.university.dto.StudentCourseJoin;
import com.example.university.model.Course;
import com.example.university.model.Registration;
import com.example.university.model.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniversityService {

    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    private final RegistrationDAO registrationDAO;

    public UniversityService() {
        this.studentDAO = new StudentDAOImpl();
        this.courseDAO = new CourseDAOImpl();
        this.registrationDAO = new RegistrationDAOImpl();
    }

    // 1. Add Student
    public Student addStudent(String name, int year) {
        Student s = new Student(name, year);
        int id = studentDAO.addStudent(s);
        s.setStudentId(id);
        return s;
    }

    // 2. Add Course
    public Course addCourse(String title, int credits) {
        Course c = new Course(title, credits);
        int id = courseDAO.addCourse(c);
        c.setCourseId(id);
        return c;
    }

    // 3. Register Student for Course
    public boolean registerStudentForCourse(int studentId, int courseId) {
        return registrationDAO.registerStudentToCourse(new Registration(studentId, courseId));
    }

    // 4. View All Students with Registered Courses
    //    DAO performs JOIN; Service groups by Student using Streams
    public Map<Student, List<Course>> getStudentsWithCourses() {
        List<StudentCourseJoin> join = registrationDAO.fetchStudentCourseJoin();

        // build Student and Course objects per row
        return join.stream()
                .collect(Collectors.groupingBy(
                        j -> new Student(j.getStudentId(), j.getStudentName(), j.getYear()),
                        Collectors.mapping(
                                j -> new Course(j.getCourseId(), j.getCourseTitle(), j.getCredits()),
                                Collectors.collectingAndThen(Collectors.toList(), list ->
                                        list.stream()
                                                .collect(Collectors.collectingAndThen(
                                                        Collectors.toMap(Course::getCourseId, Function.identity(), (a,b)->a, LinkedHashMap::new),
                                                        m -> new ArrayList<>(m.values())
                                                ))
                                )
                        )
                ));
    }

    // 5. Search Courses by Minimum Credit Requirement
    public List<Course> searchCoursesByMinCredits(int minCredits) {
        return courseDAO.getAllCourses().stream()
                .filter(c -> c.getCredits() >= minCredits)
                .sorted(Comparator.comparing(Course::getCredits).thenComparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    // 6. Get Students Registered in a Particular Course
    public List<Student> getStudentsByCourse(int courseId) {
        List<StudentCourseJoin> join = registrationDAO.fetchStudentCourseJoin();
        return join.stream()
                .filter(j -> j.getCourseId() == courseId)
                .map(j -> new Student(j.getStudentId(), j.getStudentName(), j.getYear()))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Student::getStudentId, Function.identity(), (a,b)->a, LinkedHashMap::new),
                        m -> new ArrayList<>(m.values())
                ));
    }

    // 7. Sort Students by Year and then by Name
    public List<Student> sortStudentsByYearThenName() {
        return studentDAO.getAllStudents().stream()
                .sorted(Comparator.comparingInt(Student::getYear).thenComparing(Student::getName))
                .collect(Collectors.toList());
    }

    // 8. Calculate Total Credits Per Student
    public Map<Student, Integer> totalCreditsPerStudent() {
        List<StudentCourseJoin> join = registrationDAO.fetchStudentCourseJoin();
        return join.stream()
                .collect(Collectors.groupingBy(
                        j -> new Student(j.getStudentId(), j.getStudentName(), j.getYear()),
                        Collectors.summingInt(StudentCourseJoin::getCredits)
                ));
    }

    // Helpers for controller
    public List<Student> getAllStudents() { return studentDAO.getAllStudents(); }
    public List<Course> getAllCourses() { return courseDAO.getAllCourses(); }
    public Course findCourse(int id) { return courseDAO.findById(id); }
    public Student findStudent(int id) { return studentDAO.findById(id); }
}
