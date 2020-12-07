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

/**
 * <p><b>Filename:</b> ProfileWindow</p>
 * <p><b>Description:</b> Loads the GUI for the create/delete profile menu.</p>
 * <p><b>Creation date:</b> 06/12/2020</p>
 * @since 07/12/2020
 * @author Inderpreet Sandhu - 852298
 * <p><b>Copyright:</b> no copyright</p>
 */

public class ProfileWindow extends BorderPane {
	
	/**
	 * List of player profiles to be displayed
	 */
	ArrayList<Profile> profiles = new ArrayList<Profile>();
	
	/**
	 * Table containing all existing player profiles
	 */
	private TableView profileTable = new TableView();
	
	/**
	 * Contains buttons for deleting/creating profiles
	 */
	private FlowPane profileOptions = new FlowPane();
	
	/**
	 * Button used to create a new player profile
	 */
	private Button createProfileButton = new Button("Create Profile");
	
	/**
	 * Button used to delete an existing player profile
	 */
	private Button deleteProfileButton = new Button("Delete Profile");
	
	/**
	 * Creates a window for player profiles to be displayed on			
	 */
	public ProfileWindow() {
        int[][] profile0 = {{171,115,83}, {88,75,55} ,{83,40,28}};    //Alice
        int[][] profile1 = {{92,81,144}, {79,34,100} ,{13,47,44}};    //Bob
        int[][] profile2 = {{154,0,91}, {91,0,17} ,{62,0,74}};    //Chuck
        int[][] profile3 = {{88,96,68}, {59,66,47} ,{29,30,21}};    //Craig
        int[][] profile4 = {{155,115,101}, {55,37,79} ,{100,78,22}}; 
        int[][] newProfile = {{0,0,0}, {0,0,0} ,{0,0,0}}; 

        profiles.add(new Profile("Alice", profile0[0], profile0[1], profile0[2]));
        profiles.add(new Profile("Bob", profile1[0], profile1[1], profile1[2]));
        profiles.add(new Profile("Chuck", profile2[0], profile2[1], profile2[2]));
        profiles.add(new Profile("Craig", profile3[0], profile3[1], profile3[2]));
        profiles.add(new Profile("Charlie", profile4[0], profile4[1], profile4[2]));
		
//		profiles.add(new Profile("Indy",null,null,null));
//		profiles.add(new Profile("Jeff",null,null,null));
//		profiles.add(new Profile("Ed",null,null,null));
		//Profile.getArrayListOfProfileInstances()
		TableColumn<Profile, String> nameColumn = new TableColumn<>("Name");
		TableColumn<Profile, Integer> winsColumn = new TableColumn<>("Wins");
		TableColumn<Profile, Integer> lossesColumn = new TableColumn<>("Losses");
		TableColumn<Profile, Integer> gamesPlayedColumn = new TableColumn<>("Games Played");
		
		profileTable.setPlaceholder(new Label("No profiles to display"));
		
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("totalWins"));
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("totalLosses"));
        gamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("totalGamesPlayed"));
		
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
	    	profiles.add(new Profile(profileName, newProfile[0], newProfile[1], newProfile[2]));
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