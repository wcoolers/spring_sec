/**
 * This is the Database Access Interface, with methods to be implemented in the Database subclass.
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.database;

import java.util.List;

import ca.sheridancollege.akanbiad.beans.Student;
import ca.sheridancollege.akanbiad.beans.Transcript;
import ca.sheridancollege.akanbiad.beans.Course;
import ca.sheridancollege.akanbiad.beans.Enrollment;
import ca.sheridancollege.akanbiad.beans.User;

public interface DatabaseAccess {
	public void addUser(String email, String password);
	public void addRole(Long userId, Long roleId);
	public User findUserAccount(String email);
	public List<String> getRolesById(Long userId);
	public List<Course> getCourseList();
	public void insertStudent(String firstname, String lastname, Long userId);
	public Student findStudent(Long userId);
	public Course getCourseById(Long courseId);
	public void enrollCourse(Long studentId, Long courseId, String termName, String enrollDate);
	public Enrollment getStudentEnrollment(Long studentId, Long courseId);
	public void gradeStudent(Long studentId, Long courseId, String grade);
	public int getStudentEnrollmentCount(Long studentId);
	public List<Transcript> getTranscript(Long studentId);
	public Student getStudentByStudentId(Long studentId);
}
