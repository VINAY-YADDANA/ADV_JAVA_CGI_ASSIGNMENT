INSERT INTO students(name, academic_year) VALUES
('Alice', 1),
('Bob', 2),
('Charlie', 2),
('Diana', 3);

INSERT INTO courses(title, credits) VALUES
('Data Structures', 4),
('Databases', 3),
('Operating Systems', 4),
('Discrete Mathematics', 3),
('Algorithms', 4);

-- Sample registrations
INSERT INTO registrations(student_id, course_id) VALUES
(1,1),(1,2),(1,5),
(2,2),(2,3),
(3,1),
(4,4),(4,5);
