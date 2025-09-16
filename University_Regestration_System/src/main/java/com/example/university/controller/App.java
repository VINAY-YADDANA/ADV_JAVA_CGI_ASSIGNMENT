package com.example.university.controller;

import org.h2.tools.Server;
import com.example.university.model.Course;
import com.example.university.model.Student;
import com.example.university.service.UniversityService;

import java.sql.SQLException;
import java.util.*;

public class App {

    private final UniversityService service;
    private final Scanner scanner;

    public App() {
        this.service = new UniversityService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws SQLException {
        new App().run();
    }

    private void run() throws SQLException {

        System.out.println("UNIVERSITY COURSE REGISTRATION");
        Server webServer = Server.createWebServer("-web", "-webPort", "8888").start();
        System.out.println("H2 Console at http://localhost:8888");
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": addCourse(); break;
                    case "3": registerStudentForCourse(); break;
                    case "4": viewAllStudentsWithCourses(); break;
                    case "5": searchCoursesByMinCredits(); break;
                    case "6": getStudentsRegisteredInCourse(); break;
                    case "7": sortStudentsByYearThenName(); break;
                    case "8": totalCreditsPerStudent(); break;
                    case "9": listAllStudentsAndCourses(); break;
                    case "0":
                        System.out.println("See you soon.....");
                        webServer.stop();
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Register Student for Course");
        System.out.println("4. View All Students with Registered Courses");
        System.out.println("5. Search Courses by Minimum Credit Requirement");
        System.out.println("6. Get Students Registered in a Particular Course");
        System.out.println("7. Sort Students by Year and then by Name");
        System.out.println("8. Calculate Total Credits Per Student");
        System.out.println("9. View Master Lists (All Students / All Courses)");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter year (e.g., 1-4): ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        Student s = service.addStudent(name, year);
        System.out.println("Added: " + s);
    }

    private void addCourse() {
        System.out.print("Enter course title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter credits: ");
        int credits = Integer.parseInt(scanner.nextLine().trim());
        Course c = service.addCourse(title, credits);
        System.out.println("Added: " + c);
    }

    private void registerStudentForCourse() {
        System.out.print("Enter student ID: ");
        int sid = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter course ID: ");
        int cid = Integer.parseInt(scanner.nextLine().trim());
        boolean ok = service.registerStudentForCourse(sid, cid);
        if (ok) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Student already registered for this course (or constraint violation).");
        }
    }

    private void viewAllStudentsWithCourses() {
        Map<Student, List<Course>> map = service.getStudentsWithCourses();
        if (map.isEmpty()) {
            System.out.println("No registrations yet.");
            return;
        }
        map.forEach((student, courses) -> {
            System.out.println(student.getStudentId() + " - " + student.getName() + " (Year " + student.getYear() + ")");
            if (courses.isEmpty()) {
                System.out.println("  [No courses]");
            } else {
                courses.forEach(c -> System.out.println("  - " + c.getCourseId() + ": " + c.getTitle() + " (" + c.getCredits() + " cr)"));
            }
        });
    }

    private void searchCoursesByMinCredits() {
        System.out.print("Minimum credits: ");
        int min = Integer.parseInt(scanner.nextLine().trim());
        List<Course> courses = service.searchCoursesByMinCredits(min);
        if (courses.isEmpty()) {
            System.out.println("No courses found with credits >= " + min);
        } else {
            courses.forEach(c -> System.out.println(c.getCourseId() + " - " + c.getTitle() + " (" + c.getCredits() + " cr)"));
        }
    }

    private void getStudentsRegisteredInCourse() {
        System.out.print("Enter course ID: ");
        int cid = Integer.parseInt(scanner.nextLine().trim());
        List<Student> students = service.getStudentsByCourse(cid);
        if (students.isEmpty()) {
            System.out.println("No students found for course ID " + cid);
        } else {
            students.forEach(s -> System.out.println(s.getStudentId() + " - " + s.getName() + " (Year " + s.getYear() + ")"));
        }
    }

    private void sortStudentsByYearThenName() {
        List<Student> students = service.sortStudentsByYearThenName();
        students.forEach(s -> System.out.println(s.getStudentId() + " - " + s.getName() + " (Year " + s.getYear() + ")"));
    }

    private void totalCreditsPerStudent() {
        Map<Student, Integer> totals = service.totalCreditsPerStudent();
        if (totals.isEmpty()) {
            System.out.println("No registrations found.");
        } else {
            totals.entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getKey().getStudentId()))
                    .forEach(e -> System.out.println(e.getKey().getStudentId() + " - " + e.getKey().getName()
                            + " => " + e.getValue() + " total credits"));
        }
    }

    private void listAllStudentsAndCourses() {
        System.out.println("-- Students --");
        List<Student> students = service.getAllStudents();
        students.forEach(s -> System.out.println(s.getStudentId() + " - " + s.getName() + " (Year " + s.getYear() + ")"));
        System.out.println("-- Courses --");
        List<Course> courses = service.getAllCourses();
        courses.forEach(c -> System.out.println(c.getCourseId() + " - " + c.getTitle() + " (" + c.getCredits() + " cr)"));
    }
}
