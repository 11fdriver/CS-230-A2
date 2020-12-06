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
	private Text t5 = new Text();
	private Text t6 = new Text();
	private Text t7 = new Text();
	private Text t8 = new Text();
	private VBox sidebar = new VBox();
	private VBox backtrackPane = new VBox();
	private VBox firePane = new VBox();
	private VBox icePane = new VBox();
	private VBox doublemovePane = new VBox();
	private VBox skipPane = new VBox();
	private VBox nextTile = new VBox();
	private int backtrackAmount = 0;
	private int fireAmount = 0;
	private int iceAmount = 0;
	private int doublemoveAmount = 0;
	
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
			BackgroundImage menuImage = new BackgroundImage(gameBackground, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
			this.setBackground(new Background(menuImage));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} 
		this.setLeft(sidebar);
		
		FileInputStream backtrackButtonImage;
		try {
			backtrackButtonImage = new FileInputStream("backtrack-icon.png");
			Image backtrackImage = new Image(backtrackButtonImage); 
	        ImageView imageView = new ImageView(backtrackImage);
	        backTrackActionButton.setGraphic(imageView);
	        backTrackActionButton.setStyle("-fx-background-color: Black");
	        backTrackActionButton.setTooltip(new Tooltip("Press this to play a Backtrack Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text t = new Text("Backtrack");
		backtrackPane.getChildren().addAll(t);
		backtrackPane.getChildren().addAll(backTrackActionButton);
		backtrackPane.getChildren().addAll(returnBacktrack());
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.WHITE);
		t5.setFont(Font.font ("Verdana", 20));
		t5.setFill(Color.WHITE);
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
	        ImageView imageView2 = new ImageView(fireImage);
	        fireActionButton.setGraphic(imageView2);
	        fireActionButton.setStyle("-fx-background-color: Black");
	        fireActionButton.setTooltip(new Tooltip("Press this to play a Fire Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text t1 = new Text("Fire");
		firePane.getChildren().addAll(t1);
		firePane.getChildren().addAll(fireActionButton);
		firePane.getChildren().addAll(returnFire());
		t1.setFont(Font.font ("Verdana", 20));
		t1.setFill(Color.WHITE);
		t6.setFont(Font.font ("Verdana", 20));
		t6.setFill(Color.WHITE);
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
	        ImageView imageView3 = new ImageView(iceImage);
	        iceActionButton.setGraphic(imageView3);
	        iceActionButton.setStyle("-fx-background-color: Black");
	        iceActionButton.setTooltip(new Tooltip("Press this to play a Ice Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text t2 = new Text("Ice");
		icePane.getChildren().addAll(t2);
		icePane.getChildren().addAll(iceActionButton);
		icePane.getChildren().addAll(returnIce());
		t2.setFont(Font.font ("Verdana", 20));
		t2.setFill(Color.WHITE);
		t7.setFont(Font.font ("Verdana", 20));
		t7.setFill(Color.WHITE);
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
	        ImageView imageView4 = new ImageView(doubleImage);
	        doubleMoveActionButton.setGraphic(imageView4);
	        doubleMoveActionButton.setStyle("-fx-background-color: Black");
	        doubleMoveActionButton.setTooltip(new Tooltip("Press this to play a Double Move Action!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text t3 = new Text("Double Move");
		doublemovePane.getChildren().addAll(t3);
		doublemovePane.getChildren().addAll(doubleMoveActionButton);
		doublemovePane.getChildren().addAll(returnDoubleMove());
		t3.setFont(Font.font ("Verdana", 20));
		t3.setFill(Color.WHITE);
		t8.setFont(Font.font ("Verdana", 20));
		t8.setFill(Color.WHITE);
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
	        ImageView imageView6 = new ImageView(skipImage);
	        skipButton.setGraphic(imageView6);
	        skipButton.setStyle("-fx-background-color: Black");
	        skipButton.setTooltip(new Tooltip("Press this if you don't want to play an Action Tile!"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Text t4 = new Text("Skip");
		skipPane.getChildren().addAll(t4);
		skipPane.getChildren().addAll(skipButton);
		t4.setFont(Font.font ("Verdana", 20));
		t4.setFill(Color.WHITE);
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
		
		sidebar.getChildren().addAll(backtrackPane, firePane, icePane, doublemovePane, skipPane);
		sidebar.setPadding(new Insets(20));
		sidebar.setSpacing(10);
		
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
		Button b1 = new Button("Save & Exit");
		Button b2 = new Button("End Turn");
		fp.setPadding(new Insets(30));
		fp.getChildren().addAll(b1, b2);
		this.setBottom(fp);
	}
	
	private void refreshBoard() {
		inventoryRefresh();
		this.gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.board.draw();
	}
	
	public void inventoryRefresh() {
		Player p = board.getCurrentPlayer();
		Inventory inv = p.getInventory();
		backtrackAmount = inv.getNum(new BacktrackAction());
		fireAmount = inv.getNum(new FireAction());
		iceAmount = inv.getNum(new IceAction());
		doublemoveAmount = inv.getNum(new DoubleMoveAction());
		
		if (backtrackAmount < 1) {
			backTrackActionButton.setDisable(true);
			t5.setText("x" + backtrackAmount);
		} else {
			backTrackActionButton.setDisable(false);
			t5.setText("x" + backtrackAmount);
		}
		
		if (fireAmount < 1) {
			fireActionButton.setDisable(true);
			t6.setText("x" + fireAmount);
		} else {
			fireActionButton.setDisable(false);
			t6.setText("x" + fireAmount);
		}
		
		if (iceAmount < 1) {
			iceActionButton.setDisable(true);
			t7.setText("x" + iceAmount);
		} else {
			iceActionButton.setDisable(false);
			t7.setText("x" + iceAmount);
		}
		
		if (doublemoveAmount < 1) {
			doubleMoveActionButton.setDisable(true);
			t8.setText("x" + doublemoveAmount);
		} else {
			doubleMoveActionButton.setDisable(false);
			t8.setText("x" + doublemoveAmount);
		}
	}
	
	public Text returnBacktrack() {
		inventoryRefresh();
		return this.t5;
	}
	
	public Text returnFire() {
		inventoryRefresh();
		return this.t6;
	}
	
	public Text returnIce() {
		inventoryRefresh();
		return this.t7;
	}
	
	public Text returnDoubleMove() {
		inventoryRefresh();
		return this.t8;
	}
	
}
