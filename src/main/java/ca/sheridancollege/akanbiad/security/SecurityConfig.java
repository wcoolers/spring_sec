/**
 * This is the Security Config file using spring security
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.security;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector){
		return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		
		http
		.csrf(c -> c.disable())
		.headers(header -> header.frameOptions(frame -> frame.disable()))
		.authorizeHttpRequests(requests-> 
		requests.requestMatchers(mvc.pattern("/secure/**")).hasAnyRole("USER", "ADMIN", "TEACHER") //only user, admin and teacher can access /secure path
		.requestMatchers(mvc.pattern("/admin/**")).hasAnyRole("ADMIN", "TEACHER") //only admin and teacher can access /admin path
		.requestMatchers(mvc.pattern("/"), mvc.pattern("/js/**"), 
			mvc.pattern("/css/**"), mvc.pattern("/images/**"), 
			mvc.pattern("permission-denied"),
			mvc.pattern("/h2-console/**")).permitAll()
			.requestMatchers(antMatcher("/h2-console/**")).permitAll()
			.requestMatchers(antMatcher("/register")).permitAll()
			.requestMatchers(mvc.pattern("/**")).denyAll())
		.formLogin(formLogin -> formLogin
				   	.loginPage("/login")
				   	.defaultSuccessUrl("/secure", true)
				   	.permitAll())
					.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))
		.logout(logout -> 
					logout
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout")
					.permitAll());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

