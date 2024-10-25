package groupproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <p> Startup page for first user </p>
 */
public class InitialPage {

	public Scene scene;
	public Button btn;
    public TextField textField;

	public InitialPage() {
		// show first invite code
        Label intro = new Label("Your invite code is \"first\"");
        //intro.setFont(new Font("Arial", 20));
        
        // button to take user to login page
        btn = new Button("Get Started Here");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.setVgap(10);
        grid.setHgap(10);
        textField = new TextField();

        grid.add(btn,0,1);
        grid.add(intro,0,0);

        
        BorderPane totalPage = new BorderPane();
        totalPage.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
        
        
	}
}
