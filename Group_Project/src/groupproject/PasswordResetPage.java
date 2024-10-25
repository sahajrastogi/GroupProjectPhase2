package groupproject;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * <p> Class that creates a page for password reset and validation </p>
 */
public class PasswordResetPage {

	public Scene scene;
	public Button btn;
	public String currUser;
	public String title = "Password Reset Page";
	
	//public int currUserIndex;
	public User u;
	
    public TextField userField;
    public PasswordField passwordField;
    public PasswordField cPasswordField;
    
    /**
     * constructor that lays out the GUI
     */
	public PasswordResetPage() {
		btn = new Button("Create Account");
        
        
        Label userLabel = new Label("Username:");
        userField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        
        Label cPasswordLabel = new Label("Confirm Password:");
        cPasswordField = new PasswordField();
        
        Label create = new Label("Enter a new password:");

        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(create, 0, 0);

        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(cPasswordLabel, 0, 2);
        grid.add(cPasswordField, 1, 2);
        grid.add(btn, 1, 3);
        
        BorderPane totalPage = new BorderPane();
        totalPage.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
        
	}

	/**
	 * Method to check the password against several criteria and update the password if the criteria are all met
	 * @return String with error message or "valid" if successful
	 */
	public String updateUserInfo() {
		
		//retrieve password
		String password = passwordField.getText();
		String cpassword = cPasswordField.getText();

		
		if(password.length() < 8) return "Password must be at least 8 characters long";
		
		if(!password.matches(".*[A-Z].*")) return "Password must contain at least one uppercase character";
		
		if(password.contains(" ")) return "Password may not contain any spaces";

		if(password.matches("[a-zA-Z0-9]+")) return "Password must contain at least one special character";

		if(password.equals(cpassword)) {
			u.password = password.toCharArray();
			u.passwordIsResetOTP = false;
			return "valid";
		} else {
			return "Password and confirmation don't match";
		}
	}
	
	/**
	 * clear the text entry fields
	 */
	public void clearFields() {
		passwordField.clear();
		cPasswordField.clear();
	}

   
}

