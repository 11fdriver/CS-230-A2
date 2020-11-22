package group_20;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

public class Main extends Application {
	private static final int WINDOW_HEIGHT = 500;
	private static final int WINDOW_WIDTH = 600;
	private static final int CANVAS_HEIGHT = 400;
	private static final int CANVAS_WIDTH = 400;
	private static final int SHAPE_SIZE = 20;
	private static final int SHAPE_SIZE_UPPER_BOUND = 100;
	private static final int TILE_WIDTH = 40;
	private static final int BOARD_WIDTH = 9;
	private static final int BOARD_LENGTH = 9;
	private Canvas canvas;
	Board currentBoard = new Board(1,BOARD_WIDTH,BOARD_LENGTH,new SilkBag());
	
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
		
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
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
		
		currentBoard.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		//currentBoard.calculateArea(new Location(0,0));
		System.out.println("Number of tiles in bounds: " + currentBoard.calculateArea(new Location(0,0)).length);
		System.out.println("(Testing tile finder)Number of tiles in bounds: " + currentBoard.tempTestCalculateArea().length);
		
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
	
	private void createRandomCircle() {
		Random r = new Random();
		
		int x = r.nextInt(CANVAS_WIDTH);
		int y = r.nextInt(CANVAS_HEIGHT);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		int diameter = r.nextInt(SHAPE_SIZE_UPPER_BOUND);
		gc.strokeOval(x, y, diameter, diameter);
	}
}
