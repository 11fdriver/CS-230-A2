package group_20;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

public class Main extends Application {
	private static final int SHAPE_SIZE = 20;
	private static final int SHAPE_SIZE_UPPER_BOUND = 100;
	public static final int TILE_WIDTH = 120;
	private static final int BOARD_WIDTH = 9;
	private static final int BOARD_LENGTH = 9;
	private static final int CANVAS_HEIGHT = TILE_WIDTH * BOARD_LENGTH;
	private static final int CANVAS_WIDTH = TILE_WIDTH * BOARD_WIDTH;
	private static final int WINDOW_HEIGHT = CANVAS_HEIGHT + 100;
	private static final int WINDOW_WIDTH = CANVAS_WIDTH + 300;
	private Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	Board currentBoard = new Board(canvas.getGraphicsContext2D(),TILE_WIDTH,BOARD_WIDTH,BOARD_LENGTH);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Pane root = createPane();
		
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Pane createPane() {
		BorderPane root = new BorderPane();
		
		root.setCenter(canvas);
		
		VBox sidebar = new VBox();
		root.setLeft(sidebar);
		
		Button button1 = new Button("Down");
		sidebar.getChildren().addAll(button1);
		
		button1.setOnAction(e -> {
			//createRandomCircle();
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.getCurrentPlayer().move(Direction.SOUTH);
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button2 = new Button("Right");
		sidebar.getChildren().addAll(button2);
		
		button2.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.getCurrentPlayer().move(Direction.EAST);
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button3 = new Button("Left");
		sidebar.getChildren().addAll(button3);
		
		button3.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.getCurrentPlayer().move(Direction.WEST);
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button4 = new Button("Up");
		sidebar.getChildren().addAll(button4);
		
		button4.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.getCurrentPlayer().move(Direction.NORTH);
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button5 = new Button("Random");
		sidebar.getChildren().addAll(button5);
		
		button5.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.getCurrentPlayer().randomizeLocation(BOARD_WIDTH, BOARD_LENGTH);;
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button6 = new Button("Insert Tile");
		sidebar.getChildren().addAll(button6);
		
		button6.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.insertTile(new Goal(0,false,0,false,false,new Location(0,0),null,"Goal"), new Location(4,BOARD_LENGTH-1));
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button7 = new Button("Randomize Board");
		sidebar.getChildren().addAll(button7);
		
		button7.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.randomizeBoard();
			currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		});
		
		Button button8 = new Button("Start Game");
		sidebar.getChildren().addAll(button8);
		
		button8.setOnAction(e -> {
			canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			currentBoard.startGame();
		});
		
		currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> onTime()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		//currentBoard.calculateArea(new Location(0,0));
		//System.out.println("Number of tiles in bounds: " + currentBoard.calculateArea(new Location(0,0)).length);
		//System.out.println("(Testing tile finder)Number of tiles in bounds: " + currentBoard.tempTestCalculateArea().length);
		
//		ArrayList<FloorTile> ls = new ArrayList<FloorTile>();
//		ls.add(new FloorTile());
//		ls.add(new FloorTile());
//		
//		for (int i = 0; i < ls.size(); i++) {
//			FloorTile currentTile = ls.get(i);
//			currentTile.draw(i * SHAPE_SIZE, i * SHAPE_SIZE, canvas.getGraphicsContext2D());
//		}
		
		return root;
	}
	
	private void onTime() {
		canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.currentBoard.startGame();
	}
	
	private void createRandomCircle() {
		Random r = new Random();
		
		int x = r.nextInt(CANVAS_WIDTH);
		int y = r.nextInt(CANVAS_HEIGHT);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		int diameter = r.nextInt(SHAPE_SIZE_UPPER_BOUND);
		gc.strokeOval(x, y, diameter, diameter);
	}
}
