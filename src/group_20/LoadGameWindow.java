package group_20;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LoadGameWindow extends BorderPane{
	private final int BUTTON_MAX_WIDTH = 100;
	private final int BUTTON_MAX_HEIGHT = 60;
	private ComboBox<String> savedGamesDropDown = new ComboBox<String>();
	
	public LoadGameWindow() {
		//Top of GUI
		FlowPane fp = new FlowPane();
		fp.setAlignment(javafx.geometry.Pos.CENTER);
		Text t = new Text("Select a saved game: ");
		Button select = new Button("Select");
		fp.getChildren().addAll(t,savedGamesDropDown,select);
		this.setTop(fp);
		
		//Bottom of GUI
		BorderPane bp = new BorderPane();
		Button backButton = new Button("Back");
		Button startGameButton = new Button("Start Game");
		startGameButton.setMinSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
		//backButton.autosize();
		//startGameButton.autosize();
		bp.setRight(startGameButton);
		bp.setLeft(backButton);
		bp.setPadding(new Insets(10));
		this.setBottom(bp);
		
		this.testFileNameReading();
		
		select.setOnMouseClicked(e -> {
			System.out.println(savedGamesDropDown.getValue());
		});
	}
	
	public void testFileNameReading() {
		File folder = new File("savedGames/");
		File[] listOfFiles = folder.listFiles();
		
		for (File f: listOfFiles) {
			savedGamesDropDown.getItems().add(f.getName());
			System.out.println(f.getName());
		}
	}
}
