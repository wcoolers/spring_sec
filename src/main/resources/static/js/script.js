/**
 * This is the js file. Contains password verification during registration 
 * 
 * @author Adegoke Akanbi. Student ID: 991719830
 * @version 1.0
 */

function verify() {
	var password1 = document.forms['form']['password'].value;
	var password2 = document.forms['form']['verifyPassword'].value;
	if (password1 == null || password1 == "" || password1 != password2) {
		document.getElementById("error").innerHTML = "Please check your passwords";
		return false;
	}
}

