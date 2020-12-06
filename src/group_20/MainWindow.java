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

public class MainWindow extends BorderPane {
	private final int TILE_WIDTH;
	private static MediaPlayer keyPressFX;
	private static Text motdText = new Text();
	private Button createGameButton = new Button();
	private Button leaderboardsButton = new Button();
	private Button loadGameButton = new Button();
	private Button exitGameButton = new Button();
	private Button profilesButton = new Button();
	
	public MainWindow(int TILE_WIDTH) throws FileNotFoundException {
		this.TILE_WIDTH = TILE_WIDTH;
		
		String initialMenuPressSound = "thunder.mp3";
    	Media initialMenuKeyPress = new Media(new File(initialMenuPressSound).toURI().toString());
    	keyPressFX = new MediaPlayer(initialMenuKeyPress);
    	keyPressFX.play();
    	
    	FileInputStream titleScreenImg = new FileInputStream("title_screen_animation_title_only.gif");
		Image titleScreenImage = new Image(titleScreenImg); 
        ImageView titleScreenImageView = new ImageView(titleScreenImage);
        
        StackPane titleScreen = new StackPane();
       
        FileInputStream titleScreenBackgroundImg = new FileInputStream("title_screen_animation_no_text.gif");
        Image titleScreenBackgroundImage = new Image(titleScreenBackgroundImg); 
        BackgroundImage titleScreenBackingImg = new BackgroundImage(titleScreenBackgroundImage, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
        titleScreen.setBackground(new Background(titleScreenBackingImg));
        
        FileInputStream startButtonImage = new FileInputStream("Create_game.png");
		Image createGameImage = new Image(startButtonImage); 
        ImageView createGameImageView = new ImageView(createGameImage);
        
        FileInputStream leaderboardButtonImage = new FileInputStream("Leaderboards.png");
		Image leaderboardImage = new Image(leaderboardButtonImage); 
        ImageView leaderboardButtonImageView = new ImageView(leaderboardImage);
        
        FileInputStream loadButtonImage = new FileInputStream("Load_Game.png");
		Image loadImage = new Image(loadButtonImage); 
        ImageView loadButtonImageView = new ImageView(loadImage);
        
        FileInputStream exitButtonImage = new FileInputStream("Exit_game.png");
		Image exitImage = new Image(exitButtonImage); 
        ImageView exitButtonImageView = new ImageView(exitImage);
        
        FileInputStream profilesButtonImage = new FileInputStream("profiles.png");
		Image profileImage = new Image(profilesButtonImage); 
        ImageView profileButtonImageView = new ImageView(profileImage);
        
        createGameButton.setGraphic(createGameImageView);
        createGameButton.setStyle("-fx-background-color: Black");
        
        leaderboardsButton.setGraphic(leaderboardButtonImageView);
        leaderboardsButton.setStyle("-fx-background-color: Black");
        
        loadGameButton.setGraphic(loadButtonImageView);
        loadGameButton.setStyle("-fx-background-color: Black");
       
        exitGameButton.setGraphic(exitButtonImageView);
        exitGameButton.setStyle("-fx-background-color: Black");
        
        profilesButton.setGraphic(profileButtonImageView);
        profilesButton.setStyle("-fx-background-color: Black");
        
        titleScreenImageView.setFitHeight(1000);
        titleScreenImageView.setFitWidth(1000); 
        titleScreenImageView.setPreserveRatio(true); 
        titleScreen.getChildren().add(titleScreenImageView);
    	
        titleScreen.getChildren().addAll(createGameButton, leaderboardsButton, loadGameButton, exitGameButton, profilesButton);
        
        createGameButton.setTranslateY(-50);
        leaderboardsButton.setTranslateY(50);
        loadGameButton.setTranslateY(150);
        exitGameButton.setTranslateY(250);
        profilesButton.setTranslateY(350);
        
        this.setCenter(titleScreen);
        
        try {
			motdText.setText(MessageOfTheDay.getMessage());
			motdText.setTranslateY(450);
			motdText.setFont(Font.font ("Verdana", 15));
			motdText.setFill(Color.WHITE);
			titleScreen.getChildren().add(motdText);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        loadGameButton.setOnAction(e -> {
        	Main.loadGameButtonFunctionality();
		});
        exitGameButton.setOnAction(e -> {
        	Main.exitGameButtonFunctionality();
		});
        profilesButton.setOnAction(e -> {
        	Main.profileGameButtonFunctionality();
		});
	
	}
}
