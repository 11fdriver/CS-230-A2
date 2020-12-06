package group_20;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;

public class ProfileWindow extends BorderPane {
	private final int TILE_WIDTH;
	ArrayList<Profile> profiles = new ArrayList<Profile>();
	private TableView profileTable = new TableView();
	private FlowPane profileOptions = new FlowPane();
	private Button createProfileButton = new Button("Create Profile");
	private Button deleteProfileButton = new Button("Delete Profile");
	
	public ProfileWindow(int TILE_WIDTH) {
		this.TILE_WIDTH = TILE_WIDTH;
		
		profiles.add(new Profile("Indy",null,null,null));
		profiles.add(new Profile("Jeff",null,null,null));
		profiles.add(new Profile("Ed",null,null,null));
		//Profile.getArrayListOfProfileInstances()
		TableColumn<Profile, String> nameColumn = new TableColumn<>("Name");
		TableColumn<Profile, Integer> winsColumn = new TableColumn<>("Wins");
		TableColumn<Profile, Integer> lossesColumn = new TableColumn<>("Losses");
		TableColumn<Profile, Integer> gamesPlayedColumn = new TableColumn<>("Games Played");
		
		profileTable.setPlaceholder(new Label("No profiles to display"));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
		lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
		gamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
		
		profileTable.getColumns().add(nameColumn);
		profileTable.getColumns().add(winsColumn);
		profileTable.getColumns().add(lossesColumn);
		profileTable.getColumns().add(gamesPlayedColumn);
	    
		profileTable.getItems().addAll(profiles);
	    
	    TilePane nameEntryPane = new TilePane();
	    Label nameEntryIndicator = new Label("  Enter new profile name here:  ");
	    TextField nameEntryField = new TextField();
	    
	    nameEntryPane.getChildren().add(nameEntryIndicator);
	    nameEntryPane.getChildren().add(nameEntryField);
    	
	    this.setBottom(nameEntryPane);
    	
	    createProfileButton.setOnAction(e -> {
	    	String profileName = nameEntryField.getText();
	    	profiles.add(new Profile(profileName,null,null,null));
	    	profileTable.getItems().add(profiles.get(profiles.size() - 1));
		});
	    
	    deleteProfileButton.setOnAction(e -> {
	    	profileTable.getItems().remove(profileTable.getSelectionModel().getSelectedItem());
		});
		
	    this.setCenter(profileTable);
	    profileOptions.setAlignment(javafx.geometry.Pos.CENTER);
	    profileOptions.getChildren().addAll(createProfileButton, deleteProfileButton);
		this.setTop(profileOptions);
	}

}
