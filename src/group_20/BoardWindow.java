package group_20;

import java.util.ArrayList;

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
	private Canvas gameCanvas;
	private Canvas inventoryCanvas;
	private GraphicsContext gc;
	
	public BoardWindow(int TILE_WIDTH, Board board) {
		this.TILE_WIDTH = TILE_WIDTH;
		this.board = board;
		CANVAS_WIDTH = this.board.getWidth() * TILE_WIDTH;
		CANVAS_HEIGHT = this.board.getLength() * TILE_WIDTH;
		this.gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.inventoryCanvas = new Canvas(TILE_WIDTH, CANVAS_HEIGHT);
		this.gc = this.gameCanvas.getGraphicsContext2D();
		this.board.setGraphicsContext(this.gc);
		
		this.setCenter(this.gameCanvas);
		
		//this.board.highlightValidMoves();
		
		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> refreshBoard()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		//this.setOnClickEventMovePlayer();
		
		//VBox sidebar = new VBox();
		//this.setLeft(sidebar);
		
		Thread tr = new Thread(board);
		tr.setDaemon(true);
		tr.start();
		
		refreshBoard();
		
		this.gameCanvas.setOnMouseClicked(e -> {
			this.board.setLastClickLocation(e.getX(), e.getY());
			synchronized (this.board) {
				board.notify();
			}
			System.out.println("You clicked tile: " + this.board.getlastClickLocation());
			refreshBoard();
		});
		
		this.setLeft(inventoryCanvas);
		drawInventory();
		
		this.inventoryCanvas.setOnMouseClicked(e -> {
			this.board.getCurrentPlayer().setHasBeenClicked(true);
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
			//if (tr.getState() == Thread.State.WAITING) {
			synchronized (p) {
				p.notify();
			}
			}
			//System.out.println("You clicked + " + e.getX() + "," + e.getY());
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
	
	/**
	 * Just for testing rn
	 */
	private void drawInventory() {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		directions.add(Direction.SOUTH);
		directions.add(Direction.WEST);
		Direction orientation = Direction.EAST;
		
		FloorTile t = new Straight(TILE_WIDTH, "straight_tile_with_alligners.png", directions, orientation, null, null, null, 0);
		
		for (int i = 0; i < this.board.getLength(); i++) {
			t.draw(inventoryCanvas.getGraphicsContext2D(), 0, i*TILE_WIDTH);
		}
	}
}
