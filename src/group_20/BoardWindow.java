package group_20;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BoardWindow extends BorderPane {
	private final int TILE_WIDTH;
	private final int CANVAS_WIDTH;
	private final int CANVAS_HEIGHT;
	private final int VBOX_WIDTH = 180;
	private Board board;
	private Canvas gameCanvas;
	private Canvas inventoryCanvas;
	private GraphicsContext gc;
	private Button backTrackActionButton = new Button();
	private Button fireActionButton = new Button();
	private Button iceActionButton = new Button();
	private Button doubleMoveActionButton = new Button();
	private Button skipButton = new Button();
	private Text backtrackActionAmount = new Text();
	private Text fireActionAmount = new Text();
	private Text iceActionAmount = new Text();
	private Text doubleMoveActionAmount = new Text();
	private VBox playerInventoryPane = new VBox();
	private VBox backtrackPane = new VBox();
	private VBox firePane = new VBox();
	private VBox icePane = new VBox();
	private VBox doublemovePane = new VBox();
	private VBox skipPane = new VBox();
	private int backtrackInventoryAmount = 0;
	private int fireInventoryAmount = 0;
	private int iceInventoryAmount = 0;
	private int doubleMoveInventoryAmount = 0;
	
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
		
		this.board.draw();
		this.board.highlightValidMoves();
		
		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> refreshBoard()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		
		FileInputStream backgroundImg;
		try {
			backgroundImg = new FileInputStream("title_screen_animation_no_text.gif");
			Image gameBackground = new Image(backgroundImg);
			BackgroundImage gameSessionImage = new BackgroundImage(gameBackground, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
			this.setBackground(new Background(gameSessionImage));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} 
		
		this.setLeft(playerInventoryPane);
		
		FileInputStream backtrackButtonImage;
		try {
			backtrackButtonImage = new FileInputStream("backtrack-icon.png");
			Image backtrackImage = new Image(backtrackButtonImage); 
	        ImageView backtrackButtonImageView = new ImageView(backtrackImage);
	        backTrackActionButton.setGraphic(backtrackButtonImageView);
	        backTrackActionButton.setStyle("-fx-background-color: Black");
	        backTrackActionButton.setTooltip(new Tooltip("Press this to play a Backtrack Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Text backtrackLabel = new Text("Backtrack");
		backtrackLabel.setFont(Font.font ("Verdana", 20));
		backtrackLabel.setFill(Color.WHITE);
		
		backtrackPane.getChildren().add(backtrackLabel);
		backtrackPane.getChildren().add(backTrackActionButton);
		backtrackPane.getChildren().add(returnBacktrack());
		
		backtrackActionAmount.setFont(Font.font ("Verdana", 20));
		backtrackActionAmount.setFill(Color.WHITE);
		
		backTrackActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new BacktrackAction()));
				synchronized (p) {
					p.notify();
				}
				p.getInventory().remove(new BacktrackAction());
				returnBacktrack();
				System.out.println("Backtrack action selected");
			}
		});
		
		FileInputStream fireButtonImage;
		try {
			fireButtonImage = new FileInputStream("cast-fire-button.png");
			Image fireImage = new Image(fireButtonImage); 
	        ImageView fireButtonImageView = new ImageView(fireImage);
	        fireActionButton.setGraphic(fireButtonImageView);
	        fireActionButton.setStyle("-fx-background-color: Black");
	        fireActionButton.setTooltip(new Tooltip("Press this to play a Fire Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Text fireLabel = new Text("Fire");
		fireLabel.setFont(Font.font ("Verdana", 20));
		fireLabel.setFill(Color.WHITE);
		
		firePane.getChildren().add(fireLabel);
		firePane.getChildren().add(fireActionButton);
		firePane.getChildren().add(returnFire());

		fireActionAmount.setFont(Font.font ("Verdana", 20));
		fireActionAmount.setFill(Color.WHITE);
		
		fireActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new FireAction()));
				synchronized (p) {
					p.notify();
				}
				p.getInventory().remove(new FireAction());
				returnFire();
				System.out.println("Fire action selected");
			}
		});
		
		FileInputStream iceButtonImage;
		try {
			iceButtonImage = new FileInputStream("cast-ice-button.png");
			Image iceImage = new Image(iceButtonImage); 
	        ImageView iceButtonImageView = new ImageView(iceImage);
	        iceActionButton.setGraphic(iceButtonImageView);
	        iceActionButton.setStyle("-fx-background-color: Black");
	        iceActionButton.setTooltip(new Tooltip("Press this to play a Ice Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text iceLabel = new Text("Ice");
		iceLabel.setFont(Font.font ("Verdana", 20));
		iceLabel.setFill(Color.WHITE);
		
		icePane.getChildren().add(iceLabel);
		icePane.getChildren().add(iceActionButton);
		icePane.getChildren().add(returnIce());

		iceActionAmount.setFont(Font.font ("Verdana", 20));
		iceActionAmount.setFill(Color.WHITE);
		
		iceActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new IceAction()));
				synchronized (p) {
					p.notify();
				}
				p.getInventory().remove(new IceAction());
				returnIce();
				System.out.println("Ice action selected");
			}
		});
		
		FileInputStream doubleButtonImage;
		try {
			doubleButtonImage = new FileInputStream("double-turn-icon.png");
			Image doubleImage = new Image(doubleButtonImage); 
	        ImageView doubleMoveImageView = new ImageView(doubleImage);
	        doubleMoveActionButton.setGraphic(doubleMoveImageView);
	        doubleMoveActionButton.setStyle("-fx-background-color: Black");
	        doubleMoveActionButton.setTooltip(new Tooltip("Press this to play a Double Move Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text doubleMoveLabel = new Text("Double Move");
		doubleMoveLabel.setFont(Font.font ("Verdana", 20));
		doubleMoveLabel.setFill(Color.WHITE);
		
		doublemovePane.getChildren().add(doubleMoveLabel);
		doublemovePane.getChildren().add(doubleMoveActionButton);
		doublemovePane.getChildren().add(returnDoubleMove());

		doubleMoveActionAmount.setFont(Font.font ("Verdana", 20));
		doubleMoveActionAmount.setFill(Color.WHITE);
		
		doubleMoveActionButton.setOnAction(e -> {
			Player p = this.board.getCurrentPlayer();
			if (p.isWaiting()) {
				p.setChosenActionTile(new ActionTile(new DoubleMoveAction()));
				synchronized (p) {
					p.notify();
				}
				p.getInventory().remove(new DoubleMoveAction());
				returnDoubleMove();
				System.out.println("Double move action selected");
			}
		});

		FileInputStream skipButtonImage;
		try {
			skipButtonImage = new FileInputStream("skip.png");
			Image skipImage = new Image(skipButtonImage); 
	        ImageView skipButtonImageView = new ImageView(skipImage);
	        skipButton.setGraphic(skipButtonImageView);
	        skipButton.setStyle("-fx-background-color: Black");
	        skipButton.setTooltip(new Tooltip("Press this if you don't want to play an Action Tile!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text skipButtonLabel = new Text("Skip");
		skipButtonLabel.setFont(Font.font ("Verdana", 20));
		skipButtonLabel.setFill(Color.WHITE);
		
		skipPane.getChildren().add(skipButtonLabel);
		skipPane.getChildren().add(skipButton);

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
		
		playerInventoryPane.getChildren().addAll(backtrackPane, firePane, icePane, doublemovePane, skipPane);
		playerInventoryPane.setPadding(new Insets(20));
		playerInventoryPane.setSpacing(10);
		
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
		
		FlowPane optionPane = new FlowPane();
		optionPane.setAlignment(javafx.geometry.Pos.CENTER);
		
		Button saveExitButton = new Button("Save & Exit");
		Button endTurnButton = new Button("End Turn");
		
		optionPane.setPadding(new Insets(30));
		optionPane.getChildren().addAll(saveExitButton, endTurnButton);
		
		this.setBottom(optionPane);
	}
	
	private void refreshBoard() {
		inventoryRefresh();
		this.gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.board.draw();
	}
	
	public void inventoryRefresh() {
		Player p = board.getCurrentPlayer();
		Inventory inv = p.getInventory();
		backtrackInventoryAmount = inv.getNum(new BacktrackAction());
		fireInventoryAmount = inv.getNum(new FireAction());
		iceInventoryAmount = inv.getNum(new IceAction());
		doubleMoveInventoryAmount = inv.getNum(new DoubleMoveAction());
		
		if (backtrackInventoryAmount < 1) {
			backTrackActionButton.setDisable(true);
			backtrackActionAmount.setText("x" + backtrackInventoryAmount);
		} else {
			backTrackActionButton.setDisable(false);
			backtrackActionAmount.setText("x" + backtrackInventoryAmount);
		}
		
		if (fireInventoryAmount < 1) {
			fireActionButton.setDisable(true);
			fireActionAmount.setText("x" + fireInventoryAmount);
		} else {
			fireActionButton.setDisable(false);
			fireActionAmount.setText("x" + fireInventoryAmount);
		}
		
		if (iceInventoryAmount < 1) {
			iceActionButton.setDisable(true);
			iceActionAmount.setText("x" + iceInventoryAmount);
		} else {
			iceActionButton.setDisable(false);
			iceActionAmount.setText("x" + iceInventoryAmount);
		}
		
		if (doubleMoveInventoryAmount < 1) {
			doubleMoveActionButton.setDisable(true);
			doubleMoveActionAmount.setText("x" + doubleMoveInventoryAmount);
		} else {
			doubleMoveActionButton.setDisable(false);
			doubleMoveActionAmount.setText("x" + doubleMoveInventoryAmount);
		}
	}
	
	public Text returnBacktrack() {
		inventoryRefresh();
		return this.backtrackActionAmount;
	}
	
	public Text returnFire() {
		inventoryRefresh();
		return this.fireActionAmount;
	}
	
	public Text returnIce() {
		inventoryRefresh();
		return this.iceActionAmount;
	}
	
	public Text returnDoubleMove() {
		inventoryRefresh();
		return this.doubleMoveActionAmount;
	}
	
}
