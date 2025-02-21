-- -----------------------------------------------------
-- Schema A4_Akanbiad
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `A4_Akanbiad` ;

-- -----------------------------------------------------
-- Schema A4_Akanbiad
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `A4_Akanbiad` DEFAULT CHARACTER SET utf8 ;
USE `A4_Akanbiad` ;

-- -----------------------------------------------------
-- Table `A4_Akanbiad`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A4_Akanbiad`.`User` (
  `userId` BIGINT NOT NULL auto_increment,
  `email` VARCHAR(45) NULL,
  `encryptedPassword` VARCHAR(128) NULL,
  `enabled` BIT NULL,
  PRIMARY KEY (`userId`)
);


-- -----------------------------------------------------
-- Table `A4_Akanbiad`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A4_Akanbiad`.`Role` (
  `roleId` BIGINT NOT NULL auto_increment,
  `roleName` VARCHAR(45) NULL,
  PRIMARY KEY (`roleId`)
);


-- -----------------------------------------------------
-- Table `A4_Akanbiad`.`User_Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A4_Akanbiad`.`User_Role` (
  `userId` BIGINT NOT NULL,
  `roleId` BIGINT NOT NULL,
  PRIMARY KEY (`userId`, `roleId`),
  
    FOREIGN KEY (`userId`)
    REFERENCES `A4_Akanbiad`.`User` (`userId`)
    ON DELETE CASCADE,
 
    FOREIGN KEY (`roleId`)
    REFERENCES `A4_Akanbiad`.`Role` (`roleId`)
    ON DELETE CASCADE
);


-- -----------------------------------------------------
-- Table `A4_Akanbiad`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A4_Akanbiad`.`Student` (
  `studentId` BIGINT NOT NULL auto_increment,
  `firstName` VARCHAR(45) NULL,
  `LastName` VARCHAR(45) NULL,
  `userId` BIGINT NULL,
  PRIMARY KEY (`studentId`),
    FOREIGN KEY (`userId`)
    REFERENCES `A4_Akanbiad`.`User` (`userId`)
    ON DELETE CASCADE
);


-- -----------------------------------------------------
-- Table `A4_Akanbiad`.`Course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A4_Akanbiad`.`Course` (
  `courseId` BIGINT NOT NULL auto_increment,
  `courseName` VARCHAR(45) NULL,
  PRIMARY KEY (`courseId`)
);


-- -----------------------------------------------------
-- Table `A4_Akanbiad`.`Enrollment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A4_Akanbiad`.`Enrollment` (
  `studentId` BIGINT NOT NULL,
  `courseId` BIGINT NOT NULL,
  `termName` VARCHAR(45) NOT NULL,
  `grade` VARCHAR(45) NULL,
  `enrollDate` DATE NULL,
  PRIMARY KEY (`studentId`, `courseId`),
	FOREIGN KEY (`studentId`)
    REFERENCES `A4_Akanbiad`.`Student` (`studentId`)
    ON DELETE CASCADE,
 
    FOREIGN KEY (`courseId`)
    REFERENCES `A4_Akanbiad`.`Course` (`courseId`)
    ON DELETE CASCADE
);



-------------------------------------------------------------------------------------------------------------------------------------

--INSERT RECORD STATEMENTS

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






