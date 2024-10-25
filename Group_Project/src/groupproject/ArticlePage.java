package groupproject;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Class that creates a page for password reset and validation </p>
 */
public class ArticlePage {

	public Scene scene;
	public Button btn;
	public String title = "Help Item List";
	public Button back;
	
	//public int currUserIndex;
	public User u;
	
    public TextField userField;
    public PasswordField passwordField;
    public PasswordField cPasswordField;
    
    /**
     * constructor that lays out the GUI
     */
	public ArticlePage() {
		GridPane grid = new GridPane();
	    grid.setPadding(new Insets(10, 10, 10, 10));
		
	    back = new Button("Back");
	    
        Label helpItemListLabel = new Label("Help Items:");
        ObservableList<String> articles = FXCollections.observableArrayList();
        ArrayList<Integer> ids = new ArrayList<>();
        // Creating a ListView, show all registered users
        for(HelpItem h : App.items) {
        	articles.add(h.description);
        	ids.add(h.id);
        }
        ListView<String> articleList = new ListView<>(articles);
        
        
        Button create = new Button("Create");
        Button view = new Button("View");
        Button delete = new Button("Delete");
        Button update = new Button("Update");

        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(view,create,update,delete);
        
        grid.add(helpItemListLabel, 0, 0);
        grid.add(articleList, 0, 1);
        grid.add(buttons, 1, 1);
        grid.add(back, 0, 20);
        
        
        
        VBox display = new VBox();
        HBox twoColumns = new HBox();
        twoColumns.setSpacing(125);
        twoColumns.getChildren().addAll(grid,display);
        
        
        VBox viewDisplay = new VBox();
        viewDisplay.setSpacing(10);
 
        
        
        Label titleLabel = new Label("Title: ");
        Label descriptionLabel = new Label("Description: ");
        Label bodyLabel = new Label("Body: ");
        Label keywordsLabel = new Label("Keywords: ");
        Label groupLabel = new Label("Groups: ");
        Label linkLabel = new Label("Links: ");
        viewDisplay.getChildren().addAll(new Label(),titleLabel, descriptionLabel, bodyLabel, keywordsLabel, linkLabel, groupLabel);
       
        bodyLabel.setWrapText(true);
        bodyLabel.setPrefWidth(500);
         
        view.setOnAction(e ->{
        	int idx = articleList.getSelectionModel().getSelectedIndex();
        	HelpItem h = App.items.get(idx);
        	titleLabel.setText("Title: " + h.title);
        	descriptionLabel.setText("Description: " + h.description);
        	bodyLabel.setText("Body: " + h.body);
        	keywordsLabel.setText("Keywords: " + HelpItem.listToStringPretty(h.keywords));
        	linkLabel.setText("Links: " + HelpItem.listToStringPretty(h.links));
        	groupLabel.setText("Groups: " + HelpItem.listToStringPretty(h.groups));            
        	twoColumns.getChildren().set(1, viewDisplay);
        });
                
        
        BorderPane totalPage = new BorderPane();
        totalPage.setLeft(twoColumns);
        BorderPane.setMargin(twoColumns, new Insets(100));
        twoColumns.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}


        
	
	
   
}

