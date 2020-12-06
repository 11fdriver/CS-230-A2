package group_20;

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
	private final int TILE_WIDTH;
	
	/**
	 * location of player
	 */
	private Location location;
	
	/**
	 * Inventory of player
	 */
	private ArrayList<ActionTile> inventory;
	
	/**
	 * Keeps track of the number of moves a player can make
	 */
	private int numMoves;
	
	/**
	 * Reference to the current game board
	 */
	private Board board;
	
	/**
	 * Reference to the current silk bag
	 */
	private SilkBag silkBag;
	
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
	 * (Used by board to check state of player).
	 * Keeps track of turn state -> Stage 1, Stage 2, Stage 3.
	 * Stage 1: Drawing a tile & (possibly) inserting tile.
	 * Stage 2: Playing action tile.
	 * Stage 3: Moving.
	 */
	private int currentStageOfTurn;
	
	/**
	 * Full Constructor to be called when loading a player object
	 * @param board
	 * @param silkbag
	 * @param location
	 * @param inventory
	 * @param previousLocations
	 */
	public Player(Board board, SilkBag silkbag, int TILE_WIDTH, String spriteFilename, Location location, ArrayList<ActionTile> inventory, LocationList previousLocations, boolean hasBeenBacktracked) {
		this.board = board;
		this.silkBag = silkbag;
		this.TILE_WIDTH = TILE_WIDTH;
		this.location = location;
		this.inventory = inventory;
		this.previousLocations = previousLocations;
		this.hasBeenBacktracked = hasBeenBacktracked;
		this.numMoves = 1;
		this.loadSprite(spriteFilename);
	}
	
	/**
	 * Partial constructor for creating a new player object
	 * @param board
	 * @param silkBag
	 * @param startingLocation
	 */
	public Player(Board board, SilkBag silkBag, int TILE_WIDTH, String spriteFilename, Location startingLocation) {
		this.board = board;
		this.silkBag = silkBag;
		this.TILE_WIDTH = TILE_WIDTH;
		this.location = startingLocation;
		this.inventory = new ArrayList<ActionTile>();
		this.previousLocations = new LocationList();
		this.hasBeenBacktracked = false;
		this.numMoves = 1;
		this.loadSprite(spriteFilename);
	}
	
	public void takeTurn() {
		this.currentStageOfTurn = 1;
		this.numMoves = 1;
		System.out.println("Drawing a tile");
		this.stepOne();
		System.out.println("Doing action on tile");
		this.stepTwo(); //Can't play action tiles until merge with Finn
		this.currentStageOfTurn = 3;
		while (this.numMoves > 0) {
			this.decNumMoves(1);
			System.out.println("Making a move");
			this.stepThree();
		}
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
			ActionTile chosenTile = new ActionTile();//TODO: Change to user input
			chosenTile.play(this, this.board); //Error but will be fixed when code merge
		}
	}

	/**
	 * Allows the player to move (ie change their location)
	 * - Handles user input for selecting a direction to move in
	 * - Allows 2 moves if doubleMove is true
	 */
	public synchronized void stepThree() {
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
	
	public boolean isInRange(Location l) {
		return this.getDirectionFromPlayer(l) != null;
	}
	
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
		Tile drawnTile = silkBag.drawTile();
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
	
	public synchronized void insertTile() {
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
	public ArrayList<ActionTile> getInventory() {
		return this.inventory;
	}
	
	/**
	 * Setter for inventory
	 * @param inventory New inventory
	 */
	public void setInventory(ArrayList<ActionTile> inventory) {
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
	 * Increases numMoves by a given amount
	 * @param incAmount Amount to increase by
	 */
	public void incNumMoves(int incAmount) {
		this.numMoves += incAmount;
	}
	
	/**
	 * Decreases numMoves by a given amount
	 * @param decAmount Amount to decrease by
	 */
	public void decNumMoves(int decAmount) {
		this.numMoves -= decAmount;
	}
	
	public void removeFromCurrentTile() {
		FloorTile currentlyOn = this.board.getTileAt(this.location);
		if (currentlyOn != null) {
			currentlyOn.setMyPlayer(null);
		}
	}
	
	public void addToCurrentTile() {
		FloorTile currentlyOn = this.board.getTileAt(this.location);
		currentlyOn.setMyPlayer(this);
	}
	
	public void draw(GraphicsContext gc) {
		int x = this.getLocation().getX()*TILE_WIDTH + (TILE_WIDTH/4);
		int y = this.getLocation().getY()*TILE_WIDTH;
		gc.drawImage(sprite, x, y);
	}
	
	public void randomizeLocation(int boardWidth, int boardLength) {
		Random r = new Random();
		int x = r.nextInt(boardWidth - 1);
		int y = r.nextInt(boardLength - 1);
		this.setLocation(new Location(x,y));
	}
	
	public String inventoryToString() {
		String str = "[";
		if (this.inventory != null) {
			for (int i = 0; i < this.inventory.size() -1; i++) {
				str += "ActionTile,";
				//ActionTile temp = this.inventory.get(i);
			}
			str += "ActionTile";
		}
		str += "]";
		return str;
	}
	
	public String toString() {
		return "Location: " + this.location.toString() + "\n"
				+ "Previous Locations: " + this.previousLocations.toString() + "\n"
				+ "Inventory: " + this.inventoryToString();
	}
	
	public void loadSprite(String filename) {
		Image image = null;
		try {
			image = new Image(new FileInputStream(filename),(this.TILE_WIDTH/3)*2, (this.TILE_WIDTH/3)*2,true,true);
		} catch (IOException e) {
			System.out.println("Unable to find sprite file: " + filename);
		}
		this.sprite = image;
	}
	
	public int getCurrentStageOfTurn() {
		return this.currentStageOfTurn;
	}
	
	public void highlight(GraphicsContext gc) {
		gc.setStroke(Color.MAGENTA);
		int x = this.getLocation().getX()*TILE_WIDTH;
		int y = this.getLocation().getY()*TILE_WIDTH;
		gc.strokeOval(x, y, (TILE_WIDTH), (TILE_WIDTH));
		gc.setStroke(Color.BLACK);
	}
	
	/**
	 * Increase amount of moves a Player can make during their next turn
	 * @param n
	 */
	public void addMoves(int n) {
		numMoves += n;
	}
}
