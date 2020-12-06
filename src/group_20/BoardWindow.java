package group_20;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BoardWindow extends BorderPane {
	private final int CANVAS_WIDTH;
	private final int CANVAS_HEIGHT;
	private Board board;
	private Canvas gameCanvas;
	private GraphicsContext gc;
	private Button backTrackActionButton = new Button("Backtrack Action");
	private Button fireActionButton = new Button("Fire Action");
	private Button iceActionButton = new Button("Ice Action");
	private Button doubleMoveActionButton = new Button("Double Move Action");
	private Button skipButton = new Button("Skip");
	
	public BoardWindow(Board board) {
		this.board = board;
		CANVAS_WIDTH = this.board.getWidth() * Main.TILE_WIDTH;
		CANVAS_HEIGHT = this.board.getLength() * Main.TILE_WIDTH;
		this.gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.gc = this.gameCanvas.getGraphicsContext2D();
		this.board.setGraphicsContext(this.gc);
		
		this.setCenter(this.gameCanvas);
		
		//this.board.highlightValidMoves();
		
		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> refreshBoard()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		//this.setOnClickEventMovePlayer();
		
		VBox sidebar = new VBox();
		this.setLeft(sidebar);
		
		sidebar.setPadding(new Insets(40));
		sidebar.setSpacing(40);
		
		//Backtrack
		sidebar.getChildren().addAll(backTrackActionButton);
		
		backTrackActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new BacktrackAction()));
				synchronized (p) {
					p.notify();
				}
				System.out.println("Backtrack action selected");
			}
		});
		
		//Fire action
		sidebar.getChildren().addAll(fireActionButton);
		
		fireActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new FireAction()));
				synchronized (p) {
					p.notify();
				}
				System.out.println("Fire action selected");
			}
		});
		
		//Ice action
		sidebar.getChildren().addAll(iceActionButton);
		
		iceActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new IceAction()));
				synchronized (p) {
					p.notify();
				}
				System.out.println("Ice action selected");
			}
		});
		
		//Double move
		sidebar.getChildren().addAll(doubleMoveActionButton);
		
		doubleMoveActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new DoubleMoveAction()));
				synchronized (p) {
					p.notify();
				}
				System.out.println("Double move action selected");
			}
		});
		
		//Skip button
		sidebar.getChildren().addAll(skipButton);
		
		skipButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				System.out.println("Is the tile null? " + ((new ActionTile(null)) == null));
				p.setChosenActionTile(new ActionTile(null));
				synchronized (p) {
					p.notify();
				}
				System.out.println("Skipped playing action tile");
			}
		});
		
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
		
		FlowPane fp = new FlowPane();
		fp.setAlignment(javafx.geometry.Pos.CENTER);
		Button endTurnButton = new Button("End turn");
		Button saveAndExitButton = new Button("Save & Exit");
		fp.getChildren().addAll(saveAndExitButton,endTurnButton);
		//fp.setPadding(new Insets(Main.TILE_WIDTH));
		this.setBottom(fp);
		
		endTurnButton.setOnMouseClicked(e -> {
			if (this.board.isWaitingForExitOrContinue()) {
				this.board.setContinueGame(true);
				synchronized (this.board) {
					board.notify();
				}
				System.out.println("Player wanted to continue O_O");
				refreshBoard();
			}
		});
		
		saveAndExitButton.setOnMouseClicked(e -> {
			if (this.board.isWaitingForExitOrContinue()) {
				System.out.println("oh... bye then...");
				System.exit(0);
			}
		});
	}
	
	private void refreshBoard() {
		//System.out.println("Refreshed");
//		Inventory inv = this.board.getCurrentPlayer().getInventory();
//		if (!inv.contains(new FireAction())) {
//			this.fireActionButton.setDisable(true);
//		}
		this.gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.board.draw();
	}
	
	private void refreshInventoryPane() {
		
	}
	
	/**
	 * Just for testing rn
	 */
//	private void drawInventory() {
//		ArrayList<Direction> directions = new ArrayList<Direction>();
//		directions.add(Direction.SOUTH);
//		directions.add(Direction.WEST);
//		Direction orientation = Direction.EAST;
//		
//		FloorTile t = new Straight(TILE_WIDTH, "straight_tile_with_alligners.png", directions, orientation, null, null, null, 0);
//		
//		for (int i = 0; i < this.board.getLength(); i++) {
//			t.draw(inventoryCanvas.getGraphicsContext2D(), 0, i*TILE_WIDTH);
//		}
//	}
}
