/**
 * This is the Spring SecurityWebApplicationAnalizer, more like a security middleware.
 *
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */
package ca.sheridancollege;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import ca.sheridancollege.akanbiad.security.SecurityConfig;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer{
	
	
	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}

}
