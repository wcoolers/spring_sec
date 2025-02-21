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

