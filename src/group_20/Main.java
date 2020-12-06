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
	private static final int SHAPE_SIZE = 20;
	private static final int SHAPE_SIZE_UPPER_BOUND = 100;
	private static final int TILE_WIDTH = 90;
	private static final int BOARD_WIDTH = 9;
	private static final int BOARD_LENGTH = 9;
	private static final int VBOX_WIDTH = 170;
	private static final int FLOWPANE_HEIGHT = 75;
	private static final int CANVAS_HEIGHT = TILE_WIDTH * BOARD_LENGTH;
	private static final int CANVAS_WIDTH = TILE_WIDTH * BOARD_WIDTH;
	private static final int WINDOW_HEIGHT = CANVAS_HEIGHT + FLOWPANE_HEIGHT;
	private static final int WINDOW_WIDTH = CANVAS_WIDTH + VBOX_WIDTH;
	public static Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	static Board currentBoard = new Board(canvas,TILE_WIDTH,BOARD_WIDTH,BOARD_LENGTH);
	private static MediaPlayer jukebox;
	static Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		Main.primaryStage = primaryStage;
		primaryStage.setTitle("Labryinth");
        
		FileInputStream initialBackgroundImg = new FileInputStream("title_screen_animation.gif");
        Image initialMenuImage = new Image(initialBackgroundImg); 
        ImageView initialMenuImageView = new ImageView(initialMenuImage);
        initialMenuImageView.setFitHeight(1000);
        initialMenuImageView.setFitWidth(1000);
        initialMenuImageView.setPreserveRatio(true); 
        
        FileInputStream initialMenuBackingImg = new FileInputStream("title_screen_animation_no_text.gif");
        Image initialMenuImg = new Image(initialMenuBackingImg); 
        BackgroundImage initialMenuPicture = new BackgroundImage(initialMenuImg, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
       
        StackPane initialTitleScreen = new StackPane();
        initialTitleScreen.setBackground(new Background(initialMenuPicture));
        initialTitleScreen.getChildren().addAll(initialMenuImageView);
        
        String backgroundMusic =  "backgroundmusic.mp3";
        Media backgroundTrack = new Media(new File(backgroundMusic).toURI().toString());
        jukebox = new MediaPlayer(backgroundTrack);
        jukebox.play();
        
    	Scene initialMenuScene = new Scene(initialTitleScreen, 1500, 1000);
        primaryStage.setScene(initialMenuScene);
        primaryStage.show();
        
        initialMenuScene.setOnKeyPressed(e -> {
			try {
				Main.setSceneToMainMenu();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

	}
	
	public static void setSceneToMainMenu() throws FileNotFoundException {
        Scene mainMenuScene = new Scene(new MainWindow(TILE_WIDTH), 1500, 1000);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
	
	public static void loadGameButtonFunctionality() {
		Scene gameSession = new Scene(new BoardWindow(TILE_WIDTH, currentBoard), WINDOW_WIDTH, WINDOW_HEIGHT);
    	primaryStage.setScene(gameSession);
    	primaryStage.show();
	}
	
	public static void exitGameButtonFunctionality() {
		System.exit(0);
	}
	
	public static void profileGameButtonFunctionality() {
		Scene profileMenu = new Scene(new ProfileWindow(TILE_WIDTH), WINDOW_WIDTH, WINDOW_HEIGHT);
    	primaryStage.setScene(profileMenu);
    	primaryStage.show();
	}
	
}
