package groupproject;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
    ObservableList<String> items = FXCollections.observableArrayList();
    ArrayList<Integer> ids = new ArrayList<>();
    
    /**
     * constructor that lays out the GUI
     */
	public ArticlePage() {
		GridPane grid = new GridPane();
	    grid.setPadding(new Insets(10, 10, 10, 10));
		
	    back = new Button("Back");
	    
        Label helpItemListLabel = new Label("Help Items:");
        
        updateList();
        ListView<String> itemList = new ListView<>(items);
        
        
        Button create = new Button("Create");
        Button view = new Button("View");
        Button delete = new Button("Delete");
        Button update = new Button("Update");
        Button backup = new Button("Backup");
        Button restore = new Button("Restore");

        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(view,create,update,delete,backup,restore);
        
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
       
        
         
        view.setOnAction(e ->{
        	int idx = itemList.getSelectionModel().getSelectedIndex();
        	if(idx == -1) return;
        	bodyLabel.setWrapText(true);
            bodyLabel.setPrefWidth(500);
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
        	HelpItem.removeByID(itemIndex);
        	updateList();
//        	ids.remove(idx);
//        	items.remove(idx);
        });
        
        TextField titleEntry = new TextField();
        TextField descriptionEntry = new TextField();
        TextArea bodyEntry = new TextArea();
        TextField keywordsEntry = new TextField();
        TextField linkEntry = new TextField();
        TextField groupEntry = new TextField();


        VBox createDisplay = new VBox();
        GridPane fieldGrid = new GridPane();
	    Button createButton = new Button("Create");
	    
	    createButton.setOnAction(e->{
	    	if(titleEntry.getText().equals("") || descriptionEntry.getText().equals("") || bodyEntry.getText().equals("")) {
	    		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Title, Description, or Body is missing");
        		alert.showAndWait();
        		return;
	    	}
	    	HelpItem.add(titleEntry.getText(), descriptionEntry.getText(), bodyEntry.getText(), keywordsEntry.getText(), linkEntry.getText(), groupEntry.getText());
	    	updateList();
	    	//items.add(descriptionEntry.getText());
//	    	ids.add(id);
	    });
        
        create.setOnAction(e->{
        	fieldGrid.setPadding(new Insets(10, 10, 10, 10));
    	    fieldGrid.setVgap(3);
    	    fieldGrid.getChildren().clear();
        	titleEntry.setPrefHeight(12);
        	descriptionEntry.setPrefHeight(12);
        	bodyEntry.setPrefHeight(100);
        	bodyEntry.setMaxHeight(400);
        	bodyEntry.setWrapText(true);
        	keywordsEntry.setPrefHeight(12);
        	linkEntry.setPrefHeight(12);
        	groupEntry.setPrefHeight(12);


	
        	fieldGrid.add(titleLabel, 0, 0); fieldGrid.add(titleEntry, 1, 0);
        	fieldGrid.add(descriptionLabel, 0, 1); fieldGrid.add(descriptionEntry, 1, 1);
        	fieldGrid.add(bodyLabel, 0, 2); fieldGrid.add(bodyEntry, 1, 2);	
        	fieldGrid.add(keywordsLabel, 0, 3); fieldGrid.add(keywordsEntry, 1, 3);	
        	fieldGrid.add(linkLabel, 0, 4); fieldGrid.add(linkEntry, 1, 4);	
        	fieldGrid.add(groupLabel, 0, 5); fieldGrid.add(groupEntry, 1, 5);	
        	fieldGrid.add(createButton, 1, 6);
        	
        	GridPane.setHalignment(titleLabel, HPos.RIGHT);
        	GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
        	GridPane.setHalignment(bodyLabel, HPos.RIGHT);
        	GridPane.setValignment(bodyLabel, VPos.TOP);
        	GridPane.setHalignment(keywordsLabel, HPos.RIGHT);
        	GridPane.setHalignment(linkLabel, HPos.RIGHT);
        	GridPane.setHalignment(groupLabel, HPos.RIGHT);
        	
        	
        	createDisplay.getChildren().clear();
            createDisplay.getChildren().addAll(new Label(),fieldGrid);
            
            titleLabel.setText("Title: ");
        	descriptionLabel.setText("Description: ");
            bodyLabel.setPrefWidth(-1);
        	bodyLabel.setText("Body: ");
        	keywordsLabel.setText("Keywords: ");
        	linkLabel.setText("Links: ");
        	groupLabel.setText("Groups: ");
        	
        	titleEntry.clear();
        	descriptionEntry.clear();
        	bodyEntry.clear();
        	keywordsEntry.clear();
        	linkEntry.clear();
        	groupEntry.clear();

        	twoColumns.getChildren().set(1, createDisplay);

        });
        
        Button updateButton = new Button("Update");
        VBox updateDisplay = new VBox();
        
        
        
        update.setOnAction(e->{
        	int idx = itemList.getSelectionModel().getSelectedIndex();
        	if(idx == -1) return;
        	fieldGrid.setPadding(new Insets(10, 10, 10, 10));
    	    fieldGrid.setVgap(3);
    	    fieldGrid.getChildren().clear();
        	titleEntry.setPrefHeight(12);
        	descriptionEntry.setPrefHeight(12);
        	bodyEntry.setPrefHeight(100);
        	bodyEntry.setMaxHeight(400);
        	bodyEntry.setWrapText(true);
        	keywordsEntry.setPrefHeight(12);
        	linkEntry.setPrefHeight(12);
        	groupEntry.setPrefHeight(12);


	
        	fieldGrid.add(titleLabel, 0, 0); fieldGrid.add(titleEntry, 1, 0);
        	fieldGrid.add(descriptionLabel, 0, 1); fieldGrid.add(descriptionEntry, 1, 1);
        	fieldGrid.add(bodyLabel, 0, 2); fieldGrid.add(bodyEntry, 1, 2);	
        	fieldGrid.add(keywordsLabel, 0, 3); fieldGrid.add(keywordsEntry, 1, 3);	
        	fieldGrid.add(linkLabel, 0, 4); fieldGrid.add(linkEntry, 1, 4);	
        	fieldGrid.add(groupLabel, 0, 5); fieldGrid.add(groupEntry, 1, 5);	
        	fieldGrid.add(updateButton, 1, 6);
        	
        	GridPane.setHalignment(titleLabel, HPos.RIGHT);
        	GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
        	GridPane.setHalignment(bodyLabel, HPos.RIGHT);
        	GridPane.setValignment(bodyLabel, VPos.TOP);
        	GridPane.setHalignment(keywordsLabel, HPos.RIGHT);
        	GridPane.setHalignment(linkLabel, HPos.RIGHT);
        	GridPane.setHalignment(groupLabel, HPos.RIGHT);
        	
        	
        	updateDisplay.getChildren().clear();
        	updateDisplay.getChildren().addAll(new Label(),fieldGrid);
            
            HelpItem h = HelpItem.itemByID(ids.get(idx));
            titleLabel.setText("Title: ");
        	descriptionLabel.setText("Description: ");
            bodyLabel.setPrefWidth(-1);
        	bodyLabel.setText("Body: ");
        	keywordsLabel.setText("Keywords: ");
        	linkLabel.setText("Links: ");
        	groupLabel.setText("Groups: ");
        	
        	titleEntry.setText(h.title);
        	descriptionEntry.setText(h.description);
        	bodyEntry.setText(h.body);
        	keywordsEntry.setText(HelpItem.listToStringPretty(h.keywords));
        	linkEntry.setText(HelpItem.listToStringPretty(h.links));
        	groupEntry.setText(HelpItem.listToStringPretty(h.groups));
        	
        	twoColumns.getChildren().set(1, updateDisplay);

        	updateButton.setOnAction(event->{
            	h.title = titleEntry.getText();
            	h.description = descriptionEntry.getText();
            	h.body = bodyEntry.getText();
            	h.keywords = HelpItem.prettyStringToList(keywordsEntry.getText());
            	h.links = HelpItem.prettyStringToList(linkEntry.getText());
            	h.groups = HelpItem.prettyStringToList(groupEntry.getText());
            	items.set(idx, h.description);
            });
        });
        
        
        VBox restoreDisplay = new VBox();
        Label restoreLabel = new Label("File Name:   ");
        TextField restoreField = new TextField();
        CheckBox restoreCheck = new CheckBox();
        Label overwriteLabel = new Label("Overwrite?  ");
        Button restoreButton = new Button("Restore");
        GridPane restoreGrid = new GridPane();
        

        restore.setOnAction(e->{
        	restoreGrid.setPadding(new Insets(10, 10, 10, 10));
        	restoreGrid.setVgap(3);
        	restoreGrid.getChildren().clear();
        	restoreGrid.add(restoreLabel, 0, 0); restoreGrid.add(restoreField, 1, 0);
        	restoreGrid.add(overwriteLabel, 0, 1);  restoreGrid.add(restoreCheck, 1, 1);
        	restoreGrid.add(restoreButton, 1, 2);

        	restoreDisplay.getChildren().clear();
        	restoreDisplay.getChildren().addAll(restoreGrid);
 
        	restoreField.clear();
        	restoreCheck.setSelected(true);
        	
        	twoColumns.getChildren().set(1, restoreDisplay);

        });
        
        restoreButton.setOnAction(e->{
        	try {
				HelpItem.restore(restoreField.getText().trim(), !restoreCheck.isSelected());
				updateList();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error");
        		alert.setContentText("Invalid file name");
        		alert.showAndWait();
				//e1.printStackTrace();
			}
        });
        BorderPane totalPage = new BorderPane();
        totalPage.setLeft(twoColumns);
        BorderPane.setMargin(twoColumns, new Insets(100));
        twoColumns.setAlignment(Pos.CENTER);
        scene = new Scene(totalPage, App.WIDTH, App.HEIGHT);
	}

	private void updateList() {
		// Creating a ListView, show all registered users
		items.clear();
		ids.clear();
        for(HelpItem h : App.items) {
        	items.add(h.description);
        	ids.add(h.id);
        }
	}

        
	
	
   
}

