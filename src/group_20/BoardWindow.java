package group_20;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
		
		this.board.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
		this.board.highlightValidMoves();
		
		this.setOnClickEventMovePlayer();
		
		VBox sidebar = new VBox();
		this.setLeft(sidebar);
		
		Button drawTileButton = new Button("Draw Tile");
		sidebar.getChildren().addAll(drawTileButton);
		drawTileButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			p.drawTile();
			this.refreshBoard();
			if (p.drewFloorTile()) {
				this.board.highlightValidInsertLocations();
				this.setOnClickEventInsertTile();
			}
		});
	}
	
	private void setOnClickEventMovePlayer() {
		this.canvas.setOnMouseClicked(e -> {
			this.board.movePlayer(e.getX(), e.getY());
			this.refreshBoard();
			this.board.highlightValidMoves();
		});
	}
	
	private void setOnClickEventInsertTile() {
		Player p = this.board.getCurrentPlayer();
		this.canvas.setOnMouseClicked(e2 -> {
			//System.out.println("Can I do this?");
			Location l = this.board.getCoordinateOfClick(e2.getX(), e2.getY());
			if (this.board.canInsertAt(l)) {
				p.insertTile(l);
				this.setOnClickEventMovePlayer();
				this.refreshBoard();
				this.board.highlightValidMoves();
			} else {
				System.out.println("Can't insert there m8");
				this.refreshBoard();
				this.board.highlightValidInsertLocations();
			}
		});
	}
	
	private void refreshBoard() {
		System.out.println("Refreshed");
		this.gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.board.draw(canvas.getGraphicsContext2D(), TILE_WIDTH);
	}
}
