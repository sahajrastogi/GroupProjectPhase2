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
public class GroupPage {

	public Scene scene;
	public Button btn;
	public Button logout;
	public Button articles;

    public String role;
    public User u;
    public String title = "Group Page";
    
    /**
     * Constructor that creates basic home page
     */
	public GroupPage() {
		
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
	public void setScene(){
		
	}
	
	
}

