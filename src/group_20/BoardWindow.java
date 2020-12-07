package group_20;

import java.util.ArrayList;
import java.io.File;
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

/**
 * <p><b>Filename:</b> BoardWindow</p>
 * <p><b>Description:</b> Loads the GUI for a particular game session.</p>
 * <p><b>Creation date:</b> 05/12/2020</p>
 * @since 07/12/2020
 * @author Inderpreet Sandhu - 852298
 * <p><b>Copyright:</b> no copyright</p>
 */

public class BoardWindow extends BorderPane {
	
	/**
	 * File separator for determining file paths of various images and audio
	 */
	private static final String SEP = File.separator;
	
	/**
	 * File path for all of the game assets
	 */
	private static final String CONFIG_DIR_PATH = ".lairofdagon" + SEP;
	
	/**
	 * File path for all of the game image assets
	 */
	private static final String IMG_DIR_PATH = CONFIG_DIR_PATH + "img" + SEP;
	
	/**
	 * File path for all of the game audio assets
	 */
	private static final String TITLE_MUSIC_DIR_PATH = CONFIG_DIR_PATH + "audio" + SEP;
	
	/**
	 * Width of the canvas the GUI uses
	 */
	private final int CANVAS_WIDTH;
	
	/**
	 * Height of the canvas the GUI uses
	 */
	private final int CANVAS_HEIGHT;
	
	//private final int VBOX_WIDTH = 180;
	
	private Board board;
	private Canvas gameCanvas;
	private Canvas drawnFloorTileCanvas;
	private GraphicsContext gc;
	//==
	
	/**
	 * Button representing the backtrack action
	 */
	private Button backTrackActionButton = new Button();
	
	/**
	 * Button representing the fire action
	 */
	private Button fireActionButton = new Button();
	
	/**
	 * Button representing the ice action
	 */
	private Button iceActionButton = new Button();
	
	/**
	 * Button representing the double move action
	 */
	private Button doubleMoveActionButton = new Button();
	
	/**
	 * Button representing the skip action where no action tile is played
	 */
	private Button skipButton = new Button();
	
	/**
	 * Text representing the amount of backtrack action tiles a player has in their inventory
	 */
	private Text backtrackActionAmount = new Text();
	
	/**
	 * Text representing the amount of fire action tiles a player has in their inventory
	 */
	private Text fireActionAmount = new Text();
	
	/**
	 * Text representing the amount of ice action tiles a player has in their inventory
	 */
	private Text iceActionAmount = new Text();
	
	/**
	 * Text representing the amount of double move action tiles a player has in their inventory
	 */
	private Text doubleMoveActionAmount = new Text();
	
	/**
	 * Holds details about a players action tile inventory
	 */
	private VBox playerInventoryPane = new VBox();
	
	/**
	 * Holds details about the backtrack action tiles in a players inventory
	 */
	private VBox backtrackPane = new VBox();
	
	/**
	 * Holds details about the fire action tiles in a players inventory
	 */
	private VBox firePane = new VBox();
	
	/**
	 * Holds details about the ice action tiles in a players inventory
	 */
	private VBox icePane = new VBox();
	
	/**
	 * Holds details about the double move action tiles in a players inventory
	 */
	private VBox doublemovePane = new VBox();
	
	/**
	 * Holds details about the skip action
	 */
	private VBox skipPane = new VBox();
	
	/**
	 * Stores the amount of backtrack action tiles a player has in their inventory
	 */
	private int backtrackInventoryAmount = 0;
	
	/**
	 * Stores the amount of fire action tiles a player has in their inventory
	 */
	private int fireInventoryAmount = 0;
	
	/**
	 * Stores the amount of ice action tiles a player has in their inventory
	 */
	private int iceInventoryAmount = 0;
	
	/**
	 * Stores the amount of double move action tiles a player has in their inventory
	 */
	private int doubleMoveInventoryAmount = 0;
	
	//== YOSHAN
//	private Button backTrackActionButton = new Button("Backtrack Action");
//	private Button fireActionButton = new Button("Fire Action");
//	private Button iceActionButton = new Button("Ice Action");
//	private Button doubleMoveActionButton = new Button("Double Move Action");
//	private Button skipButton = new Button("Skip");
	
	/**
	 * Displays information on which players turn it currently is and guidance on what they should do next
	 */
	private Text banner = new Text("");
	
	/**
	 * Creates a window for a game session to be displayed on
	 * 
	 * @param board				The game board that will be displayed in the window				
	 */
	public BoardWindow(Board board) {
		this.board = board;
		CANVAS_WIDTH = this.board.getWidth() * Main.TILE_WIDTH;
		CANVAS_HEIGHT = this.board.getLength() * Main.TILE_WIDTH;
		this.gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		drawnFloorTileCanvas = new Canvas(Main.TILE_WIDTH, Main.TILE_WIDTH);
		this.gc = this.gameCanvas.getGraphicsContext2D();
		this.board.setGraphicsContext(this.gc);
		
		this.setRight(drawnFloorTileCanvas);
		this.setCenter(this.gameCanvas);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> refreshBoard()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		this.gameCanvas.setOnMouseClicked(e -> {
			this.board.setLastClickLocation(e.getX(), e.getY());
			synchronized (this.board) {
				board.notify();
			}
			System.out.println("You clicked tile: " + this.board.getlastClickLocation());
			refreshBoard();
		});
		
		this.createHeader();
		this.createFooter();
		this.createInventoryPane();
		startGame();
		refreshBoard();
	}
	
	/**
	 * Refreshes the board state
	 */
	private void refreshBoard() {
		//System.out.println("Refreshed");
		banner.setText(this.board.getCurrentStepMessage());
		Player p = this.board.getCurrentPlayer();
		if (p.getTileToInsert() != null) {
			p.getTileToInsert().draw(drawnFloorTileCanvas.getGraphicsContext2D(), 0, 0);
		} else {
			drawnFloorTileCanvas.getGraphicsContext2D().clearRect(0, 0, Main.TILE_WIDTH, Main.TILE_WIDTH);
		}
		this.gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		this.board.draw();
		this.inventoryRefresh();
	}
	
	/**
	 * Refreshes a players inventory
	 */
	public void inventoryRefresh() {
		Player p = this.board.getCurrentPlayer();
		Inventory inv = p.getInventory();
		backtrackInventoryAmount = inv.getNum(new BacktrackAction());
		fireInventoryAmount = inv.getNum(new FireAction());
		iceInventoryAmount = inv.getNum(new IceAction());
		doubleMoveInventoryAmount = inv.getNum(new DoubleMoveAction());
		
		if (backtrackInventoryAmount < 1) {
			backTrackActionButton.setDisable(true);
			backtrackActionAmount.setText("x" + 0);
		} else {
			backTrackActionButton.setDisable(false);
			backtrackActionAmount.setText("x" + backtrackInventoryAmount);
		}
		
		if (fireInventoryAmount < 1) {
			fireActionButton.setDisable(true);
			fireActionAmount.setText("x" + 0);
		} else {
			fireActionButton.setDisable(false);
			fireActionAmount.setText("x" + fireInventoryAmount);
		}
		
		if (iceInventoryAmount < 1) {
			iceActionButton.setDisable(true);
			iceActionAmount.setText("x" + 0);
		} else {
			iceActionButton.setDisable(false);
			iceActionAmount.setText("x" + iceInventoryAmount);
		}
		
		if (doubleMoveInventoryAmount < 1) {
			doubleMoveActionButton.setDisable(true);
			doubleMoveActionAmount.setText("x" + 0);
		} else {
			doubleMoveActionButton.setDisable(false);
			doubleMoveActionAmount.setText("x" + doubleMoveInventoryAmount);
		}
	}
	
	/**
	 * Starts the game session for the current board
	 */
	private void startGame() {
		Thread tr = new Thread(board);
		tr.setDaemon(true);
		tr.start();
	}
	
	/**
	 * Creates the header for the game session window containing the banner and its associated information (player turns and instructions on how to proceed) 
	 */
	private void createHeader() {
		FlowPane fpBanner = new FlowPane();
		fpBanner.setAlignment(javafx.geometry.Pos.CENTER);
		fpBanner.getChildren().add(banner);
		this.setTop(fpBanner);
	}
	
	/**
	 * Creates the footer for the game session window containing buttons for ending a turn and saving and exiting the current game session
	 */
	private void createFooter() {
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
	
	/**
	 * Creates the players action tile inventory pane and displays it on the game session screen
	 */
	private void createInventoryPane() {
		FileInputStream backgroundImg;
		try {
			backgroundImg = new FileInputStream(IMG_DIR_PATH + "title_screen_animation_no_text.gif");
			Image gameBackground = new Image(backgroundImg);
			BackgroundImage gameSessionImage = new BackgroundImage(gameBackground, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null); 
			this.setBackground(new Background(gameSessionImage));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} 
		
		this.setLeft(playerInventoryPane);
		
		FileInputStream backtrackButtonImage;
		try {
			backtrackButtonImage = new FileInputStream(IMG_DIR_PATH + "backtrack-icon.png");
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
			fireButtonImage = new FileInputStream(IMG_DIR_PATH + "cast-fire-button.png");
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
			iceButtonImage = new FileInputStream(IMG_DIR_PATH + "cast-ice-button.png");
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
			doubleButtonImage = new FileInputStream(IMG_DIR_PATH + "double-turn-icon.png");
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
			skipButtonImage = new FileInputStream(IMG_DIR_PATH + "skip.png");
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
	}
	
	/**
	 * Returns the amount of backtrack action tiles a player has in their inventory in text form
	 * 
	 * @return 				The amount of backtrack action tiles a player has in their inventory
	 */
	public Text returnBacktrack() {
		inventoryRefresh();
		return this.backtrackActionAmount;
	}
	
	/**
	 * Returns the amount of fire action tiles a player has in their inventory in text form
	 * 
	 * @return 				The amount of fire action tiles a player has in their inventory
	 */
	public Text returnFire() {
		inventoryRefresh();
		return this.fireActionAmount;
	}
	
	/**
	 * Returns the amount of ice action tiles a player has in their inventory in text form
	 * 
	 * @return 				The amount of ice action tiles a player has in their inventory
	 */
	public Text returnIce() {
		inventoryRefresh();
		return this.iceActionAmount;
	}
	
	/**
	 * Returns the amount of double move action tiles a player has in their inventory in text form
	 * 
	 * @return 				The amount of double move action tiles a player has in their inventory
	 */
	public Text returnDoubleMove() {
		inventoryRefresh();
		return this.doubleMoveActionAmount;
	}
	
//	private void createInventoryPane() {
//		VBox sidebar = new VBox();
//		this.setLeft(sidebar);
//		
//		sidebar.setPadding(new Insets(40));
//		sidebar.setSpacing(40);
//		
//		//Backtrack
//		sidebar.getChildren().addAll(backTrackActionButton);
//		
//		backTrackActionButton.setOnAction(e -> {
//			Player p = this.board.getCurrentPlayer();
//			if (p.isWaiting()) {
//				p.setChosenActionTile(new ActionTile(new BacktrackAction()));
//				synchronized (p) {
//					p.notify();
//				}
//				System.out.println("Backtrack action selected");
//			}
//		});
//		
//		//Fire action
//		sidebar.getChildren().addAll(fireActionButton);
//		
//		fireActionButton.setOnAction(e -> {
//			Player p = this.board.getCurrentPlayer();
//			if (p.isWaiting()) {
//				p.setChosenActionTile(new ActionTile(new FireAction()));
//				synchronized (p) {
//					p.notify();
//				}
//				System.out.println("Fire action selected");
//			}
//		});
//		
//		//Ice action
//		sidebar.getChildren().addAll(iceActionButton);
//		
//		iceActionButton.setOnAction(e -> {
//			Player p = this.board.getCurrentPlayer();
//			if (p.isWaiting()) {
//				p.setChosenActionTile(new ActionTile(new IceAction()));
//				synchronized (p) {
//					p.notify();
//				}
//				System.out.println("Ice action selected");
//			}
//		});
//		
//		//Double move
//		sidebar.getChildren().addAll(doubleMoveActionButton);
//		
//		doubleMoveActionButton.setOnAction(e -> {
//			Player p = this.board.getCurrentPlayer();
//			if (p.isWaiting()) {
//				p.setChosenActionTile(new ActionTile(new DoubleMoveAction()));
//				synchronized (p) {
//					p.notify();
//				}
//				System.out.println("Double move action selected");
//			}
//		});
//		
//		//Skip button
//		sidebar.getChildren().addAll(skipButton);
//		
//		skipButton.setOnAction(e -> {
//			Player p = this.board.getCurrentPlayer();
//			if (p.isWaiting()) {
//				System.out.println("Is the tile null? " + ((new ActionTile(null)) == null));
//				p.setChosenActionTile(new ActionTile(null));
//				synchronized (p) {
//					p.notify();
//				}
//				System.out.println("Skipped playing action tile");
//			}
//		});
//	}
}
