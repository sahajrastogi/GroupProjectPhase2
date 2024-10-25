package groupproject;

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
 * <p> class that contains the page for setting personal information about a user</p>
 */
public class SetUserUpPage {

	public Scene scene;
	public Button btn;
	public User u;
	public String role;
	
    public TextField fNameField;
    public TextField mNameField;
    public TextField lNameField;
    public TextField pNameField;
    public TextField eField;
    

    public String title = "Set Up Page";
    
    /**
     * constructor that lays out the GUI
     */
	public SetUserUpPage() {
		btn = new Button("Submit");
        
        
		Label info = new Label("Set up your account:");
        Label fNameLabel = new Label("First Name:");
        fNameField = new TextField();
        
        Label mNameLabel = new Label("Middle Name:");
        mNameField = new TextField();
        
        Label lNameLabel = new Label("Last Name:");
        lNameField = new TextField();
        
        Label pNameLabel = new Label("Preferred Name:");
        pNameField = new TextField();
        
        Label eLabel = new Label("email:");
        eField = new TextField();
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(info, 0, 0);
        grid.add(fNameLabel, 0, 1);
        grid.add(fNameField, 1, 1);
        grid.add(mNameLabel, 0, 2);
        grid.add(mNameField, 1, 2);
        grid.add(lNameLabel, 0, 3);
        grid.add(lNameField, 1, 3);
        grid.add(pNameLabel, 0, 4);
        grid.add(pNameField, 1, 4);
        grid.add(eLabel, 0, 5);
        grid.add(eField, 1, 5);
        grid.add(btn, 1, 8);
        
        BorderPane totalPage = new BorderPane();
        totalPage.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}
	       
	/**
	 * method to clear all input fields in the GUI
	 */
	public void clearFields() {
		fNameField.clear();
		lNameField.clear();
		mNameField.clear();
		pNameField.clear();
		eField.clear();
	}
	
	/**
	 * Store all entered fields inside 
	 */
	public void updateUserInfo() {
		// TODO check if name and email requirements are met
		u.firstName = fNameField.getText();
		u.lastName = lNameField.getText();
		u.middleName = mNameField.getText();
		u.email = eField.getText();
		
		
		// store preferred name if provided, otherwise set it as the first name
		if(pNameField.getText().length() == 0) {
			u.preferredName = u.firstName;
		} else {
			u.preferredName = pNameField.getText();
		}
		
		u.infoSetup = true;
	}

   
}

