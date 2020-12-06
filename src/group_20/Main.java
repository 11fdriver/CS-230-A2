package group_20;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class Main extends Application {
	private static final int SHAPE_SIZE = 20;
	private static final int SHAPE_SIZE_UPPER_BOUND = 100;
	public static final int TILE_WIDTH = 80;
	private static final int BOARD_WIDTH = 9;
	private static final int BOARD_LENGTH = 9;
	private static final int CANVAS_HEIGHT = TILE_WIDTH * BOARD_LENGTH;
	private static final int CANVAS_WIDTH = TILE_WIDTH * BOARD_WIDTH;
	private static final int WINDOW_HEIGHT = CANVAS_HEIGHT + 100;
	private static final int WINDOW_WIDTH = CANVAS_WIDTH + 300;
	public static Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	static Board currentBoard = new Board(canvas.getGraphicsContext2D(),BOARD_WIDTH,BOARD_LENGTH);
	static Stage primaryStage;
	
	public static void main(String[] args) {
		//System.out.println(Screen.getScreens().get(1).getBounds().getWidth() + "x" + Screen.getScreens().get(1).getBounds().getHeight());
		//System.out.println(currentBoard.saveFormat());
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		//Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		Main.primaryStage = primaryStage;
		Scene leaderboard = new Scene(new leaderboardWindow(),WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene createGame = new Scene(new CreateGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene loadGame = new Scene(new LoadGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
		//Scene game = new Scene(new BoardWindow(currentBoard), WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(createGame);
		primaryStage.show();
	}
	
	public static void setSceneToLoadGame() {
		Scene scene = new Scene(new LoadGameWindow(), WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
