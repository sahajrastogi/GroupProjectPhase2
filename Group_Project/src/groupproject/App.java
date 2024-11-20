package groupproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
 * <p> Description: This class contains the window and scene setup </p>
 */
public class App extends Application {
	
	/**
	 * window width and height constants
	 */
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 750;
	
	/**
	 * ArrayList object for users
	 */
	public static ArrayList<User> users;
	public static ArrayList<HelpItem> items = new ArrayList<HelpItem>();; 
	public static ArrayList<String> groups;
	//maps from username to view or admin rights over groups
    public static HashMap<String,ArrayList<String>> viewMap = new HashMap<>();
    public static HashMap<String,ArrayList<String>> adminMap = new HashMap<>();

	
	/**
	 * main method to launch the JavaFX app
	 * 
	 * @param args	Standard Java main method argument for passing in command line args
	 */
	public static void main(String[] args) {
	    launch(args);
	}
    
	/**
	 * This method initializes all scenes within the app and initializes the users ArrayList.
	 * The initial user is created and the application flow is implemented
	 */
    public void start(Stage primaryStage) {

    	// init users, along with a special case for the first user who is an admin    	
    	users = new ArrayList<User>();
    	User first = new User();
    	first.password = "first".toCharArray();
    	first.isAdmin = true;
    	users.add(first);
    	
    	//There are 100 dummy admin users with username a followed by a number and password a
    	// TODO not safe, can use to login as admin
    	for(int i=0;i<20;i++) {
	    	User convenience = new User();
	    	convenience.password = "a".toCharArray();
	    	convenience.username = ("a" + i);
	    	convenience.isAdmin = true;
	    	convenience.isInstructor = true;
	    	convenience.isStudent = true;
	    	convenience.infoSetup = true;
	    	convenience.passwordIsInviteCode = false;
	    	users.add(convenience);
    	}
    	
    	//initialize help items
    	//items = new ArrayList<HelpItem>();
//    	HelpItem h = new HelpItem();
//    	h.id = 1; h.title = "Eating"; h.description = "How to Eat"; h.body = "You put the food into your mouth.";
//    	h.keywords = new ArrayList<>(Arrays.asList("eat","food","mouth"));
//    	h.groups = new ArrayList<>(Arrays.asList("eat","food"));
//    	items.add(h);
    	
    
//    	try {
//			HelpItem.backup("file.txt", "all");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
    	try {
			HelpItem.restore("file.txt", false);
			//items.get(1).print();
			//System.out.println(items.get(0).links.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	
    	initMaps();
    	
    	for(User u : users) {
    		if(u.username.equals("a1")) continue;
    		viewMap.get(u.username).add("general");
    		adminMap.get(u.username).add("general");
    	}
    	
    	System.out.println("ASU Hello World!");
    	System.out.println("It started!");
    	
    	//Initialize all pages in the flow of the program
    	InitialPage initPage = new InitialPage();
    	SetUserUpPage setUpPage = new SetUserUpPage();
    	LoginPage loginPage = new LoginPage();
    	HomePage homePage = new HomePage();
    	InvitePage invitePage = new InvitePage();
    	PasswordResetPage prPage = new PasswordResetPage();
    	ArticlePage arPage = new ArticlePage();

    	
    	//Sets the action of the initialization page to go the login Page        
        initPage.btn.setOnAction(e -> {
        	System.out.println("Going to LoginPage");
        	loginPage.clearFields();
        	primaryStage.setTitle(loginPage.title);
        	primaryStage.setScene(loginPage.scene);
        });   
        
        
        //Sets the transition for the set up page to the home page and passes along user information
        setUpPage.btn.setOnAction(e -> {
        	//need to do alerts if invalid name or password
        	
        	if(setUpPage.fNameField.getText().equals("") || setUpPage.lNameField.getText().equals("") || setUpPage.eField.getText().equals("")){
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Missing field(s).");
        		alert.showAndWait();
        	} else {
	        	System.out.println("Going to HomePage");
	        	setUpPage.updateUserInfo();
	        	homePage.u = setUpPage.u;
				homePage.role = setUpPage.role;
				homePage.setSceneFromRole();
	        	primaryStage.setTitle(homePage.title);
	        	primaryStage.setScene(homePage.scene);
        	}
        });

        //sets transition for the home page
        homePage.logout.setOnAction(e ->{
        	System.out.println("Going to LoginPage");
        	loginPage.clearFields();
        	primaryStage.setTitle(loginPage.title);
        	primaryStage.setScene(loginPage.scene);
        });
        
        
        //Handle login button event on login page
        loginPage.btn.setOnAction(e ->{
        	
        	//Attempt to login, get result
        	int res = loginPage.login(
        			users,
        			loginPage.userField.getText(),
        			loginPage.passwordField.getText(),
        			loginPage.comboBox.getSelectionModel().getSelectedItem()
    			);
        	// branch from login result
        	if(res == -1) {
        		//Error message
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Invalid username, password or role selection");
        		alert.showAndWait();

        	} else if(res >= 0){
        		//Successful login
            	//Check if to be routed to set up user page
        		User u = users.get(res);
        		if(u.infoSetup) {
        			
        			//Authenticated a registered user, going to home page
                	System.out.println("Going to HomePage - passing user information along");

        			homePage.u = u;

        			homePage.role = loginPage.comboBox.getSelectionModel().getSelectedItem();
        			homePage.setSceneFromRole();
		    		primaryStage.setTitle(homePage.title);
            		primaryStage.setScene(homePage.scene);
        		} else {
        			
        			//Got one time password, go to user setup
                	System.out.println("Going to SetUserUpPage - passing user information along");

        			setUpPage.u=u;
        			setUpPage.role = loginPage.comboBox.getSelectionModel().getSelectedItem();
        			setUpPage.clearFields();
        			primaryStage.setTitle(setUpPage.title);
            		primaryStage.setScene(setUpPage.scene);
        		}
        	} else {
        		// need password reset
        		if(res == -2) {
        			// password reset OTP expired, show alert
        			Alert alert = new Alert(AlertType.ERROR);
            		alert.setHeaderText("Error");
            		alert.setContentText("Reset password expired.");
            		alert.showAndWait();

        		} else {
        			//valid password reset OTP, go to reset page
	        		res += 10;
	        		res = -res;
	        		User u = users.get(res);
	
	        		//send to password reset page
	            	System.out.println("Going to PasswordResetPage - passing user information along");
	
	        		prPage.u=u;
	        		prPage.clearFields();
	    			primaryStage.setTitle(prPage.title);
	        		primaryStage.setScene(prPage.scene);
        		}
        	}
        	
        });
        
        //Set the invite button event on login page
        loginPage.inviteLogin.setOnAction(e ->{
        	
        	//Confirm valid invite code
        	int res = invitePage.confirm(users,loginPage.inviteField.getText());
        	if(res == -1) {
        		//error message
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Incorrect invite code");
        		alert.showAndWait();
        	} else {
        		//Pass information to Invite page
            	System.out.println("Going to Invite Page - passing user information along");

        		invitePage.u = users.get(res);
        		invitePage.clearFields();
	        	primaryStage.setTitle(invitePage.title);
	        	primaryStage.setScene(invitePage.scene);
        	}
        });
        
        //Set action for submit button on Invite page
        invitePage.btn.setOnAction(e ->{
        	
        	//confirm valid set up 
        	String temp = invitePage.updateUserInfo();
        	if (temp.equals("valid")) {
        		//Successful setup, go to login page
            	System.out.println("Going to LoginPage");

        		loginPage.clearFields();
        		primaryStage.setTitle(loginPage.title);
            	primaryStage.setScene(loginPage.scene);
        	} else {
        		//Error message
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText(temp);
        		alert.showAndWait();
        	}
        	
        });
        
        // Set action for password reset page
        prPage.btn.setOnAction(e -> {
        	String temp = prPage.updateUserInfo();
        	if (temp.equals("valid")) {
        		// Successful reset, return to login page
            	System.out.println("Going to LoginPage");
        		loginPage.clearFields();
        		primaryStage.setTitle(loginPage.title);
            	primaryStage.setScene(loginPage.scene);
        	} else {
        		//Error message
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText(temp);
        		alert.showAndWait();
        	}
        });
        
        
        //set article button action for home page
        homePage.articles.setOnAction(e -> {
        	System.out.println("Going to ArticlePage");
        	arPage.role = homePage.role;
        	arPage.u = homePage.u;
        	arPage.updateList();
        	arPage.emptyDisplay();
        	arPage.updateBoxFromRole();
    		primaryStage.setTitle(arPage.title);
        	primaryStage.setScene(arPage.scene);
        });
        
        //set back button action on article page
        arPage.back.setOnAction(e->{
			//homePage.setSceneFromRole();
        	primaryStage.setTitle(homePage.title);
    		primaryStage.setScene(homePage.scene);
        });
        // display welcome page
    	primaryStage.setTitle("Welcome Page");
        primaryStage.setScene(initPage.scene); // Start with welcome page
//        primaryStage.setTitle(arPage.title);
//    	primaryStage.setScene(arPage.scene);
    	
        primaryStage.show();
        
        
    }
    
    //Find index of user from their username
    public static int indexFromUsername(String s) {
    	for(int i=0;i<users.size();i++) {
    		if(users.get(i).username.equals(s)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    //Check whether the current list of Users contains a certain username
    public static boolean containsUsername(String s) {
    	for(int i=0;i<users.size();i++) {
    		if(users.get(i).username.equals(s)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static void initMaps() {
    	for(User u : users) {
    		viewMap.put(u.username,new ArrayList<String>());
    		adminMap.put(u.username,new ArrayList<String>());
    	}
    }
    public static boolean checkAdminAccess(String username, String group) {
    	return adminMap.get(username).contains(group.trim());
    }
    public static boolean checkViewAccess(String username, String group) {
    	return viewMap.get(username).contains(group.trim());
    }
    public static void viewMapAdd(String username, String group) {
    	if(!viewMap.get(username).contains(group.trim())) viewMap.get(username).add(group.trim());
    }
    public static void adminMapAdd(String username, String group) {
    	if(!adminMap.get(username).contains(group.trim())) adminMap.get(username).add(group.trim());
    }
    public static void viewMapRemove(String username, String group) {
    	if(viewMap.get(username).contains(group.trim())) viewMap.get(username).remove(group.trim());
    }
    public static void adminMapRemove(String username, String group) {
    	if(adminMap.get(username).contains(group.trim())) adminMap.get(username).remove(group.trim());
    }
    

}