import java.util.ArrayList;
import java.util.Stack;

public class Player {
	private Location location;
	private ArrayList<ActionTile> inventory;
	private boolean doubleMove;
	private Board board;
	private SilkBag silkBag;
	private Stack<Location> previousLocations;
	private Boolean hasBeenBacktracked;
	
	/**
	 * Full Constructor to be called when loading a player object
	 * @param board
	 * @param silkbag
	 * @param location
	 * @param inventory
	 * @param previousLocations
	 */
	public Player(Board board, SilkBag silkbag, Location location, ArrayList<ActionTile> inventory, Stack<Location> previousLocations) {
		this.board = board;
		this.silkBag = silkbag;
		this.location = location;
		this.inventory = inventory;
		this.previousLocations = previousLocations;
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
		this.previousLocations = new Stack<Location>();
	}
	
	public void takeTurn() {
		this.drawTile();
	}
	
	/**
	 * Allows the player to draw a tile
	 * 	- Action Tile = Add to inventory
	 * 	- Floor Tile = Insert onto board (Also get input from user as to where to insert)
	 */
	public void stepOne() {
		FloorTile drawnTile = this.drawTile();
		//If tile is null then it was an ActionTile and was added to inventory
		if (drawnTile != null) {
			Location l = new Location(0,0); //As location to insert new tile -> temp value for now
			this.board.insertTile(drawnTile, l);
		}
	}
	
	/**
	 * Allows the player to play an ActionTile
	 *  - Handles user input for selecting a tile from inventory
	 */
	public void stepTwo() {
		//Skip step 2 if inventory is empty
		if (!this.inventory.isEmpty()) {
			//Allow the user to select a tile
			ActionTile chosenTile = new ActionTile();//TEMP
			chosenTile.play(this.board, this);
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
			Direction d = Direction.EAST;//User input
		
			//Loop until input is valid
			while (!canMove(d)) {
				d = Direction.EAST;//New direction from user input, for now constant
			}
			
			//Allow player to move
			this.addPreviousLocation(this.getLocation());
			this.getLocation().update(d);
			this.board.setPlayer(this, this.getLocation());
			
			//If player has a double move then recurse stepThree()
			if (this.doubleMove == true) {
				this.doubleMove = false;
				stepThree();
			}
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
	public FloorTile drawTile() {
		Tile drawnTile = silkBag.drawTile();
		
		//If is ActionTile
		if (drawnTile.getClass() == ActionTile.class) {
			System.out.println("I drew an action tile");
			this.addToInventory((ActionTile) drawnTile);
			
		//If is FloorTile
		} else if (drawnTile.getClass() == FloorTile.class) {
			System.out.println("I drew a floor tile");
			return (FloorTile) drawnTile;
			
		//If is Tile
		} else if (drawnTile.getClass() == Tile.class) {
			System.out.println("I draw a generic Tile? Why would you give me that?");
			
		//Anything else
		} else {
			System.out.println("What the heck did I just draw?????");
		}
		return null;
	}
	
	public void move(Direction d) {
		this.location.update(d);
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public ArrayList<ActionTile> getInventory() {
		return this.inventory;
	}
	
	public void setInventory(ArrayList<ActionTile> inventory) {
		this.inventory = inventory;
	}
	
	public void addToInventory(ActionTile t) {
		this.inventory.add(t);
	}
	
	public void setPreviousLocations(Stack<Location> previousLocations) {
		this.previousLocations = previousLocations;
	}
	
	public void addPreviousLocation(Location l) {
		this.previousLocations.push(l);
	}
	
	public Stack<Location> getPreviousLocations() {
		return this.previousLocations;
	}
}
