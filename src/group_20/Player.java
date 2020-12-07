package group_20;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player {
	/**
	 * 
	 */
	private static final String SEP = File.separator;
	
	/**
	 * 
	 */
	private static final String CONFIG_DIR_PATH = ".lairofdagon" + SEP;
	
	/**
	 * 
	 */
	private static final String TILE_IMG_DIR_PATH = CONFIG_DIR_PATH + "img" + SEP;
	
	/**
	 * 
	 */
	private static final String HIGHLIGHT_IMG_FILEPATH = TILE_IMG_DIR_PATH + "Magnifying_glass.png";//TODO change to actual file name
	
	/**
	 * location of player
	 */
	private Location location;
	
	/**
	 * Inventory of player
	 */
	private Inventory inventory;
	
	/**
	 * Keeps track of the number of moves a player can make
	 */
	private int numMoves;
	
	/**
	 * Reference to the current game board
	 */
	private Board board;
	
	/**
	 * List of up to last 3 previous locations
	 */
	private LocationList previousLocations;
	
	/**
	 * True if player has been backtracked
	 * Players can't be backtracked twice per game
	 */
	private Boolean hasBeenBacktracked;
	
	/**
	 * The current floor tile that the player has drawn on their turn
	 */
	private FloorTile tileToInsert;
	
	/**
	 * Sprite for player
	 */
	private Image sprite;
	
	/**
	 * Sprite used to highlight player on board
	 */
	private Image highlightSprite;
	
	/**
	 * (Used by board to check state of player).
	 * Keeps track of turn state -> Stage 1, Stage 2, Stage 3.
	 * Stage 1: Drawing a tile & (possibly) inserting tile.
	 * Stage 2: Playing action tile.
	 * Stage 3: Moving.
	 */
	private int currentStageOfTurn;
	
	/**
	 * The current action tile the player has chosen to play
	 */
	private ActionTile chosenActionTile = null;
	
	/**
	 * Stores if the player it waiting for input from the GUI
	 */
	private boolean isWaiting = false;
	
	/**
	 * The profile linked to this object for storing stats
	 */
	private Profile profile;
	
	/**
	 * Player identifier from 1 to 4 (inclusive)
	 */
	private int playerNumber;
	
	/**
	 * Full Constructor to be called when loading a player object
	 * @param board
	 * @param silkbag
	 * @param location
	 * @param inventory
	 * @param previousLocations
	 */
	public Player(int playerNumber, Location location, Inventory inventory, LocationList previousLocations, boolean hasBeenBacktracked, Profile profile) {
		this.playerNumber = playerNumber;
		this.location = location;
		this.inventory = inventory;
		this.previousLocations = previousLocations;
		this.hasBeenBacktracked = hasBeenBacktracked;
		this.profile = profile;
		this.numMoves = 1;
		this.loadSprite();
		this.loadHighlightSprite();
	}
	
	/**
	 * Partial constructor for creating a new player object
	 * @param board
	 * @param silkBag
	 * @param startingLocation
	 */
	public Player(int playerNumber, Location startingLocation, Profile profile) {
		this.playerNumber = playerNumber;
		this.location = startingLocation;
		this.inventory = new Inventory();
		this.previousLocations = new LocationList();
		this.hasBeenBacktracked = false;
		this.profile = profile;
		this.numMoves = 1;
		this.loadSprite();
		this.loadHighlightSprite();
	}
	
	public void takeTurn() {
		this.currentStageOfTurn = 1;
		this.numMoves = 1;
		System.out.println("Drawing a tile");
		this.stepOne();
		this.currentStageOfTurn = 2;
		//System.out.println("Doing action on tile");
		this.stepTwo();
		this.currentStageOfTurn = 3;
		while (this.numMoves > 0) {
			this.decNumMoves(1);
			System.out.println("Making a move");
			this.stepThree();
		}
		this.currentStageOfTurn = 4;
	}
	
	/**
	 * Allows the player to draw a tile
	 * 	- Action Tile = Add to inventory
	 * 	- Floor Tile = Insert onto board (Also get input from user as to where to insert)
	 */
	public void stepOne() {
		this.drawTile();
	}
	
	/**
	 * Allows the player to play an ActionTile
	 *  - Handles user input for selecting a tile from inventory
	 */
	public void stepTwo() {
		//Skip step 2 if inventory is empty
		if (!this.inventory.isEmpty()) {
			//Allow the user to select a tile
			this.selectTileFromInventory();
			if (!(chosenActionTile.getAction() == null)) {
				System.out.println("'Playing' " + chosenActionTile.toString());
			} else {
				System.out.println("Oh I guess you wanted to skip your turn.. fine by me");
			}
			//chosenActionTile.play(this, this.board);
		} else {
			System.out.println("My inventory is empty -> Can't play an action tile :(");
		}
	}

	/**
	 * Allows the player to move (ie change their location)
	 * - Handles user input for selecting a direction to move in
	 * - Allows 2 moves if doubleMove is true
	 */
	public void stepThree() {
		//Skip method if player can't make any valid moves
		if (this.canMove()) {
			Location clickLocation = this.board.getLocationAtClick();
			
			while (!this.canMove(clickLocation)) {
				clickLocation = this.board.getLocationAtClick();;
			}
			
			Direction d = this.getDirectionFromPlayer(clickLocation);
			this.move(d);
		}
	}
	
	/**
	 * Checks if a location is 1 move away from the player
	 * @param l Location to check
	 * @return True if location is 1 tile away from player
	 */
	public boolean isInRange(Location l) {
		return this.getDirectionFromPlayer(l) != null;
	}
	
	/**
	 * Gets the direction of a location from the player's location
	 * @param l Location to check
	 * @return Direction of location from player
	 */
	public Direction getDirectionFromPlayer(Location l) {
		Direction directionToMove = null;
		if (l.getY() == this.getLocation().getY()+1 && l.getX() == this.getLocation().getX()) {
			directionToMove = Direction.SOUTH;
		} else if (l.getY() == this.getLocation().getY()-1 && l.getX() == this.getLocation().getX()) {
			directionToMove = Direction.NORTH;
		} else if (l.getX() == this.getLocation().getX()+1 && l.getY() == this.getLocation().getY()) {
			directionToMove = Direction.EAST;
		} else if (l.getX() == this.getLocation().getX()-1 && l.getY() == this.getLocation().getY()) {
			directionToMove = Direction.WEST;
		}
		return directionToMove;
	}
	
	/**
	 * Checks if player can move to a location
	 * @param l location to move to
	 * @return True if player can move to the given location
	 */
	public boolean canMove(Location l) {
		if (this.isInRange(l) &&
				this.canMove(this.getDirectionFromPlayer(l))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if player can move in a given direction on the board
	 * @param d Direction to move in
	 * @return True if move is valid
	 */
	public boolean canMove(Direction d) {
		return this.board.canMove(this.location, d);
	}
	
	/**
	 * Checks if player can move at all
	 * @return True if player can move in at least 1 direction
	 */
	public boolean canMove() {
		//If can move North, East, South or West
		if (this.board.canMove(this.location, Direction.NORTH) || this.board.canMove(this.location, Direction.EAST)
				|| this.board.canMove(this.location, Direction.SOUTH) || this.board.canMove(this.location, Direction.WEST)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Responsible for drawing a tile from the silk bag
	 * @return ActionTile if action tile was drawn or null if FloorTile was drawn
	 */
	public void drawTile() {
		Tile drawnTile = SilkBag.removeTile(); //TODO put this line in when merging with Finn
		//System.out.println(drawnTile.toString());
		
		//If is ActionTile
		if (ActionTile.class.isAssignableFrom(drawnTile.getClass())) {
			System.out.println("I drew an action tile");
			this.addToInventory((ActionTile) drawnTile);
			
		//If is FloorTile
		} else if (FloorTile.class.isAssignableFrom(drawnTile.getClass())) {
			System.out.println("I drew a floor tile");
			this.tileToInsert = (FloorTile) drawnTile;
			this.insertTile();
			
		//If is Tile
		} else if (Tile.class.isAssignableFrom(drawnTile.getClass())) {
			System.out.println("I draw a generic Tile? Why would you give me that?");
			
		//Anything else
		} else {
			System.out.println("What the heck did I just draw?????");
		}
	}
	
	/**
	 * Allows the player to insert the current floor tile in their hand
	 */
	public void insertTile() {
		System.out.println("Inserting the tile");
		Location insertLocation = this.board.getLocationAtClick();
		
		while (!this.board.canInsertAt(insertLocation)) {
			insertLocation = this.board.getLocationAtClick();
		}
		
		this.insertTile(insertLocation);
	}
	
	/**
	 * Inserts the tile drawn at a given location
	 * @param l Location to insert tile
	 */
	public void insertTile(Location l) {
		this.board.insertTile(this.tileToInsert,l);
		this.tileToInsert = null;
		System.out.println("Tile inserted!");
	}
	
	/**
	 * True if player has a value for tileToInsert ie drew a floor tile
	 * @return
	 */
	public boolean drewFloorTile() {
		return this.tileToInsert != null;
	}
	
	/**
	 * Returns the current tile the player has chosen to insert onto board
	 * @return Tile player is waiting to insert
	 */
	public FloorTile getTileToInsert() {
		return this.tileToInsert;
	}
	
	/**
	 * Allows the player to move in a direction
	 * @param d Direction to move
	 */
	public void move(Direction d) {
		if (this.board.canMove(this.location, d)) {
			this.addPreviousLocation(this.getLocation());
			this.removeFromCurrentTile();
			this.location.update(d);
			this.addToCurrentTile();
		}
		System.out.println("Player Moved!");
	}
	 /**
	  * Setter for board
	  * @param board New board reference
	  */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Getter for board
	 * @return Player's board pointer
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Getter for player number
	 * @return Player number
	 */
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	/**
	 * Setter for current location
	 * @param location New location
	 */
	public void setLocation(Location location) {
		this.addPreviousLocation(this.getLocation());
		this.removeFromCurrentTile();
		this.location = location;
		this.addToCurrentTile();
	}
	
	/**
	 * Getter for current location
	 * @return current location
	 */
	public Location getLocation() {
		return this.location.copy();
	}
	
	/**
	 * Getter for player's inventory of ActionTile's
	 * @return
	 */
	public Inventory getInventory() {
		return this.inventory;
	}
	
	/**
	 * Setter for inventory
	 * @param inventory New inventory
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Adds a tile to the player's inventory
	 * @param t Tile to add
	 */
	public void addToInventory(ActionTile t) {
		this.inventory.add(t);
	}
	
	/**
	 * Removes an action tile from the player's inventory
	 * @param t ActionTile to remove
	 */
	public void removeFromInventory(ActionTile t) {
		this.inventory.remove(t);
	}
	
	/**
	 * For setting entire list of previous locations
	 * @param previousLocations New list of previous locations
	 */
	public void setPreviousLocations(LocationList previousLocations) {
		this.previousLocations = previousLocations;
	}
	
	/**
	 * Adds a location to the player's list of previous locations
	 * @param l Location to add
	 */
	public void addPreviousLocation(Location l) {
		this.previousLocations.add(l);
	}
	
	/**
	 * Getter for previous locations
	 * @return list of previous locations
	 */
	public LocationList getPreviousLocations() {
		return this.previousLocations;
	}
	
	/**
	 * Setter for hasBeenBacktracked
	 * @param hasBeenBacktracked New value
	 */
	public void setHasBeenBacktracked(boolean hasBeenBacktracked) {
		this.hasBeenBacktracked = hasBeenBacktracked;
	}
	
	/**
	 * Getter for hasBeenBackTracked
	 * @return True if player has not been backtracked this game, else false
	 */
	public boolean getHasBeenBacktracked() {
		return this.hasBeenBacktracked;
	}
	
	/**
	 * Setter for profile
	 * @param profile New reference for profile
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	/**
	 * Getter for profile
	 * @return Player's profile
	 */
	public Profile getProfile() {
		return this.profile;
	}
	
	/**
	 * Setter for numMoves
	 * @param numMoves New value for numMoves
	 */
	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}
	
	/**
	 * Getter for numMoves
	 * @return numMoves
	 */
	public int getNumMoves() {
		return this.numMoves;
	}
	
	/**
	 * Increase amount of moves a Player can make during their next turn
	 * @param n
	 */
	public void addMoves(int n) {
		numMoves += n;
	}
	
	/**
	 * Decreases numMoves by a given amount
	 * @param decAmount Amount to decrease by
	 */
	public void decNumMoves(int decAmount) {
		this.numMoves -= decAmount;
	}
	
	/**
	 * Removes the pointer to this player from the tile on the board at the location of this player
	 */
	public void removeFromCurrentTile() {
		FloorTile currentlyOn = this.board.getTileAt(this.location);
		if (currentlyOn != null) {
			currentlyOn.setPlayer(null);
		}
	}
	
	/**
	 * Adds a pointer to this player to the tile at this player's location on the board
	 */
	public void addToCurrentTile() {
		FloorTile currentlyOn = this.board.getTileAt(this.location);
		currentlyOn.setPlayer(this);
	}
	
	/**
	 * Draws the player on a given graphics context
	 * @param gc Graphics Context to draw onto
	 */
	public void draw(GraphicsContext gc) {
		int x = this.getLocation().getX()*Main.TILE_WIDTH + (Main.TILE_WIDTH/4);
		int y = this.getLocation().getY()*Main.TILE_WIDTH;
		gc.drawImage(sprite, x, y);
	}
	
	/**
	 * Sets the player to a random location on the board
	 * @param boardWidth Width of board
	 * @param boardLength Length of board
	 */
	public void randomizeLocation(int boardWidth, int boardLength) {
		Random r = new Random();
		int x = r.nextInt(boardWidth - 1);
		int y = r.nextInt(boardLength - 1);
		this.setLocation(new Location(x,y));
	}
	
	/**
	 * Converts the player object to a readable string
	 * @return Player object as a readable string
	 */
	public String toString() {
		return "Location: " + this.location.toString() + "\n"
				+ "Previous Locations: " + this.previousLocations.toString() + "\n"
				+ "Inventory: " + this.inventory.toString();
	}
	
	/**
	 * Loads player's sprite from given file location
	 * @param filename File name of sprite
	 */
	public void loadSprite() {
		Image image = null;
		String filename = TILE_IMG_DIR_PATH;
		switch (this.playerNumber) {
		case 1:
			filename += "Howard-no-background.png";
			break;
		case 2:
			filename += "Dagon-no-background.png";
			break;
		case 3:
			filename += "Nightgaunt-no-background.png";
			break;
		case 4:
			filename += "Shelley-no-background.png";
			break;
		default:
			filename += "Howard-no-background.png";
			break;
		}
		
		try {
			image = new Image(new FileInputStream(filename),(Main.TILE_WIDTH/3)*2, (Main.TILE_WIDTH/3)*2,true,true);
		} catch (IOException e) {
			System.out.println("Unable to find sprite file: " + filename);
		}
		this.sprite = image;
	}
	
	/**
	 * Loads characters highlighting sprite
	 */
	public void loadHighlightSprite() {
  		Image image = null;
		try {
			image = new Image(new FileInputStream(HIGHLIGHT_IMG_FILEPATH),Main.TILE_WIDTH, Main.TILE_WIDTH,true,true);
		} catch (IOException e) {
			System.out.println("Unable to load highlight sprite");
			//TODO add code
		}
		this.highlightSprite = image;
  	}
	
	/**
	 * Getter for currentStageOfTurn
	 * @return Current stage of player's turn
	 */
	public int getCurrentStageOfTurn() {
		return this.currentStageOfTurn;
	}
	
	/**
	 * Highlights player on the given graphics context
	 * @param gc Graphics context to draw highlight on
	 */
	public void highlight(GraphicsContext gc) {
		FloorTile tileStandingOn = this.board.getTileAt(this.getLocation());
		tileStandingOn.highlight(gc, this.highlightSprite);
	}
	
	/**
	 * Sets the chosen action tile for player to use
	 * @param t Action tile for player to use
	 */
	public void setChosenActionTile(ActionTile t) {
		this.chosenActionTile = t;
		System.out.println("Removing action tile");
		this.inventory.remove(t);
		System.out.println("Removed action tile");
	}
	
	/**
	 * Checks of the player is waiting for input from GUI
	 * @return True if player is waiting for input from GUI
	 */
	public boolean isWaiting() {
		return this.isWaiting;
	}
	
	/**
	 * Sets the player object to wait for input from the GUI
	 * - Sets the value of chosenActionTile to corresponding input from GUI
	 */
	public void selectTileFromInventory() {
		synchronized (this) {
			this.setChosenActionTile(null);
			while (this.chosenActionTile == null) {
				this.isWaiting = true;
				System.out.println("Select An Action Tile");
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Action Tile Selected");
			this.isWaiting = false;
		}
	}
	
	 /**
	  * Converts player objects to string in correct format for saving
	  * @return Player as formatted string
	  */
	public String saveFormat() {
		//(Board board, int playerNumber, Location location, Inventory inventory, LocationList previousLocations, boolean hasBeenBacktracked, Profile profile)
		String str = "{Player," +
				this.playerNumber + "," +
				this.location.toString() + "," +
				this.inventory.saveFormat() + "," +
				this.previousLocations.toString() + "," +
				this.hasBeenBacktracked + ",";
		if (this.profile == null) {
			str += "null,";
		} else {
			str += this.profile.getID() + ",";
		}
		str += "Player}";
		return str;
	}
	
	//Just for testing
	public static void main(String[] args) {
		Inventory inv = new Inventory();
		LocationList locList = new LocationList();
		Player p = new Player(1,new Location(1,7), inv, locList, false, null);
		System.out.println(p.saveFormat());
		inv.add(new ActionTile(new FireAction()));
		inv.add(new ActionTile(new FireAction()));
		inv.add(new ActionTile(new IceAction()));
		locList.add(new Location(1,7));
		locList.add(new Location(1,3));
		locList.add(new Location(2,7));
		System.out.println(p.saveFormat());
	}
}
