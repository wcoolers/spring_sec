/**
 * This is the Home Controller. Helps to manage routing
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.controllers;


import java.time.LocalDate;
import java.util.ArrayList;





import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.akanbiad.beans.Student;
import ca.sheridancollege.akanbiad.beans.Transcript;
import ca.sheridancollege.akanbiad.beans.Course;
import ca.sheridancollege.akanbiad.beans.Enrollment;
import ca.sheridancollege.akanbiad.database.DatabaseAccess;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@Autowired
	private UserDetailsService userService;
	
	
	//UNPROTECTED ROUTES
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password) {
		da.addUser(username, password);
		Long userId = da.findUserAccount(username).getUserId();
		da.addRole(userId, Long.valueOf(2));
		
		return "redirect:/login";
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}
	

	//SECURE ROUTES FOR /SECURE PATH
	@GetMapping("/secure")
	public String getSecureIndex(Model model, Authentication authentication) {
		
		Long userId = da.findUserAccount(authentication.getName()).getUserId();
		Student student = da.findStudent(userId);
		
		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		
		if (student != null) {
			int count  = da.getStudentEnrollmentCount(student.getStudentId());
			model.addAttribute("count", count);
			
		}
		model.addAttribute("roleList", roleList);
		model.addAttribute("student", student);
		
		return "secure/index";
	}
	
	@PostMapping("/secure")
	public String postSecureIndex(Model model, @RequestParam String firstname, @RequestParam String lastname, Authentication authentication) {
		Long userId = da.findUserAccount(authentication.getName()).getUserId();
		
		da.insertStudent(firstname, lastname, Long.valueOf(userId));
		
		Student placeHolderStudent = new Student();
		
		model.addAttribute("student", placeHolderStudent);
			
		return "redirect:/secure";
	}
	
	
	//SECURE COURSE REGISTRATION PAGE
	@GetMapping("/secure/courseRegistration")
	public String getCourseRegistration(Model model, Authentication authentication) {
		Long userId = da.findUserAccount(authentication.getName()).getUserId();
		Student student = da.findStudent(userId);
		
		List<Course> courseList = da.getCourseList();
		
		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		if (student != null) {
			int count  = da.getStudentEnrollmentCount(student.getStudentId());
			model.addAttribute("count", count);
			
		}
		model.addAttribute("roleList", roleList);
		model.addAttribute("courseList", courseList);
		model.addAttribute("student", student);
	
		return "/secure/courseRegistration";
	}
	
	@PostMapping("/secure/courseRegistration")
	public String postCourseRegistration(Model model, @RequestParam List<String> selectedCourseIds, Authentication authentication) {
		
		List<Course> selectedCourses =  new ArrayList<>();
		
		if (selectedCourseIds != null) {
            for (String idInString: selectedCourseIds) {
            	Long id = Long.parseLong(idInString);
            	Course course = da.getCourseById(id);
            	selectedCourses.add(course);
            }
        }
		
		Long userId = da.findUserAccount(authentication.getName()).getUserId();
		Student student = da.findStudent(userId);
		
		
		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		model.addAttribute("roleList", roleList);
		
		if (selectedCourses != null) {
            for (Course course : selectedCourses) {
            	da.enrollCourse(student.getStudentId(), course.getCourseId(), "F2023", java.time.LocalDate.now().toString());
            }
        }
	
		return "redirect:/secure/courseRegistration";
	}
	//ADMIN - TEACHER VIEWS
	
	// GRADE STUDENT
	@GetMapping("/admin/grading")
	public String getGrading(Model model, HttpSession session, Authentication authentication) {
		Enrollment enrollmentToBe = (Enrollment) session.getAttribute("enrollmentToBeUpdated"); 
		

		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		model.addAttribute("roleList", roleList);
		model.addAttribute("enrollment", new Enrollment());
		model.addAttribute("enrollmentToBeUpdated", enrollmentToBe);

		

		return "/admin/grading";
	}
	
	@PostMapping("/admin/getEnrollment")
	public String postGrading(Model model, HttpSession session, @ModelAttribute Enrollment enrollment, @ModelAttribute Enrollment enrollmentToBeUpdated) {
		

		
		Enrollment enrollmentToBe = da.getStudentEnrollment(enrollment.getStudentId(), enrollment.getCourseId());
		
		session.setAttribute("enrollmentToBeUpdated", enrollmentToBe);

		
		return "redirect:/admin/grading";
	}
	
	@PostMapping("/admin/updateGrade")
	public String postGrading2(Model model, HttpSession session, @ModelAttribute Enrollment enrollment, @ModelAttribute Enrollment enrollmentToBeUpdated) {

		
		session.setAttribute("enrollmentToBeUpdated", new Enrollment());

		da.gradeStudent(enrollmentToBeUpdated.getStudentId(), enrollmentToBeUpdated.getCourseId(), enrollmentToBeUpdated.getGrade());

		
		return "redirect:/admin/grading";
	}

	
	
	
	@GetMapping("/admin/transcript")
	public String getTranscript(Model model, HttpSession session, Authentication authentication) {
		Student student = (Student) session.getAttribute("student");
		
		List<Transcript> transcriptList = (List<Transcript>) session.getAttribute("transcriptList");
		
		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		model.addAttribute("roleList", roleList);
		model.addAttribute("student", student);
		model.addAttribute("date", LocalDate.now());
		model.addAttribute("transcriptList", transcriptList);
		
		
		
		return "/admin/transcript";
	}
	
	@PostMapping("/admin/transcript")
	public String postTranscript(Model model, HttpSession session, @RequestParam Long studentId) {
		Student student = da.getStudentByStudentId(studentId);
		
		List<Transcript> transcriptList = da.getTranscript(studentId);
		session.setAttribute("student", student);
		session.setAttribute("transcriptList", transcriptList);
		
		return "redirect:/admin/transcript";
	}
	
	
	
	
}
