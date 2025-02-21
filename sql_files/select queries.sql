use a4_akanbiad;

select * from `enrollment`;
select * from user_role;
select * from role;
select * from course;
select * from student;
select * from user;

select c.courseName, e.grade, e.termName from enrollment e 
join course c 
on c.courseId = e.courseId
where studentId = 3 and e.grade is not null;

select count(*) from enrollment 
where studentId = 3;