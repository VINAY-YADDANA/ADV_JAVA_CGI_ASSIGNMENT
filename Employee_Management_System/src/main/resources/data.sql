INSERT INTO employee (name, email, department) VALUES
('Alice Johnson', 'alice@example.com', 'Engineering'),
('Bob Smith', 'bob@example.com', 'HR'),
('Charlie Lee', 'charlie@example.com', 'Finance');

INSERT INTO leave_request (employee_id, leave_type, start_date, end_date, status) VALUES
(1, 'SICK', '2025-09-10', '2025-09-12', 'PENDING'),
(2, 'CASUAL', '2025-09-18', '2025-09-19', 'APPROVED'),
(3, 'PAID', '2025-10-01', '2025-10-05', 'PENDING');
