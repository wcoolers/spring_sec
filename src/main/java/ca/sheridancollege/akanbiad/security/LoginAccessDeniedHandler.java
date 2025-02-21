/**
 * This is the Login Access Handler class
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {
	

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			System.out.println(auth.getName() 
					+ " was trying to access protected resource: " 
					+ request.getRequestURI());
		}
		
		response.sendRedirect(request.getContextPath() + "/permission-denied");
	}	

}
