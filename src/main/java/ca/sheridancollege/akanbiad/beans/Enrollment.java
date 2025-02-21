/**
 * This is the Enrollment class
 * 
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
public class Enrollment {
	@NonNull
	private Long studentId;
	@NonNull
	private Long courseId;
	@NonNull
	private String termName;
	private String grade;
	private String enrollDate;
}