

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private static final int CANVAS_HEIGHT = TILE_WIDTH * BOARD_LENGTH;
	private static final int CANVAS_WIDTH = TILE_WIDTH * BOARD_WIDTH;
	private static final int WINDOW_HEIGHT = CANVAS_HEIGHT;
	private static final int WINDOW_WIDTH = CANVAS_WIDTH + VBOX_WIDTH;
	public static Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	Board currentBoard = new Board(canvas,TILE_WIDTH,BOARD_WIDTH,BOARD_LENGTH);
	private static MediaPlayer jukebox;
	private static MediaPlayer buttonPress;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Labryinth");
        Button btn = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        Button btn4 = new Button();
        FileInputStream backgroundImg = new FileInputStream("title_screen_animation.gif");
        Image menuImage = new Image(backgroundImg); 
        ImageView imageView = new ImageView(menuImage);
        FileInputStream backgroundImg5 = new FileInputStream("title_screen_animation_no_text.gif");
        Image menuImage5 = new Image(backgroundImg5); 
        BackgroundImage img2 = new BackgroundImage(menuImage5, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
       
        StackPane titleScreen = new StackPane();
       
        titleScreen.setBackground(new Background(img2));
        imageView.setFitHeight(1000);
        imageView.setFitWidth(1000);
        imageView.setPreserveRatio(true); 
        titleScreen.getChildren().addAll(imageView);
        String gameTunes =  "backgroundmusic.mp3";
        Media song = new Media(new File(gameTunes).toURI().toString());
        jukebox = new MediaPlayer(song);
        jukebox.play();
        String buttonPressFX = "thunder.mp3";
    	Media buttonPress1 = new Media(new File(buttonPressFX).toURI().toString());
    	buttonPress = new MediaPlayer(buttonPress1);
    	Scene scene = new Scene(titleScreen, 950, 950);
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(e -> {
			try {
				buttonPress.play();
				FileInputStream backgroundImg1 = new FileInputStream("title_screen_animation_title_only.gif");
				Image menuImage1 = new Image(backgroundImg1); 
		        ImageView imageView1 = new ImageView(menuImage1);
		        StackPane titleScreen1 = new StackPane();
		        FileInputStream backgroundImg6 = new FileInputStream("title_screen_animation_no_text.gif");
		        Image menuImage6 = new Image(backgroundImg6); 
		        BackgroundImage img3 = new BackgroundImage(menuImage6, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
		        titleScreen1.setBackground(new Background(img3));
		        FileInputStream startButtonImage = new FileInputStream("Start_Game.png");
				Image startImage = new Image(startButtonImage); 
		        ImageView imageView2 = new ImageView(startImage);
		        FileInputStream leaderboardButtonImage = new FileInputStream("Leaderboards.png");
				Image leaderboardImage = new Image(leaderboardButtonImage); 
		        ImageView imageView3 = new ImageView(leaderboardImage);
		        FileInputStream loadButtonImage = new FileInputStream("Load_Game.png");
				Image loadImage = new Image(loadButtonImage); 
		        ImageView imageView4 = new ImageView(loadImage);
		        FileInputStream exitButtonImage = new FileInputStream("Exit_game.png");
				Image exitImage = new Image(exitButtonImage); 
		        ImageView imageView6 = new ImageView(exitImage);
		        btn.setPrefSize(40, 40);
		        btn.setGraphic(imageView2);
		        btn.setStyle("-fx-background-color: Black");
		        btn2.setPrefSize(40, 40);
		        btn2.setGraphic(imageView3);
		        btn2.setStyle("-fx-background-color: Black");
		        btn3.setPrefSize(40, 40);
		        btn3.setGraphic(imageView4);
		        btn3.setStyle("-fx-background-color: Black");
		        btn4.setPrefSize(40, 40);
		        btn4.setGraphic(imageView6);
		        btn4.setStyle("-fx-background-color: Black");
		        imageView1.setFitHeight(1000);
		        imageView1.setFitWidth(1000); 
		        imageView1.setPreserveRatio(true); 
		        titleScreen1.getChildren().addAll(imageView1);
		    	titleScreen1.getChildren().add(btn);
		        btn.setTranslateY(-50);
		        titleScreen1.getChildren().add(btn2);
		        btn2.setTranslateY(50);
		        titleScreen1.getChildren().add(btn3);
		        btn3.setTranslateY(150);
		        titleScreen1.getChildren().add(btn4);
		        btn4.setTranslateY(250);
		        Scene scene1 = new Scene(titleScreen1, 950, 950);  
		        //primaryStage.setResizable(false);
		        primaryStage.setScene(scene1);
		        primaryStage.show();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
        });
        btn.setOnAction(e -> {
        	Scene gameSession = new Scene(new BoardWindow(this.TILE_WIDTH, this.currentBoard), WINDOW_WIDTH, WINDOW_HEIGHT);
        	primaryStage.setScene(gameSession);
        	primaryStage.show();
		});
        btn4.setOnAction(e -> {
        	System.exit(0);
		});
		
	}
}
