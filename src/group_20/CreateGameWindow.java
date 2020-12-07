package group_20;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreateGameWindow extends BorderPane{
	/**
	 * 
	 */
	private static final String SEP = File.separator;
	
	/**
	 * 
	 */
	private static final String CONFIG_DIR_PATH = ".lairofdagon" + SEP;
	
	/**
	 * 
	 */
	private static final String TEMPLATES_DIR_PATH = CONFIG_DIR_PATH + "templates" + SEP;
	
	private final int BUTTON_MAX_WIDTH = 100;
	private final int BUTTON_MAX_HEIGHT = 60;
	private ComboBox<String> templatesDropdown = new ComboBox<String>();
	private ComboBox<String> player1ProfileDropdown = new ComboBox<String>();
	private ComboBox<String> player2ProfileDropdown = new ComboBox<String>();
	private ComboBox<String> player3ProfileDropdown = new ComboBox<String>();
	private ComboBox<String> player4ProfileDropdown = new ComboBox<String>();
	private String templateFileName;
	private int numPlayers;
	private String[] playerProfiles = new String[4];
	private TextArea filenameTextbox;
//	private String player1Profile;
//	private String player2Profile;
//	private String player3Profile;
//	private String player4Profile;
	
	public CreateGameWindow() {
				//Top of GUI
				FlowPane fp = new FlowPane();
				fp.setAlignment(javafx.geometry.Pos.CENTER);
				Text templateText = new Text("Select a board template: ");
				Button selectButton = new Button("Select");
				fp.getChildren().addAll(templateText,templatesDropdown,selectButton);
				this.setTop(fp);
				
				//Bottom of GUI
				BorderPane bp = new BorderPane();
				Button backButton = new Button("Back");
				Button createGameButton = new Button("Create Game");
				createGameButton.setMinSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
				//backButton.autosize();
				//startGameButton.autosize();
				bp.setRight(createGameButton);
				bp.setLeft(backButton);
				bp.setPadding(new Insets(10));
				this.setBottom(bp);
				
				this.loadSavedGameNames();
				
				selectButton.setOnMouseClicked(e -> {
					System.out.println(templatesDropdown.getValue());
				});
				
				createGameButton.setOnMouseClicked(e -> {
					if (validValuesSelected()) {
						System.out.println("Yeah valid values selected");
						//Create new BoardWindow from a Board object
						System.out.println("Number of players: " + numPlayers);
						System.out.println("Template selected: " + templatesDropdown.getValue());
						System.out.println("Filename = " + filenameTextbox.getText());
						if (playerProfiles[0] == null || playerProfiles[0].equals("Guest")) {
							System.out.println("Player 1's Profile: null");
						} else {
							System.out.println("Player 1's Profile: " + playerProfiles[0]);
						}
						
						if (playerProfiles[1] == null || playerProfiles[1].equals("Guest")) {
							System.out.println("Player 2's Profile: null");
						} else {
							System.out.println("Player 2's Profile: " + playerProfiles[1]);
						}
						
						if (playerProfiles[2] == null || playerProfiles[2].equals("Guest")) {
							System.out.println("Player 3's Profile: null");
						} else {
							System.out.println("Player 3's Profile: " + playerProfiles[2]);
						}
						
						if (playerProfiles[3] == null || playerProfiles[3].equals("Guest")) {
							System.out.println("Player 4's Profile: null");
						} else {
							System.out.println("Player 4's Profile: " + playerProfiles[3]);
						}
						//IO.loadNewGame(numPlayers,templatesDropdown.getValue(),filenameTextbox.getText(), playerProfiles);
						try {
							Board b = IO.loadNewGame(templatesDropdown.getValue(), filenameTextbox.getText(), numPlayers, new Profile[4]);
							//System.out.println(b.saveFormat());
							Main.setSceneToBoardWindow(b);
						} catch (FileNotFoundException | FileParseException e5) {
							// TODO Auto-generated catch block
							e5.printStackTrace();
						}
						//IO.loadNewGame(numPlayers,templatesDropdown.getValue(),filenameTextbox.getText(), new Profile[4]);
					} else {
						System.out.println("Invalid selection");
					}
				});
				
				backButton.setOnMouseClicked(e -> {
					System.out.println("Going to main menu");
					Main.setSceneToMainMenu();
				});
				
				VBox vb = new VBox();
				RadioButton rb1 = new RadioButton("2 Players");
				RadioButton rb2 = new RadioButton("3 Players");
				RadioButton rb3 = new RadioButton("4 Players");
				ToggleGroup group = new ToggleGroup();
				rb1.setToggleGroup(group);
				rb2.setToggleGroup(group);
				rb3.setToggleGroup(group);
				
				rb1.setOnAction(e -> {
					System.out.println("Radio button 1 clicked");
					this.changePlayersShown(2);
					this.numPlayers = 2;
				});
				rb2.setOnAction(e -> {
					System.out.println("Radio button 2 clicked");
					this.changePlayersShown(3);
					this.numPlayers = 3;
				});
				rb3.setOnAction(e -> {
					System.out.println("Radio button 3 clicked");
					this.changePlayersShown(4);
					this.numPlayers = 4;
				});
				
				//ComboBox<String> player1Profiles = new ComboBox<String>();
				Text player1Text = new Text("Player 1's Profile: ");
				FlowPane fpPlayer1 = new FlowPane();
				fpPlayer1.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer1.getChildren().addAll(player1Text,player1ProfileDropdown);
				
				//ComboBox<String> player2Profiles = new ComboBox<String>();
				Text player2Text = new Text("Player 2's Profile: ");
				FlowPane fpPlayer2 = new FlowPane();
				fpPlayer2.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer2.getChildren().addAll(player2Text,player2ProfileDropdown);
				
				//ComboBox<String> player3Profiles = new ComboBox<String>();
				Text player3Text = new Text("Player 3's Profile: ");
				FlowPane fpPlayer3 = new FlowPane();
				fpPlayer3.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer3.getChildren().addAll(player3Text,player3ProfileDropdown);
				
				//ComboBox<String> player4Profiles = new ComboBox<String>();
				Text player4Text = new Text("Player 4's Profile: ");
				FlowPane fpPlayer4 = new FlowPane();
				fpPlayer4.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer4.getChildren().addAll(player4Text,player4ProfileDropdown);
				
				player1ProfileDropdown.setOnAction(e1 -> {
					//removeValueFromComboBoxes(player1Profiles.getValue());
					System.out.println(player1ProfileDropdown.getValue());
					this.playerProfiles[0] = player1ProfileDropdown.getValue();
				});
				
				player2ProfileDropdown.setOnAction(e2 -> {
					//removeValueFromComboBoxes(player2Profiles.getValue());
					System.out.println(player2ProfileDropdown.getValue());
					this.playerProfiles[1] = player2ProfileDropdown.getValue();
				});
				
				player3ProfileDropdown.setOnAction(e3 -> {
					//removeValueFromComboBoxes(player3Profiles.getValue());
					System.out.println(player3ProfileDropdown.getValue());
					this.playerProfiles[2] = player3ProfileDropdown.getValue();
				});
				
				player4ProfileDropdown.setOnAction(e4 -> {
					//removeValueFromComboBoxes(player4Profiles.getValue());
					System.out.println(player4ProfileDropdown.getValue());
					this.playerProfiles[3] = player4ProfileDropdown.getValue();
				});
				
				FlowPane fpFilename = new FlowPane();
				fpFilename.setAlignment(javafx.geometry.Pos.CENTER);
				Text filenameText = new Text("Please enter a file name: ");
				filenameTextbox = new TextArea();
				filenameTextbox.setMaxWidth(200);
				filenameTextbox.setMaxHeight(10);
				
				fpFilename.getChildren().addAll(filenameText,filenameTextbox);
				
				vb.getChildren().addAll(fpFilename,rb1,rb2,rb3,fpPlayer1,fpPlayer2,fpPlayer3,fpPlayer4);
				vb.setAlignment(javafx.geometry.Pos.CENTER);
				this.setCenter(vb);
				
				this.changePlayersShown(0);
				this.addValueToComboBoxes("Guest");
				this.loadProfileNames();
	}
	
	public void loadSavedGameNames() {
		File folder = new File(TEMPLATES_DIR_PATH);
		File[] listOfFiles = folder.listFiles();
		
		for (File f: listOfFiles) {
			templatesDropdown.getItems().add(f.getName());
			System.out.println(f.getName());
		}
	}
	
	public void loadProfileNames() {
		File folder = new File("templates/");
		File[] listOfFiles = folder.listFiles();
		
		for (File f: listOfFiles) {
			addValueToComboBoxes(f.getName());
			System.out.println(f.getName());
		}
	}
	
	public void changePlayersShown(int numPlayersToShow) {
		this.clearProfileSelection();
		switch (numPlayersToShow) {
		case 2:
			player1ProfileDropdown.setDisable(false);
			player2ProfileDropdown.setDisable(false);
			player3ProfileDropdown.setDisable(true);
			player4ProfileDropdown.setDisable(true);
			break;
		case 3:
			player1ProfileDropdown.setDisable(false);
			player2ProfileDropdown.setDisable(false);
			player3ProfileDropdown.setDisable(false);
			player4ProfileDropdown.setDisable(true);
			break;
		case 4:
			player1ProfileDropdown.setDisable(false);
			player2ProfileDropdown.setDisable(false);
			player3ProfileDropdown.setDisable(false);
			player4ProfileDropdown.setDisable(false);
			break;
		default:
			player1ProfileDropdown.setDisable(true);
			player2ProfileDropdown.setDisable(true);
			player3ProfileDropdown.setDisable(true);
			player4ProfileDropdown.setDisable(true);	
		}
	}
	
	public void addValueToComboBoxes(String val) {
		player1ProfileDropdown.getItems().add(val);
		player2ProfileDropdown.getItems().add(val);
		player3ProfileDropdown.getItems().add(val);
		player4ProfileDropdown.getItems().add(val);
	}
	
	public void clearProfileSelection() {
		player1ProfileDropdown.getSelectionModel().clearSelection();
		player2ProfileDropdown.getSelectionModel().clearSelection();
		player3ProfileDropdown.getSelectionModel().clearSelection();
		player4ProfileDropdown.getSelectionModel().clearSelection();
		
		for (int i = 0; i < numPlayers; i++) {
			playerProfiles[i] = null;
		}
	}
	
	private boolean validValuesSelected() {
		if (templatesDropdown.getValue() == null) {
			System.out.println("No template selected");
			return false;
		}
		
		if (numPlayers == 0) {
			System.out.println("No player number selected");
			return false;
		}
		
		if (filenameTextbox.getText() == null) {
			System.out.println("No filename entered");
			return false;
		}
		
		for (int i = 0; i < this.numPlayers; i++) {
			String compA = this.playerProfiles[i];
			for (int j = 0; j < this.numPlayers; j++) {
				String compB = this.playerProfiles[j];
				if ((i != j) && (compA != null && compB != null) && (!compA.equals("Guest")) && (!compB.equals("Guest")) && (compA.equals(compB))) {
					System.out.println("Duplicate profiles picked");
					return false;
				}
			}
		}
		
		return true;
	}
}
