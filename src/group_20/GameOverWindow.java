package group_20;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;

/**
 * Class for showing the winning player
 * @author Yoshan Mumberson
 *
 */
public class GameOverWindow extends BorderPane{
	/**
	 * File separator for determining file paths of various images and audio
	 */
	private static final String SEP = File.separator;
	
	/**
	 * File path for all of the game assets
	 */
	private static final String CONFIG_DIR_PATH = ".lairofdagon" + SEP;
	
	/**
	 * File path for all of the game image assets
	 */
	private static final String IMG_DIR_PATH = CONFIG_DIR_PATH + "img" + SEP;
	
	public GameOverWindow(int winningPlayer) {
		String filename = null;
		switch (winningPlayer) {
		case 1:
			filename = IMG_DIR_PATH + "Game_over_player_1.png";
			break;
		case 2:
			filename = IMG_DIR_PATH + "Game_over_player_2.png";
			break;
		case 3:
			filename = IMG_DIR_PATH + "Game_over_player_3.png";
			break;
		case 4:
			filename = IMG_DIR_PATH + "Game_over_player_4.png";
			break;
		default:
			Main.setSceneToMainMenu();
		}
		
		FileInputStream titleScreenBackgroundImg = null;
		try {
			titleScreenBackgroundImg = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Image titleScreenBackgroundImage = new Image(titleScreenBackgroundImg); 
        BackgroundImage titleScreenBackingImg = new BackgroundImage(titleScreenBackgroundImage, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
        this.setBackground(new Background(titleScreenBackingImg));
		
		this.setOnMouseClicked(e -> {
			Main.setSceneToMainMenu();
		});
	}
}
