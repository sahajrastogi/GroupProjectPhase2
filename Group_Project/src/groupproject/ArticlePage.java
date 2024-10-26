package groupproject;

import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
        ObservableList<String> items = FXCollections.observableArrayList();
        ArrayList<Integer> ids = new ArrayList<>();
        // Creating a ListView, show all registered users
        for(HelpItem h : App.items) {
        	items.add(h.description);
        	ids.add(h.id);
        }
        ListView<String> itemList = new ListView<>(items);
        
        
        Button create = new Button("Create");
        Button view = new Button("View");
        Button delete = new Button("Delete");
        Button update = new Button("Update");

        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(view,create,update,delete);
        
        grid.add(helpItemListLabel, 0, 0);
        grid.add(itemList, 0, 1);
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
       
        bodyLabel.setWrapText(true);
        bodyLabel.setPrefWidth(500);
         
        view.setOnAction(e ->{
        	int idx = itemList.getSelectionModel().getSelectedIndex();
        	if(idx == -1) return;
        	viewDisplay.getChildren().clear();
            viewDisplay.getChildren().addAll(new Label(),titleLabel, descriptionLabel, bodyLabel, keywordsLabel, linkLabel, groupLabel);

        	//System.out.println(idx);
        	HelpItem h = App.items.get(idx);
        	titleLabel.setText("Title: " + h.title);
        	descriptionLabel.setText("Description: " + h.description);
        	bodyLabel.setText("Body: " + h.body);
        	keywordsLabel.setText("Keywords: " + HelpItem.listToStringPretty(h.keywords));
        	linkLabel.setText("Links: " + HelpItem.listToStringPretty(h.links));
        	groupLabel.setText("Groups: " + HelpItem.listToStringPretty(h.groups));            
        	twoColumns.getChildren().set(1, viewDisplay);
        });
        
        
        
        delete.setOnAction(e->{
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to delete this article?");
            Optional<ButtonType> result = alert.showAndWait();
            boolean res = result.isPresent() && result.get() == ButtonType.OK;
            if(!res) return;
            
        	int idx = itemList.getSelectionModel().getSelectedIndex();
        	if(idx == -1) return;
        	//System.out.println(idx);
        	int itemIndex = ids.get(idx);
        	ids.remove(idx);
        	items.remove(idx);
        	HelpItem.removeByID(itemIndex);
//        	try {
//				HelpItem.backup("check.txt", "all");
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
        });
        
        TextField titleEntry = new TextField();
        TextField descriptionEntry = new TextField();
        TextArea bodyEntry = new TextArea();
        VBox createDisplay = new VBox();

        
        create.setOnAction(e->{
        	createDisplay.getChildren().clear();
            createDisplay.getChildren().addAll(new Label(),titleLabel, descriptionLabel, bodyLabel, keywordsLabel, linkLabel, groupLabel,titleEntry,descriptionEntry,bodyEntry);
        	titleLabel.setText("Title: ");
        	descriptionLabel.setText("Description: ");
        	bodyLabel.setText("Body: ");
        	keywordsLabel.setText("Keywords: ");
        	linkLabel.setText("Links: ");
        	groupLabel.setText("Groups: ");
        	twoColumns.getChildren().set(1, createDisplay);

        });
        
        BorderPane totalPage = new BorderPane();
        totalPage.setLeft(twoColumns);
        BorderPane.setMargin(twoColumns, new Insets(100));
        twoColumns.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}


        
	
	
   
}

