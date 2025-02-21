/**
 * This is the Transcript class
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
public class Transcript {
	@NonNull
	private String courseName;
	@NonNull
	private String grade;
	@NonNull
	private String termName;
}