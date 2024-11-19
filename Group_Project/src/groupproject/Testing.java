package groupproject;

import java.time.LocalTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 

/**
 * <p> App class for JavaFX </p>
 * 
 * <p> Description: This class contains the output of various functions</p>
 */
public class Testing extends Application{
    
	/**
	 * main method to launch the JavaFX app
	 * 
	 * @param args	Standard Java main method argument for passing in command line args
	 * 
	 */

    public void start(Stage primaryStage) {
    	testPhase2();
    }
    
    /*
     * This method contains the calls for testing in Phase 2
     */
    public void testPhase2() {
    	testAddCase("Coding", "How to Code", "Use an IDE", "" ,"" ,"code" );
		testAddCase("Bad article", "to be removed in the future", "nothing here", "" ,"" ,"" );
		testAddCase("Documenting", "How to Document", "Use comments, // for java", "java, comments" ,"www.document.org" ,"comment" );
		testAddCase("Running Code", "How to run code", "Press the green run button", "run, code" ,"" ,"code" );
		testContainCase(1,true);
		testContainCase(5,false);
		testContainCase(2,true);
		testContainCase(4,true);
		testRemovalCase(2,3);
		testRemovalCase(5,3);
		testContainCase(2,false);
		testContainCase(5,false);
		testAddCase("Running Code in C++ ", "How to run code in C++", "Use the g++ command", "run, code" ,"" ,"code" );
		testContainCase(5,true);
		testRemovalCase(5,3);
		testAddCase("Running Code in C++ ", "How to run code in C++", "Use the g++ command", "run, code" ,"" ,"code" );
		testContainCase(5,false);
		testContainCase(6,true);


    }
    
    /*
     * This method contains the calls for testing in Phase 1
     */
    public void testPhase1() {
    	System.out.println("Username and Password Test Cases: \n");
		testUsernamePassword("user","password!","password!","Username must be at least 5 characters long");
		testUsernamePassword("user!23","password!","password!","Username must only contain letters and numbers");
		testUsernamePassword("user23","pass!","pass!","Password must be at least 8 characters long");
		testUsernamePassword("user23","password!","password!","Password must contain at least one uppercase character");
		testUsernamePassword("user23","Pass word!","Pass word!","Password may not contain any spaces");
		testUsernamePassword("user23","Password","Password","Password must contain at least one special character");
		testUsernamePassword("user23","Password!","Password2!","Password and confirmation don't match");
		testUsernamePassword("user23","Password!","Password!","valid");
		System.out.println("\n\n\n");
		System.out.println("--------------------------------------------------");
		System.out.println("Login Response Test Cases: ");
		testLogin();
    }
	public static void main(String[] args) {
		launch(args);
	}
    
	/*
	 * This method runs a single test case on the updateUserInfo function belonging to the InvitePage class
	 */
	public static void testUsernamePassword(String username, String password, String confirmPassword, String expected) {
		//Create an invite page object to test the updateUserInfo function
		InvitePage invTest = new InvitePage();
		invTest.u = new User();
		//Set fields of the invTest page according to passed input arguments
		invTest.userField.setText(username);
		invTest.passwordField.setText(password);
		invTest.cPasswordField.setText(confirmPassword);
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Password Confirmation: " + confirmPassword);

		//Obtain the result of the function to be tested and compare it to the expected output
		String res = invTest.updateUserInfo();
		System.out.println(res);
		if(res.equals(expected)) {
			System.out.println("Test Case passed");
		} else {
			System.out.println("Test Case failed");
		}
		//formatting
		System.out.println("");
		System.out.println("--------------------------------------------------");
		System.out.println("");
	}
	
	/*
	 * This method creates a User array and runs various login test cases with it
	 */
	public static void testLogin() {
		LoginPage logTest = new LoginPage();
		ArrayList<User> users = new ArrayList<User>();
		
		//Initialize user1
		User user1 = new User();
		user1.passwordIsResetOTP = true;
		user1.passwordIsInviteCode = false;
		user1.expireTime = LocalTime.now().plusHours(2);
		user1.username = "user1";
		user1.password = "password1".toCharArray();
		user1.isAdmin = true;
		
		//Initialize user2
		User user2 = new User();
		user2.passwordIsResetOTP = false;
		user2.passwordIsInviteCode = false;
		user2.username = "user2";
		user2.password = "password2".toCharArray();
		user2.isStudent = true;
		
		//Initialize user3
		User user3 = new User();
		user3.passwordIsResetOTP = false;
		user3.passwordIsInviteCode = false;
		user3.username = "user3";
		user3.password = "password3".toCharArray();
		user3.isStudent = true;
		user3.isAdmin = true;
		
		//Build the array of users
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		//Run all test cases and display output
		testLoginCase(logTest,users,"user1","password","Admin",-1);
		testLoginCase(logTest,users,"user1","password1","Admin",-10);
		user1.expireTime = LocalTime.now().minusHours(2);
		testLoginCase(logTest,users,"user1","password1","Admin",-2);
		testLoginCase(logTest,users,"user2","password1","Admin",-1);
		testLoginCase(logTest,users,"user2","password2","Admin",-1);
		testLoginCase(logTest,users,"user2","password2","Student",1);
		testLoginCase(logTest,users,"user3","password3","Student",2);
		testLoginCase(logTest,users,"user3","password3","Instructor",-1);
		testLoginCase(logTest,users,"user3","password3","Admin",2);

	}
	/*
	 * This method runs an individual case of a user attempting to login
	 */
	public static void testLoginCase(LoginPage logTest, ArrayList<User> users, String username, String password, String role, int expected) {
		int x = logTest.login(users,username,password,role);
		//formatting input arguments
		System.out.println("Attempting login in:");
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Role: " + role);
		System.out.println("Index flag: " + x);
		if(x == expected) {
			System.out.println("Test case passed");
		} else {
			System.out.println("Test case failed");
		}
		
		System.out.println("");
		System.out.println("--------------------------------------------------");
		System.out.println("");

	}
	
	
	/*
	 * This method runs an individual test case of adding a help item
	 */
	public void testAddCase(String a, String b, String c, String d, String e, String f) {
		HelpItem.add(a, b, c, d, e, f,"Beginner","hi");
		HelpItem h = App.items.get(App.items.size()-1);
		h.print();
		if(h.title.equals(a) && h.description.equals(b) && h.body.equals(c) && h.keywords.equals(HelpItem.prettyStringToList(d)) && h.links.equals(HelpItem.prettyStringToList(e)) && h.groups.equals(HelpItem.prettyStringToList(f))) {
			System.out.println("Test case passed");
		} else {
			System.out.println("Test case failed");
		}
		System.out.println("");
		System.out.println("--------------------------------------------------");
		System.out.println("");
	}
	
	/*
	 * This method runs and individual test case of testing containment
	 */
	public void testContainCase(int id, boolean expected) {
		boolean x = HelpItem.containsID(id);
		System.out.println("Test containment of id: " + id);
		System.out.println("Result: " + x);
		System.out.println("Expected: " + expected);
		if(x == expected) {
			System.out.println("Test case passed");
		} else {
			System.out.println("Test case failed");
		}
		System.out.println("");
		System.out.println("--------------------------------------------------");
		System.out.println("");
	}
	
	/*
	 * This method tests and individual case of removal of an article
	 */
	public void testRemovalCase(int id,int expectedSize) {
		System.out.println("Test removal of id: " + id);
		HelpItem.removeByID(id);
		boolean x = HelpItem.containsID(id);
		System.out.println("Containment Result: " + x);
		System.out.println("Expected Containment Result: " + false);
		System.out.println("Result Size: " + App.items.size());
		System.out.println("Expected Size: " + expectedSize);
		if(x == false && App.items.size()==expectedSize) {
			System.out.println("Test case passed");
		} else {
			System.out.println("Test case failed");
		}
		System.out.println("");
		System.out.println("--------------------------------------------------");
		System.out.println("");
	}
	

	

	
}