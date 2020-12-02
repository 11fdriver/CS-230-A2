package group_20;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BoardWindow extends BorderPane {
	private final int TILE_WIDTH;
	private final int CANVAS_WIDTH;
	private final int CANVAS_HEIGHT;
	private Board board;
	private Canvas canvas;
	private GraphicsContext gc;
	
	public BoardWindow(int TILE_WIDTH, Board board) {
		this.TILE_WIDTH = TILE_WIDTH;
		this.board = board;
		CANVAS_WIDTH = this.board.getWidth() * TILE_WIDTH;
		CANVAS_HEIGHT = this.board.getLength() * TILE_WIDTH;
		this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.gc = this.canvas.getGraphicsContext2D();
		this.board.setGraphicsContext(this.gc);
		
		this.setCenter(this.canvas);
		
		//this.board.highlightValidMoves();
		
		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> refreshBoard()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		//this.setOnClickEventMovePlayer();
		
		VBox sidebar = new VBox();
		this.setLeft(sidebar);
		
		Thread tr = new Thread(board);
		tr.setDaemon(true);
		tr.start();
		
		refreshBoard();
		
		this.canvas.setOnMouseClicked(e -> {
			this.board.setLastClickLocation(e.getX(), e.getY());
			synchronized (this.board) {
				board.notify();
			}
			System.out.println("You clicked tile: " + this.board.getlastClickLocation());
			refreshBoard();
		});
		
//		Button button1 = new Button("Button");
//		sidebar.getChildren().addAll(button1);
//		
//		button1.setOnAction(e -> {
//			
//		});
	}
	
	private void refreshBoard() {
		//System.out.println("Refreshed");
		this.gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.board.draw();
	}
}
