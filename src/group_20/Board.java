package group_20;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

//TODO make sure can't push fixed tiles
//TODO currently tiles don't really keep track of players: There's a lot of null pointers and pointers which aren't valid any more. Fix this.
public class Board extends Task<Void>{
	
	/**
	 * Reference to the board template used
	 */
	private int boardID;
	
	/**
	 * Length of the board
	 */
	private int length;
	
	/**
	 * Width of the board
	 */
	private int width;
	
	/**
	 * Matrix representing the physical game board
	 */
	private FloorTile[][] gameBoard;
	
	/**
	 * The Board's goal tile
	 */
	private Goal goalTile;
	
	/**
	 * List of players on the board
	 */
	private Player[] players;
	
	/**
	 * Index in player list of current player taking their turn
	 */
	private int currentPlayer;
	
	/**
	 * Graphic context of board
	 */
	private GraphicsContext gc;
	
	/**
	 * Location of last click on board
	 */
	private Location lastClickLocation;
	
	/**
	 * State of game if waiting for user to either continue or exit
	 */
	private boolean waitingForExitOrContinue;
	
	/**
	 * Stores if GUI has given relevant input to change state of waitingForExitOrContinue
	 */
	private boolean continueGame;
	
	public Board(int boardID, int width, int length, FloorTile[][] gameBoard, Player[] players, int startingPlayer) {
		this.boardID = boardID;
		this.length = length;
		this.width = width;
		this.gameBoard = gameBoard;
		this.players = players;
		this.assignPlayersToBoard();
		this.currentPlayer = startingPlayer;
		this.goalTile = this.findGoalTile();
	}
	
	//TODO
	private Goal findGoalTile() {
		for (FloorTile[] col : this.gameBoard) {
			for (FloorTile t : col) {
				if (t instanceof Goal) {
					return (Goal) t;
				}
			}
		}
		return null;
	}
	
	public void assignPlayersToBoard() {
		for (Player p: this.players) {
			p.setBoard(this);
		}
	}
	
	/**
	 * Assigns players to the tiles they're on
	 * As in makes tiles point to the players standing on them
	 */
	public void assignPlayersToTiles() {
		for (Player p: this.players) {
			Location playerLocation = p.getLocation().copy();
			FloorTile tileAtLocation = this.gameBoard[playerLocation.getX()][playerLocation.getY()];
			tileAtLocation.setPlayer(p);
		}
	}
	
	//TODO make sure tile locations are updated
	/**
	 * Inserts a given tile at a given location if the location is valid
	 * @param t Tile to insert onto board
	 * @param l Location to insert tile at
	 */
	public void insertTile(FloorTile t, Location l) {
		//Checks you aren't inserting at corners of the board
		if (!(l.equals(0,0) || l.equals(0,this.length - 1) ||
				l.equals(this.width - 1, 0) || l.equals(this.width - 1, this.length - 1))) {
			//this.gameBoard[l.getX()][l.getY()] = t;
			
			FloorTile ejectedTile = null;
			Player ejectedPlayer = null;
			if (l.getX() == 0) {
				ejectedTile = this.gameBoard[this.width-1][l.getY()];
				ejectedPlayer = this.gameBoard[this.width-1][l.getY()].getPlayer();
				for (int i = this.width-1; i > 0; i--) {
					this.gameBoard[i][l.getY()] = this.gameBoard[i-1][l.getY()];
					this.gameBoard[i][l.getY()].setLocation(new Location(i,l.getY()));
					if (this.gameBoard[i-1][l.getY()].hasPlayer()) {
						this.gameBoard[i-1][l.getY()].getPlayer().setLocation(new Location(i,l.getY()));
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
				
			} else if (l.getX() == this.width-1) {
				ejectedTile = this.gameBoard[0][l.getY()];
				ejectedPlayer = this.gameBoard[0][l.getY()].getPlayer();
				for (int i = 0; i < this.length-1; i++) {
					this.gameBoard[i][l.getY()] = this.gameBoard[i+1][l.getY()];
					this.gameBoard[i][l.getY()].setLocation(new Location(i,l.getY()));
					if (this.gameBoard[i+1][l.getY()].hasPlayer()) {
						this.gameBoard[i+1][l.getY()].getPlayer().setLocation(new Location(i,l.getY()));
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;

			} else if (l.getY() == 0) {
				ejectedTile = this.gameBoard[l.getX()][this.width-1];
				ejectedPlayer = this.gameBoard[l.getX()][this.width-1].getPlayer();
				for (int i = this.width-1; i > 0; i--) {
					this.gameBoard[l.getX()][i] = this.gameBoard[l.getX()][i-1];
					this.gameBoard[l.getX()][i].setLocation(new Location(l.getX(),i));
					if (this.gameBoard[l.getX()][i-1].hasPlayer()) {
						this.gameBoard[l.getX()][i-1].getPlayer().setLocation(new Location(l.getX(),i));
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
				
			} else if (l.getY() == this.length-1) {
				ejectedTile = this.gameBoard[l.getX()][0];
				ejectedPlayer = this.gameBoard[l.getX()][0].getPlayer();
				for (int i = 0; i < this.width-1; i++) {
					this.gameBoard[l.getX()][i] = this.gameBoard[l.getX()][i+1];
					this.gameBoard[l.getX()][i].setLocation(new Location(l.getX(),i));
					if (this.gameBoard[l.getX()][i+1].hasPlayer()) {
						this.gameBoard[l.getX()][i+1].getPlayer().setLocation(new Location(l.getX(),i));
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
				
			} else {
				System.out.println("Invalid tile insertion location");
			}
			
			//Adds ejected tile to silk bag
			SilkBag.addTile(ejectedTile);
			
			if (ejectedPlayer != null) {
				Player p = this.getTileAt(ejectedPlayer.getLocation()).getPlayer();
				if (p != null) {
					ejectedPlayer.setLocation(l);
					p.setLocation(p.getLocation());
				} else {
					ejectedPlayer.setLocation(l);
				}
			}
			System.out.println("Tile inserted at: " + l.toString());
		}
	}
	
	public boolean rowContainsFixedTile(Location l) {
		//Inserting at row
		if (l.getX() == 0 || l.getX() == this.width-1) {
			int y = l.getY();
			for (int i = 0; i < this.width; i++) {
				if (this.gameBoard[i][y].isFixed()) {
					return true;
				}
			}
		//Inserting at column
		} else if (l.getY() == 0 || l.getY() == this.length-1) {
			int x = l.getX();
			for (int i = 0; i < this.length; i++) {
				if (this.gameBoard[x][i].isFixed()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Boolean canInsertAt(Location l) {
		//Is in bounds
		if ((this.isInBounds(l)) && //And isn't a corner
				!(l.equals(0,0) || l.equals(0,this.length - 1) ||
				l.equals(this.width - 1, 0) || l.equals(this.width - 1, this.length - 1))) {
			//If is an edge
			if ((l.getY() == 0 || l.getX() == 0 || 
					l.getY() == this.length-1 || l.getX() == this.width-1) &&
					!this.rowContainsFixedTile(l)){ //And row/column doesn't contain fixed tiles
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//TODO
	public void returnToBag(FloorTile t) {
		//this.silkBag.returnTile(t);//TODO change returnTile(Tile t) to returnTile(FloorTile t) in silkbag class
	}
	
	/**
	 * Checks if a player could move from location 'l' in direction 'd'
	 * @param l Location of player
	 * @param d Direction to move
	 * @return True if player can move from 'l' in direction 'd'
	 */
	public boolean canMove(Location l, Direction d) {
		FloorTile currentTile = this.getTileAt(l);
		Location locationOfOppositeTile = l.copy();
		locationOfOppositeTile.update(d);
		
		if (this.isInBounds(locationOfOppositeTile)) {
			FloorTile oppositeTile = this.getTileAt(locationOfOppositeTile);
			if (currentTile.canExitTo(d) && oppositeTile.canEnterFrom(d.opposite())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if given location is in bounds of board
	 * @param l Location to check
	 * @return True if l is on bounds of the board
	 */
	public boolean isInBounds(Location l) {
		if (l.getX() >= 0 && l.getX() < this.width &&
				l.getY() >= 0 && l.getY() < this.length) {
			return true;
		} else {
			return false;
		}
	}
	
	//For testing
	public FloorTile[] tempTestCalculateArea() {
		return this.calculateArea(this.gameBoard[0][0]);
	}
	
	/**
	 * Find a given tile on the board and then calculates the 3x3 around it
	 * @param t Tile on board
	 * @return 3x3 of tiles around the tile
	 */
	public FloorTile[] calculateArea(FloorTile t) {
		Location l = null;
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				FloorTile currentTile = this.gameBoard[i][j];
				if (currentTile == t) {
					l = new Location(i,j);
				}
			}
		}
		return calculateArea(l);
	}
	
	/**
	 * Very bad method for calculating 3x3 area around a given tile
	 * Think Finn implemented a better one in the action tile classes
	 * @param l Location of center tile
	 * @return All tiles in bounds of board in a 3x3 area around l
	 */
	public FloorTile[] calculateArea(Location l) {
		if (this.isInBounds(l)) {
			ArrayList<FloorTile> tilesInBounds = new ArrayList<FloorTile>();
			Location temp = l.copy();
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.NORTH);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.EAST);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.SOUTH);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.SOUTH);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.WEST);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.WEST);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.NORTH);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			temp.update(Direction.NORTH);
			if (this.isInBounds(temp)) {
				System.out.println(temp.toString());
				tilesInBounds.add(this.getTileAt(temp));
			}
			
			FloorTile[] arr = new FloorTile[tilesInBounds.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = (FloorTile) tilesInBounds.get(i);
			}
			return arr;
			//return (FloorTile[]) tilesInBounds.toArray(); //TODO can't cast Object[] to FloorTile[]
		} else {
			return null;
		}
	}
	
	/**
	 * Setter for length of board
	 * @param length New length of board
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Setter for width of board
	 * @param width New value for width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Getter for length of board
	 * @return Length of board
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * Getter for width of board
	 * @return Width of board
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Sets players on the board
	 * @param players New list of players
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	/**
	 * Returns list of players
	 * @return All players on board
	 */
	public Player[] getPlayers() {
		return this.players;
	}
	
	//Change order of players by giving a new list of players. Not sure this is needed?
	public void changePlayerOrder(Player[] playerOrder) {
		
	}
	
	/**
	 * Checks if game has finished
	 * @return True if game has finished
	 */
	public boolean gameOver() {
		return this.goalTile.hasPlayer();
	}
	
	//Sets player of FloorTile at given location
	//As in finds FloorTile at location and then sets the tile's player pointer
	public void setPlayer(Player p, Location l) {
		//Not sure if this is needed
	}
	
	/**
	 * Gets the tile at a given location
	 * @param l Location of tile
	 * @return Tile at location
	 */
	public FloorTile getTileAt(Location l) {
		if (this.isInBounds(l)) {
			return this.gameBoard[l.getX()][l.getY()];
		} else {
			return null;
		}
	}
	
	/**
	 * Sets board's graphics context
	 * @param gc New graphics context
	 */
	public void setGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}
	
	/**
	 * Getter for graphics context
	 * @return Board's graphics context
	 */
	public GraphicsContext getGraphicsContext() {
		return this.gc;
	}
	
	/**
	 * Draws the board onto the board's GraphicsContext
	 */
	public void draw() {
		//Draws all tiles
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				FloorTile currentTile = this.gameBoard[i][j];
				currentTile.draw(this.gc,i*Main.TILE_WIDTH, j*Main.TILE_WIDTH);
			}
		}
		
		//Draws all players
		for (Player p: this.players) {
			p.draw(this.gc);
		}
		
		//Highlights appropriate tiles depending on player's turn state
		switch (this.getCurrentPlayer().getCurrentStageOfTurn()) {
		case 1:
			this.highlightValidInsertLocations();
			break;
		case 3:
			this.highlightValidMoves();
			break;
		}
		this.getCurrentPlayer().highlight(this.gc);
	}
	
	/**
	 * Returns the current player taking their turn
	 * @return Current player taking their turn
	 */
	public Player getCurrentPlayer() {
		return this.players[this.currentPlayer];
	}
	
	/**
	 * Sets the board to reference the next player in the player list
	 */
	public void advancePlayerTurn() {
		this.currentPlayer++;
		if (this.currentPlayer > 3) {
			this.currentPlayer = 0;
		}
	}
	
	/**
	 * Randomized all player locations
	 */
	public void randomizeAllPlayerLocations() {
		for (Player p: this.players) {
			p.randomizeLocation(this.width, this.length);
		}
	}
	
	/**
	 * Highlights on the canvas the valid moves that the current player can make
	 */
	public void highlightValidMoves() {
		Location playerLocation = this.getCurrentPlayer().getLocation();
		//FloorTile currentTile = this.getTileAt(playerLocation);
		
		if (this.canMove(playerLocation, Direction.NORTH)) {
			Location lNorth = playerLocation.check(Direction.NORTH);
			FloorTile tNorth = this.getTileAt(lNorth);
			tNorth.highlight(this.gc, null);
			//System.out.println("Valid move at: " + lNorth.toString());
		}
		if (this.canMove(playerLocation, Direction.EAST)) {
			Location lEast = playerLocation.check(Direction.EAST);
			FloorTile tEast = this.getTileAt(lEast);
			tEast.highlight(this.gc, null);
			//System.out.println("Valid move at: " + lEast.toString());
		}
		if (this.canMove(playerLocation, Direction.SOUTH)) {
			Location lSouth = playerLocation.check(Direction.SOUTH);
			FloorTile tSouth = this.getTileAt(lSouth);
			tSouth.highlight(this.gc, null);
			//System.out.println("Valid move at: " + lSouth.toString());
		}
		if (this.canMove(playerLocation, Direction.WEST)) {
			Location lWest = playerLocation.check(Direction.WEST);
			FloorTile tWest = this.getTileAt(lWest);
			tWest.highlight(this.gc, null);
			//System.out.println("Valid move at: " + lWest.toString());
		}
	}
	
	/**
	 * Highlights on the canvas the valid insert locations a tile can be inserted at
	 */
	public void highlightValidInsertLocations() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				Location l = new Location(i,j);
				if (this.canInsertAt(l)) {
					this.gameBoard[i][j].highlight(this.gc, null);
				}
			}
		}
	}
	
	/**
	 * Converts raw coordinates into a board index[x][y]
	 * @param xClick X coordinate of click
	 * @param yClick Y coordinate of click
	 * @return Corresponding board index[x][y]
	 */
	public Location getCoordinateOfClick(Double xClick, Double yClick) {
		int x = (int) Math.round(xClick)/Main.TILE_WIDTH;
		int y = (int) Math.round(yClick)/Main.TILE_WIDTH;
		return new Location(x,y);
	}
	
	/**
	 * Setter for last click location
	 * @param x Raw X coordinate of click
	 * @param y Raw Y coordinate of click
	 */
	public void setLastClickLocation(Double x, Double y) {
		System.out.println("Updated last click location");
		this.lastClickLocation = this.getCoordinateOfClick(x, y);
	}
	
	/**
	 * Setter for last click location
	 * @param l Location of click
	 */
	public void setLastClickLocation(Location l) {
		System.out.println("(Probably) Cleared last click location");
		this.lastClickLocation = l;
	}
	
	/**
	 * Getter for last click location on board
	 * @return Last click location on board
	 */
	public Location getlastClickLocation() {
		return this.lastClickLocation;
	}
	
	/**
	 * Interrupts the thread until a click is made on the board and returns the tile clicked
	 * @return Tile clicked
	 */
	public FloorTile getTileAtClick() {
		this.getLocationAtClick();
		return this.getTileAt(this.lastClickLocation);
	}
	
	/**
	 * Interrupts the thread until a click is made on the board and returns the location clicked
	 * @return Location clicked
	 */
	public Location getLocationAtClick() {
		synchronized (this) {
			this.setLastClickLocation(null);
			while (this.lastClickLocation == null) {
				try {
					System.out.println("I'm waiting");
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("You clicked!");
		}
		return this.lastClickLocation;
	}
	
	/**
	 * Highlights each tile that matches a filter lambda.
	 * @param f Filter function
	 */
	private void highlightTilesMatching(Function<FloorTile, Boolean> f) {
		for (FloorTile[] row : this.gameBoard) {
			for (FloorTile t : row) {
				if (f.apply(t)) {
					Location loc = t.getLocation();
					t.highlight(gc,null);
				}
			}
		}
	}
	
	/**
	 * Return a Tile that matches a filter lambda.
	 * @param f Filter function
	 * @return A FloorTile matching {@code f}
	 */
	public FloorTile getTileAtClickMatching(Function<FloorTile, Boolean> f) {
		highlightTilesMatching(f);
		FloorTile t;
		do {
			t = getTileAtClick();
		} while (!f.apply(t));
		// draw(); // Reset the board visuals
		return t;
	}

	public boolean isWaitingForExitOrContinue() {
		return this.waitingForExitOrContinue;
	}
	
	public void setContinueGame(boolean b) {
		this.continueGame = true;
	}
	
	public void checkForExitGame() {
		synchronized (this) {
			this.continueGame = false;
			while (!this.continueGame) {
				this.waitingForExitOrContinue = true;
				try {
					System.out.println("Would you like to Save&Exit or Continue Playing?");
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Continuing game!!!");
			this.waitingForExitOrContinue = false;
		}
	}
	
	public String getCurrentStepMessage() {
		String msg = "Player " + (this.currentPlayer+1) + "'s turn: ";
		switch (this.getCurrentPlayer().getCurrentStageOfTurn()) {
		case 1:
			//msg += "Drawing a tile from the silk bag";
			msg += "Please select a location to insert the floor tile.";
			break;
		case 2:
			msg += "Please select an action tile to use, or skip playing an action tile.";
			break;
		case 3:
			msg += "Please select a tile to move to.";
			break;
		default:
			msg += "Please end your turn, or save and exit the game.";
		}
		return msg;
	}
	
	@Override
	/**
	 * Called when thread is started
	 * @return Void
	 * @throws Exception
	 */
	protected Void call() throws Exception {
		System.out.println("Starting Game");
		while (!this.gameOver()) {
			this.getCurrentPlayer().takeTurn();
			if (this.gameOver()) {
				break;
			}
			this.checkForExitGame();
			System.out.println("Advancing player");
			this.advancePlayerTurn();
			System.out.println("Drawing board");
			//this.draw();
			System.out.println("Next Player's Turn");
			System.out.println("\nPlayer " + (this.currentPlayer+1) + "'s turn");
		}
		System.out.println("GAME OVER!!!!");
		return null;
	}
	
	/**
	 * Should update the leaderboard on who won and lost
	 */
	public void updateScores() {
		Player currentPlayer = this.getCurrentPlayer();
		Profile p = currentPlayer.getProfile();
		if (p != null) {
			//p.updateProfile(this.boardID, true);
		}
	}
	
	public String saveFormat() {
		//(int boardID, int width, int length, FloorTile[][] gameBoard, Player[] players, int startingPlayer)
		String str = "{Board," +
				this.boardID + "," +
				this.width + "," +
				this.length + ",\n";
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				str += this.gameBoard[i][j].saveFormat() + ",\n";
			}
		}
		for (int i = 0; i < this.players.length; i++) {
			str += this.players[i].saveFormat() + ",\n";
		}
		str += this.currentPlayer + ",Board}";
		return str;
	}
}
