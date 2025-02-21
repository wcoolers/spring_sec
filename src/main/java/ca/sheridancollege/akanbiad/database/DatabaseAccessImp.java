/**
 * This is the Database Implementation Class, it implements the DatabaseAccess Interface
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.database;

import java.util.List;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.akanbiad.beans.Student;
import ca.sheridancollege.akanbiad.beans.Transcript;
import ca.sheridancollege.akanbiad.beans.Course;
import ca.sheridancollege.akanbiad.beans.Enrollment;
import ca.sheridancollege.akanbiad.beans.User;

@Repository
public class DatabaseAccessImp implements DatabaseAccess{
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	public void addUser(String email, String password) {
		MapSqlParameterSource namedParameters = new	MapSqlParameterSource();
		String query = "INSERT INTO `user` "
				+ "(email, encryptedPassword, enabled) "
				+ "VALUES (:email, :encryptedPassword, 1)";
		namedParameters.addValue("email", email);
		namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		
		jdbc.update(query, namedParameters);
	}
	
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO `user_role` (userId, roleId) "
				+ "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);
		
		jdbc.update(query, namedParameters);
	}


	public User findUserAccount(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM `user` WHERE email = :email";
		namedParameters.addValue("email", email);
		
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<User>(User.class));
			
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	public List<String> getRolesById(Long userId){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT `role`.roleName " 
				+ "FROM `user_role`, `role` " 
				+ "WHERE `user_role`.roleId = `role`.roleId "
				+ "AND userId = :userId";
		
		namedParameters.addValue("userId", userId);
		
		return jdbc.queryForList(query, namedParameters, String.class);
	}
	
	//GET COURSE LIST
	public List<Course> getCourseList() {
		MapSqlParameterSource namedParameters= new MapSqlParameterSource();
		String query = "SELECT * FROM course";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Course>(Course.class));
	}
	
	//FIND STUDENT
	public Student findStudent(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM `student` WHERE userId = :userId";
		namedParameters.addValue("userId", userId);
		
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Student>(Student.class));
			
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	//INSERT STUDENT
	public void insertStudent(String firstname, String lastname, Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "INSERT INTO `student` (firstName, lastName, userId) VALUES (:firstName, :lastName, :userId)";
		
		namedParameters.addValue("firstName", firstname);
		namedParameters.addValue("lastName", lastname); 
		namedParameters.addValue("userId", userId); 
		
		 jdbc.update(query, namedParameters);
		 
	}
	
	
	
	//ENROLL STUDENT IN A COURSE
	public void enrollCourse(Long studentId, Long courseId, String termName, String enrollDate) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
				
		String query = "INSERT INTO `enrollment` (studentId, courseId, termName, enrollDate) VALUES (:studentId, :courseId, :termName, :enrollDate)"; 
		
		
		namedParameters.addValue("studentId", studentId);
		namedParameters.addValue("courseId", courseId); 
		namedParameters.addValue("termName", termName); 
		namedParameters.addValue("enrollDate", enrollDate); 
				
	    jdbc.update(query, namedParameters);
	    
	}
	
	//GRADE STUDENT
	public void gradeStudent(Long studentId, Long courseId, String grade) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "UPDATE `enrollment` SET grade = :grade where studentId = :studentId and courseId = :courseId"; 
		
		
		namedParameters.addValue("studentId", studentId);
		namedParameters.addValue("courseId", courseId); 
		namedParameters.addValue("grade", grade); 
						
	    jdbc.update(query, namedParameters);
	}
	
	//GET STUDENT ENROLLMENT COUNT
		public int getStudentEnrollmentCount(Long studentId) {
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			
			
			String query = "select * from enrollment where studentId = :studentId";
			namedParameters.addValue("studentId", studentId);
			
			
			List<Enrollment> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Enrollment>(Enrollment.class));
			
			
			return result == null? 0 : result.size();	
			
		}
	
	//GET STUDENT ENROLLMENT RECORD
	public Enrollment getStudentEnrollment(Long studentId, Long courseId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		//UPDATE THIS QUERY
		String query = "select * from enrollment where studentId = :studentId and courseId = :courseId";
		namedParameters.addValue("studentId", studentId);
		namedParameters.addValue("courseId", courseId);
		
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Enrollment>(Enrollment.class));
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}	
		
	}

	
	
	//GET COURSE
	public Course getCourseById(Long courseId){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM `course` WHERE courseId = :courseId";
		
		namedParameters.addValue("courseId", courseId);
		
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Course>(Course.class));
			
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}

	}
	
	//GET STUDENT TRANSCRIPT
	public List<Transcript> getTranscript(Long studentId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "select c.courseName, e.grade, e.termName from enrollment e join course c on c.courseId = e.courseId where studentId = :studentId and e.grade is not null";
		
		namedParameters.addValue("studentId", studentId);
		
		try {
			return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Transcript>(Transcript.class));
			
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	public Student getStudentByStudentId(Long studentId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "select * from student where studentId = :studentId";
		
		namedParameters.addValue("studentId", studentId);
		
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Student>(Student.class));
			
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
}
