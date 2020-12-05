package group_20;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreateGameWindow extends BorderPane{
	private final int BUTTON_MAX_WIDTH = 100;
	private final int BUTTON_MAX_HEIGHT = 60;
	private ComboBox<String> templatesDropDown = new ComboBox<String>();
	private ComboBox<String> player1Profiles = new ComboBox<String>();
	private ComboBox<String> player2Profiles = new ComboBox<String>();
	private ComboBox<String> player3Profiles = new ComboBox<String>();
	private ComboBox<String> player4Profiles = new ComboBox<String>();
	
	public CreateGameWindow() {
		//Top of GUI
				FlowPane fp = new FlowPane();
				fp.setAlignment(javafx.geometry.Pos.CENTER);
				Text t = new Text("Select a board template: ");
				Button select = new Button("Select");
				fp.getChildren().addAll(t,templatesDropDown,select);
				this.setTop(fp);
				
				//Bottom of GUI
				BorderPane bp = new BorderPane();
				Button backButton = new Button("Back");
				Button startGameButton = new Button("Create Game");
				startGameButton.setMinSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
				//backButton.autosize();
				//startGameButton.autosize();
				bp.setRight(startGameButton);
				bp.setLeft(backButton);
				bp.setPadding(new Insets(10));
				this.setBottom(bp);
				
				this.loadSavedGameNames();
				
				select.setOnMouseClicked(e -> {
					System.out.println(templatesDropDown.getValue());
				});
				
				VBox vb = new VBox();
				RadioButton rb1 = new RadioButton("2 Players");
				RadioButton rb2 = new RadioButton("3 Players");
				RadioButton rb3 = new RadioButton("4 Players");
				ToggleGroup group = new ToggleGroup();
				rb1.setToggleGroup(group);
				rb2.setToggleGroup(group);
				rb3.setToggleGroup(group);
				
				//ComboBox<String> player1Profiles = new ComboBox<String>();
				Text player1Text = new Text("Player 1's Profile: ");
				FlowPane fpPlayer1 = new FlowPane();
				fpPlayer1.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer1.getChildren().addAll(player1Text,player1Profiles);
				
				//ComboBox<String> player2Profiles = new ComboBox<String>();
				Text player2Text = new Text("Player 2's Profile: ");
				FlowPane fpPlayer2 = new FlowPane();
				fpPlayer2.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer2.getChildren().addAll(player2Text,player2Profiles);
				
				//ComboBox<String> player3Profiles = new ComboBox<String>();
				Text player3Text = new Text("Player 3's Profile: ");
				FlowPane fpPlayer3 = new FlowPane();
				fpPlayer3.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer3.getChildren().addAll(player3Text,player3Profiles);
				
				//ComboBox<String> player4Profiles = new ComboBox<String>();
				Text player4Text = new Text("Player 4's Profile: ");
				FlowPane fpPlayer4 = new FlowPane();
				fpPlayer4.setAlignment(javafx.geometry.Pos.CENTER);
				fpPlayer4.getChildren().addAll(player4Text,player4Profiles);
				
				vb.getChildren().addAll(rb1,rb2,rb3,fpPlayer1,fpPlayer2,fpPlayer3,fpPlayer4);
				vb.setAlignment(javafx.geometry.Pos.CENTER);
				this.setCenter(vb);
				
				this.loadProfileNames();
	}
	
	public void loadSavedGameNames() {
		File folder = new File("templates/");
		File[] listOfFiles = folder.listFiles();
		
		for (File f: listOfFiles) {
			templatesDropDown.getItems().add(f.getName());
			System.out.println(f.getName());
		}
	}
	
	public void loadProfileNames() {
		File folder = new File("profiles/");
		File[] listOfFiles = folder.listFiles();
		
		for (File f: listOfFiles) {
			player1Profiles.getItems().add(f.getName());
			player2Profiles.getItems().add(f.getName());
			player3Profiles.getItems().add(f.getName());
			player4Profiles.getItems().add(f.getName());
			System.out.println(f.getName());
		}
	}
}
