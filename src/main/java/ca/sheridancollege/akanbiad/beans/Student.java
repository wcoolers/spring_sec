/**
 * This is the Student class
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

package ca.sheridancollege.akanbiad.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Student {
	@NonNull
	private Long studentId;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private Long userId;
}
