package groupproject;

import java.time.LocalTime;

/**
 * class for user
 */
public class User {
	public boolean isAdmin = false;
	public boolean isStudent = false;
	public boolean isInstructor = false;
	public boolean passwordIsInviteCode;
	public boolean passwordIsResetOTP;
	public LocalTime expireTime;
	public boolean infoSetup;
	public String email = "";
	public String username = "";
	public char[] password;
	public String firstName = "";
	public String middleName = "";
	public String lastName = "";
	public String preferredName = "";
	
	/**
	 * constructor: default to needing OTP, not having info, not needing password reset
	 */
	public User() {
		passwordIsInviteCode = true;
		infoSetup = false;
		passwordIsResetOTP = false;
	}
	
	
}
