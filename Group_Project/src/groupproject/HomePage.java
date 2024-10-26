package groupproject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> This class sets up the home page for a given user and role </p>
 */
public class HomePage {

	public Scene scene;
	public Button btn;
	public Button logout;
	public Button articles;

    public String role;
    public User u;
    public String title = "Home Page";
    
    /**
     * Constructor that creates basic home page
     */
	public HomePage() {
		
		logout = new Button("Logout");
        articles = new Button("Articles");


		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(logout,0,1);

        scene = new Scene(grid, App.WIDTH, App.HEIGHT);
	}
	
	/**
	 * read the role attribute and set up the elements of the scene accordingly
	 */
	public void setSceneFromRole(){
		if(role == "Student") {
			setStudentScene();
		} else if (role.equals("Admin")) {
			setAdminScene();
		} else if(role.equals("Instructor")) {
			setInstructorScene();
		}
	}
	
	/**
	 * Administrator page
	 * 
	 * Show a list of users, options to create a new user with a one-time password, and reset a user's password
	 */
	public void setAdminScene() {

		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);
        Label intro = new Label("Your name is " + u.preferredName + " and you are a " + role);
        
        
        ObservableList<String> items = FXCollections.observableArrayList();

        
        // Creating a ListView, show all registered users
        for(User u : App.users) {
        	if(!u.passwordIsInviteCode) {
        		items.add(u.username);
        	}
        }
        ListView<String> userList = new ListView<>(items);
        
        // options to generate OTP for new user
        Label inviteGenerate = new Label("Generate an invite code for a new user:");
        Label roleLabel = new Label("Select Role(s) for the new user:");
        Label userListLabel = new Label("User List:");
        Label inviteLabel = new Label("");

        
		Button btn = new Button("Generate");
        
        
		CheckBox checkStudent = new CheckBox("Student");
        CheckBox checkInstructor = new CheckBox("Instructor");
        CheckBox checkAdmin = new CheckBox("Admin");

        // options to delete or reset a user
        Button del = new Button("Delete User");
        Button reset = new Button("Reset User");
        Label resetField = new Label("Code:");
        CheckBox updStudent = new CheckBox("Student");
        CheckBox updInstructor = new CheckBox("Instructor");
        CheckBox updAdmin = new CheckBox("Admin");
        Button update = new Button("Update User Roles");
        

        
        VBox reswrap = new VBox();
        reswrap.setSpacing(5);
        reswrap.getChildren().addAll(reset,resetField);

        VBox upd = new VBox();
        upd.setSpacing(5);
        upd.getChildren().addAll(updStudent,updInstructor,updAdmin);
        
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(checkStudent, checkInstructor,checkAdmin);
        
        VBox editUser = new VBox();
        editUser.setSpacing(40);
        editUser.getChildren().addAll(del,reswrap,upd, update);
        
        
        // list selection handling
        userList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	int x = App.indexFromUsername(newValue);
        	if(x != -1) {
        		User us = App.users.get(x);
        		updStudent.setSelected(us.isStudent);
        		updAdmin.setSelected(us.isAdmin);
        		updInstructor.setSelected(us.isInstructor);
        	}
        });
        
       // deletion logic and confirmation
       del.setOnAction(e->{
    	   if(userList.getSelectionModel().getSelectedItem() == null) {
       		Alert alert = new Alert(AlertType.ERROR);
       		alert.setHeaderText("Error");
       		alert.setContentText("Select a user first.");
       		alert.showAndWait();
	       	} else { 
	       		if(userList.getSelectionModel().getSelectedItem().equals(u.username)) {
	       			Alert alert = new Alert(AlertType.ERROR);
	           		alert.setHeaderText("Error");
	           		alert.setContentText("You can't delete yourself");
	           		alert.showAndWait();
	       		} else {	       		
	       		
		       		Alert alert = new Alert(AlertType.CONFIRMATION);
		            alert.setTitle("Confirmation Dialog");
		            alert.setHeaderText("Are you sure?");
		            alert.setContentText("Do you want to delete this user's account?");
		            Optional<ButtonType> result = alert.showAndWait();
		            boolean res = result.isPresent() && result.get() == ButtonType.OK;
		            
		            if(res) {
		        		int x = App.indexFromUsername(userList.getSelectionModel().getSelectedItem());
		        		App.users.remove(x);
		        		items.remove(userList.getSelectionModel().getSelectedItem());
		            }
	       		}
	       	}
       });
        
        // options to update a user
        update.setOnAction(e ->{
        	if(userList.getSelectionModel().getSelectedItem() == null) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Select a user first.");
        		alert.showAndWait();
        	} else {        	
        		int x = App.indexFromUsername(userList.getSelectionModel().getSelectedItem());
        		User us = App.users.get(x);
        		us.isAdmin = updAdmin.isSelected();
        		us.isStudent = updStudent.isSelected();
        		us.isInstructor = updInstructor.isSelected();
        	}
        	
        });
        
        // reset user's password with a OTP for next login
        reset.setOnAction(e ->{
        	if(userList.getSelectionModel().getSelectedItem() == null) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Select a user first.");
        		alert.showAndWait();
        	} else {        	
        		int x = App.indexFromUsername(userList.getSelectionModel().getSelectedItem());
        		User us = App.users.get(x);
        		us.passwordIsResetOTP = true;
        		
        		Random random = new Random();
	        	String code = "";
	        	String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        	for(int i=0;i<10;i++) {
	        		int tx = random.nextInt(chars.length());
	        		code += (chars.charAt(tx));
	        	}
	        	resetField.setText("Code: " + code);
	        	us.password = code.toCharArray();
	        	us.passwordIsResetOTP = true;
	        	// OTP expires in 1 hr after reset
	        	us.expireTime = LocalTime.now().plusHours(1);
        	}
        });
        

        grid.add(inviteGenerate, 0, 0);
        grid.add(inviteLabel, 1, 0);

        grid.add(roleLabel,0,1);
        GridPane.setHalignment(roleLabel,HPos.RIGHT);

        grid.add(vbox,1,1);
        
        grid.add(btn,1,2);
        
        grid.add(userListLabel, 0, 8);
        GridPane.setHalignment(userListLabel, HPos.RIGHT);

        grid.add(userList, 1, 8);
        grid.add(editUser, 2, 8);


        // new user/invite code generation
        btn.setOnAction(e ->{
        	
        	//check roleLabel selected
        	if(!checkStudent.isSelected() && !checkAdmin.isSelected() && !checkInstructor.isSelected()) {
        		//Error message
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Select a role for the user.");
        		alert.showAndWait();
        	} else {
	        	//generate invite code randomly
	        	Random random = new Random();
	        	String code = "";
	        	String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        	for(int i=0;i<10;i++) {
	        		int x = random.nextInt(chars.length());
	        		code += (chars.charAt(x));
	        	}
	        	inviteLabel.setText(code);
	        	//add to Users array
	        	User next = new User();
	        	next.password = code.toCharArray();
	        	next.passwordIsInviteCode = true;
	        	next.infoSetup = false;
	        	next.isStudent = checkStudent.isSelected();
	        	next.isAdmin = checkAdmin.isSelected();
	        	next.isInstructor = checkInstructor.isSelected();

	        	App.users.add(next);	        	
        	}
        });

        grid.add(articles, 1, 15);
        grid.add(logout,0,20);
        
        
        BorderPane totalPage = new BorderPane();
        totalPage.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}
	
	/**
	 * Student view, just a role and username for now
	 */
	public void setStudentScene() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);
        Label intro = new Label("Your name is " + u.preferredName + " and you are a " + role);

        grid.add(intro,0,0);
        grid.add(logout,0,1);
        
        BorderPane totalPage = new BorderPane();
        totalPage.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}
	
	/**
	 * instructor view, just a role and username for now
	 */
	public void setInstructorScene() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);
        Label intro = new Label("Your name is " + u.preferredName + " and you are a " + role);

        grid.add(intro,0,0);
        grid.add(logout,0,1);
        
        BorderPane totalPage = new BorderPane();
        totalPage.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}
}

