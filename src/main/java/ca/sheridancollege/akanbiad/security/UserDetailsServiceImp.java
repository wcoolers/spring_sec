/**
 * This is the User Details Service class
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.security;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ca.sheridancollege.akanbiad.database.DatabaseAccess;



@Service
public class UserDetailsServiceImp implements UserDetailsService {
	@Autowired
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ca.sheridancollege.akanbiad.beans.User user = da.findUserAccount(username);
		
		if(user == null) {
			System.out.println("User not found: " + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		System.out.println("User found: " + user.getEmail());
		//Get a list of roles for that user
		List<String> roleNameList = da.getRolesById(user.getUserId());
		
		//change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if(roleNameList != null) {
			for (String role: roleNameList) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		
		//create a user based on the info above
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), grantList);
		user.setEncryptedPassword(new BCryptPasswordEncoder().encode(user.getEncryptedPassword()));
		
		return userDetails;
		
		
	}

}
