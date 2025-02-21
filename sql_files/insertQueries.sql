use a4_akanbiad;

insert into course(courseName) values
('Systems Design'),
('Computer Security'),
('C Programming'),
('Java EE');

insert into role(roleName) values
('ROLE_GUEST'),
('ROLE_USER'),
('ROLE_ADMIN'),
('ROLE_TEACHER');

INSERT INTO user (email, encryptedPassword, enabled) VALUES 
('user1@user.com', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1),
('user2@user.com', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1),
('admin@admin.com', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1),
('teacher@teacher.com', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO student (firstName, lastName, userId) VALUES 
('Paul', 'Jackson', 1),
('Mary', 'Philips', 2),
('Admin', 'Lampard', 3),
('Teacher', 'Jerry', 4);

INSERT INTO user_role (userId, roleId) VALUES 
(1, 2),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO enrollment (studentId, courseId, termName, grade, enrollDate) VALUES 
(1, 1, 'F2023', 'A-', CURRENT_DATE() ),
(1, 2, 'F2023', 'B+', CURRENT_DATE() ),
(2, 1, 'F2023', 'A+', CURRENT_DATE() ),
(2, 2, 'F2023', 'C', CURRENT_DATE() );



