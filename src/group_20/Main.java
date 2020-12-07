package group_20;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {
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
	private static final String TITLE_IMG_DIR_PATH = CONFIG_DIR_PATH + "img" + SEP;
	
	private static final String TITLE_MUSIC_DIR_PATH = CONFIG_DIR_PATH + "audio" + SEP;
	
	private static final String TITLE_MUSIC_AUDIO_FILE_PATH = TITLE_MUSIC_DIR_PATH + "backgroundmusic.mp3";
	
	/**
	 * 
	 */
	private static final String TITLE_SCREEN_IMG_FILEPATH = TITLE_IMG_DIR_PATH + "title_screen_animation.gif";
	
	/**
	 * 
	 */
	private static final String MAIN_MENU_IMG_FILEPATH = TITLE_IMG_DIR_PATH + "title_screen_animation_no_text.gif";
	
	//private static final int BOARD_WIDTH = 9;
	//private static final int BOARD_LENGTH = 9;
	//private static final int VBOX_WIDTH = 170;
	//private static final int FLOWPANE_HEIGHT = 75;
	//private static final int CANVAS_HEIGHT = TILE_WIDTH * BOARD_LENGTH;
	//private static final int CANVAS_WIDTH = TILE_WIDTH * BOARD_WIDTH;
	//private static final int WINDOW_HEIGHT = CANVAS_HEIGHT + FLOWPANE_HEIGHT;
	//private static final int WINDOW_WIDTH = CANVAS_WIDTH + VBOX_WIDTH;
	//private static Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	//private Board currentBoard = new Board(canvas.getGraphicsContext2D(),BOARD_WIDTH,BOARD_LENGTH);
	
	//private static final Double WINDOW_HEIGHT = Screen.getPrimary().getBounds().getHeight();
	//private static final Double WINDOW_WIDTH = Screen.getPrimary().getBounds().getWidth();
	private static final int WINDOW_HEIGHT = 1000;
	private static final int WINDOW_WIDTH = 1500;
	public static final int TILE_WIDTH = 90;
	private static MediaPlayer jukebox;
	static Stage primaryStage;
	
	//==Yoshan
//	private static final int SHAPE_SIZE = 20;
//	private static final int SHAPE_SIZE_UPPER_BOUND = 100;
//	public static final int TILE_WIDTH = 80;
//	private static final int BOARD_WIDTH = 9;
//	private static final int BOARD_LENGTH = 9;
//	private static final int CANVAS_HEIGHT = TILE_WIDTH * BOARD_LENGTH;
//	private static final int CANVAS_WIDTH = TILE_WIDTH * BOARD_WIDTH;
//	private static final int WINDOW_HEIGHT = CANVAS_HEIGHT + 100;
//	private static final int WINDOW_WIDTH = CANVAS_WIDTH + 300;
//	public static Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
//	static Board currentBoard = new Board(canvas.getGraphicsContext2D(),BOARD_WIDTH,BOARD_LENGTH);
//	static Stage primaryStage;
	//==
	
	public static void main(String[] args) {
		//System.out.println(Screen.getScreens().get(1).getBounds().getWidth() + "x" + Screen.getScreens().get(1).getBounds().getHeight());
		//System.out.println(currentBoard.saveFormat());
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		//==Yoshan
//		Main.primaryStage = primaryStage;
//		//Scene leaderboard = new Scene(new leaderboardWindow(),WINDOW_WIDTH, WINDOW_HEIGHT);
//		//Scene createGame = new Scene(new CreateGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
//		Scene loadGame = new Scene(new LoadGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
//		//Scene game = new Scene(new BoardWindow(currentBoard), WINDOW_WIDTH, WINDOW_HEIGHT);
//		primaryStage.setScene(loadGame);
//		primaryStage.show();
		//==
		
		Main.primaryStage = primaryStage;
		primaryStage.setTitle("Labryinth");
        
		FileInputStream initialBackgroundImg = new FileInputStream(TITLE_SCREEN_IMG_FILEPATH);
        Image initialMenuImage = new Image(initialBackgroundImg); 
        ImageView initialMenuImageView = new ImageView(initialMenuImage);
        initialMenuImageView.setFitHeight(1000);
        initialMenuImageView.setFitWidth(1000);
        initialMenuImageView.setPreserveRatio(true); 
        
        FileInputStream initialMenuBackingImg = new FileInputStream(MAIN_MENU_IMG_FILEPATH);
        Image initialMenuImg = new Image(initialMenuBackingImg); 
        BackgroundImage initialMenuPicture = new BackgroundImage(initialMenuImg, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
       
        StackPane initialTitleScreen = new StackPane();
        initialTitleScreen.setBackground(new Background(initialMenuPicture));
        initialTitleScreen.getChildren().addAll(initialMenuImageView);
        
        String backgroundMusic =  TITLE_MUSIC_AUDIO_FILE_PATH;
        Media backgroundTrack = new Media(new File(backgroundMusic).toURI().toString());
        jukebox = new MediaPlayer(backgroundTrack);
        //jukebox.play();
        
    	Scene initialMenuScene = new Scene(initialTitleScreen, 1500, 1000);
        primaryStage.setScene(initialMenuScene);
        primaryStage.show();
        
        initialMenuScene.setOnKeyPressed(e -> {
			Main.setSceneToMainMenu();
		});
	}
	
	public static void setSceneToMainMenu() {
        Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(new MainWindow(TILE_WIDTH), 1500, 1000);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
	
	public static void exitGameButtonFunctionality() {
		System.exit(0);
	}
	
	public static void profileGameButtonFunctionality() {
		Scene profileMenu = new Scene(new ProfileWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
    	primaryStage.setScene(profileMenu);
    	primaryStage.show();
	}
	
	public static void setSceneToLoadGame() {
		Scene scene = new Scene(new LoadGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void setSceneToCreateGame() {
		Scene scene = new Scene(new CreateGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
