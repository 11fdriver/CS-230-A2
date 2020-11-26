package group_20;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {
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
	
	private Image sprite;
	
	/**
	 * Full Constructor to be called when loading a player object
	 * @param board
	 * @param silkbag
	 * @param location
	 * @param inventory
	 * @param previousLocations
	 */
	public Player(Board board, SilkBag silkbag, Location location, ArrayList<ActionTile> inventory, LocationList previousLocations, boolean hasBeenBacktracked) {
		this.board = board;
		this.silkBag = silkbag;
		this.location = location;
		this.inventory = inventory;
		this.previousLocations = previousLocations;
		this.hasBeenBacktracked = hasBeenBacktracked;
		this.numMoves = 1;
		this.loadSprite();
	}
	
	/**
	 * Partial constructor for creating a new player object
	 * @param board
	 * @param silkBag
	 * @param startingLocation
	 */
	public Player(Board board, SilkBag silkBag, Location startingLocation) {
		this.board = board;
		this.silkBag = silkBag;
		this.location = startingLocation;
		this.inventory = new ArrayList<ActionTile>();
		this.previousLocations = new LocationList();
		this.hasBeenBacktracked = false;
		this.numMoves = 1;
		this.loadSprite();
	}
	
	public void takeTurn() {
		this.numMoves = 1;
		this.drawTile();
		//this.stepTwo();//Can't play action tiles until merge with Finn
		while (this.numMoves > 0) {
			this.decNumMoves(1);
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
//		FloorTile drawnTile = this.drawTile();
//		//If tile is null then it was an ActionTile and was added to inventory
//		if (drawnTile != null) {
//			Location l = new Location(0,0); //As location to insert new tile -> temp value for now
//			this.board.insertTile(drawnTile, l);
//		}
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
	public void stepThree() {
		//Skip method if player can't make any valid moves
		if (this.canMove()) {
			Direction d = null;//TODO: change to user input
			
			Random r = new Random();
			int directionNum = r.nextInt(5);
			switch (directionNum) {
			case 1:
				d = Direction.NORTH;
				break;
			case 2:
				d = Direction.EAST;
				break;
			case 3:
				d = Direction.SOUTH;
				break;
			case 4:
				d = Direction.WEST;
				break;
			default:
				d = Direction.NORTH;
			}
			
			//Loop until input is valid
			//For now just picks a random new location
			while (this.canMove() && !canMove(d)) {
				//System.out.print("Invalid move choice");
				directionNum = r.nextInt(5);
				switch (directionNum) {
				case 1:
					d = Direction.NORTH;
					break;
				case 2:
					d = Direction.EAST;
					break;
				case 3:
					d = Direction.SOUTH;
					break;
				case 4:
					d = Direction.WEST;
					break;
				default:
					d = Direction.NORTH;
				}
				
//				int directionNum = r.nextInt(4000);
//				if (directionNum < 1000) {
//					d = Direction.NORTH;
//				} else if (directionNum < 1) {
//					d = Direction.EAST;
//				} else if (directionNum < 3000) {
//					d = Direction.SOUTH;
//				} else if (directionNum < 4000) {
//					d = Direction.WEST;
//				} else {
//					d = Direction.NORTH;
//				}
				
			}
			
//			if (this.canMove(Direction.NORTH)) {
//				d = Direction.NORTH;
//			} else if (this.canMove(Direction.EAST)) {
//				d = Direction.EAST;
//			} else if (this.canMove(Direction.SOUTH)) {
//				d = Direction.SOUTH;
//			} else if (this.canMove(Direction.WEST)) {
//				d = Direction.WEST;
//			}
			
			//Allow player to move
			//System.out.print("Moved from " + this.location.toString());
			this.move(d);
			//System.out.println(" to " + this.location.toString());
		} else {
			System.out.println("No valid moves to make");
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
		//if (drawnTile.getClass() == ActionTile.class) {
		if (ActionTile.class.isAssignableFrom(drawnTile.getClass())) {
			System.out.println("I drew an action tile");
			this.addToInventory((ActionTile) drawnTile);
			
		//If is FloorTile
		//} else if (drawnTile.getClass() == FloorTile.class) {
		} else if (FloorTile.class.isAssignableFrom(drawnTile.getClass())) {
			System.out.println("I drew a floor tile");
			this.insertTile((FloorTile) drawnTile);
			//return (FloorTile) drawnTile;
			
		//If is Tile
		//} else if (drawnTile.getClass() == Tile.class) {
		} else if (Tile.class.isAssignableFrom(drawnTile.getClass())) {
			System.out.println("I draw a generic Tile? Why would you give me that?");
			
		//Anything else
		} else {
			System.out.println("What the heck did I just draw?????");
		}
	}
	
	/**
	 * Allows the player to insert a tile onto the board at a chosen location
	 * - Location chosen inside of method
	 * @param t FloorTile to insert onto board
	 */
	public void insertTile(FloorTile t) {
		//Location insertLocation = new Location(0,5);//TODO change to player input
		Location insertLocation = this.board.getRandomInsertLocation();
		this.board.insertTile(t, insertLocation);
	}
	
	/**
	 * Allows the player to move in a direction
	 * @param d Direction to move
	 */
	public void move(Direction d) {
//		Location newLocation = new Location(this.location.getX(), this.location.getY());
//		newLocation.update(d);
//		
//		//Only update location if new position is in bounds of Board
//		if (this.board.isInBounds(newLocation)) {
//			this.location = newLocation;
//		}
		//this.location.update(d);
		
		if (this.board.canMove(this.location, d)) {
			this.addPreviousLocation(this.getLocation());
			this.removeFromCurrentTile();
			this.location.update(d);
			this.addToCurrentTile();
		}
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
		return this.location;
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
	
	public void draw(GraphicsContext gc, int tileWidth) {
//		if (this == this.board.getCurrentPlayer()) {
//			gc.setStroke(Color.RED);
//		}
//		int x = this.getLocation().getX()*tileWidth + (tileWidth/4);
//		int y = this.getLocation().getY()*tileWidth + (tileWidth/4);
//		gc.strokeOval(x, y, (tileWidth/2), (tileWidth/2));
//		gc.setStroke(Color.BLACK);
		
		
//		Image image = null;
//		try {
//			image = new Image(new FileInputStream("Howard-no-background.png"),(tileWidth/3)*2, (tileWidth/3)*2,true,true);
//		} catch (IOException e) {
//		}
		int x = this.getLocation().getX()*tileWidth + (tileWidth/4);
		int y = this.getLocation().getY()*tileWidth;
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
	
	public void loadSprite() {
		Image image = null;
		try {
			image = new Image(new FileInputStream("Howard-no-background.png"),(Main.TILE_WIDTH/3)*2, (Main.TILE_WIDTH/3)*2,true,true);
		} catch (IOException e) {
		}
		this.sprite = image;
	}
}
