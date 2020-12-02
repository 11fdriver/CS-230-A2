package group_20;

import java.util.ArrayList;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

//TODO make sure can't push fixed tiles
//TODO currently tiles don't really keep track of players: There's a lot of null pointers and pointers which aren't valid any more. Fix this.
public class Board extends Task<Void>{
	private final int TILE_WIDTH;
	private int boardID;
	private int length;
	private int width;
	private FloorTile[][] gameBoard;
	private SilkBag silkBag;
	//private Player player1;
	private Goal goalTile;
	private Player[] players;
	private int currentPlayer;
	private Canvas canvas;
	private GraphicsContext gc;
	private Location lastClickLocation;
	private Double xClick;
	private Double yClick;
	
	//For testing
	public Board(Canvas canvas, int TILE_WIDTH, int width, int length) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.TILE_WIDTH = TILE_WIDTH;
		this.boardID = 1;
		this.length = length;
		this.width = width;
		this.silkBag = new SilkBag(this.TILE_WIDTH);
		this.gameBoard = new FloorTile[width][length];		
		//player1 = new Player(this, this.silkBag, new Location(0,0));
		Player[] newPlayers = {new Player(this, this.silkBag,this.TILE_WIDTH,"Howard-no-background.png", new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Dagon-no-background.png",new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Nightgaunt-no-background.png",new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Shelley-no-background.png",new Location(0,0))};
		this.players = newPlayers;
		this.currentPlayer = 0;
		this.populate();//TODO change from temp full population with random tiles
		this.goalTile = new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
		this.randomizeAllPlayerLocations();//For testing
	}
	
	//For testing too
	public Board(int width, int length, int TILE_WIDTH) {
		this.gc = null;
		this.TILE_WIDTH = TILE_WIDTH;
		this.boardID = 1;
		this.length = length;
		this.width = width;
		this.silkBag = new SilkBag(this.TILE_WIDTH);
		this.gameBoard = new FloorTile[width][length];		
		//player1 = new Player(this, this.silkBag, new Location(0,0));
		Player[] newPlayers = {new Player(this, this.silkBag,this.TILE_WIDTH,"Howard-no-background.png", new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Dagon-no-background.png",new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Nightgaunt-no-background.png",new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Shelley-no-background.png",new Location(0,0))};
		this.players = newPlayers;
		this.currentPlayer = 0;
		this.populate();//TODO change from temp full population with random tiles
		this.goalTile = new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
		this.randomizeAllPlayerLocations();//For testing
	}
	
	public Board(Canvas canvas, int TILE_WIDTH,int boardID, int width, int length, SilkBag silkBag, FloorTile[][] gameBoard) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.TILE_WIDTH = TILE_WIDTH;
		this.boardID = boardID;
		this.length = length;
		this.width = width;
		this.silkBag = silkBag;
		this.gameBoard = gameBoard;
		//this.gameBoard = new FloorTile[width][length];		
		//player1 = new Player(this, this.silkBag, new Location(0,0));
		Player[] newPlayers = {new Player(this, this.silkBag,this.TILE_WIDTH,"Howard-no-background.png", new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Dagon-no-background.png",new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Nightgaunt-no-background.png",new Location(0,0)),
				new Player(this, this.silkBag,this.TILE_WIDTH,"Shelley-no-background.png",new Location(0,0))};
		this.players = newPlayers;
		this.currentPlayer = 0;
		this.populate();//TODO change from temp full population with random tiles
		this.goalTile = new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
		this.randomizeAllPlayerLocations();//For testing
	}
	
	//redundant as of this version
	private void placeKnownTiles(FloorTile[] knownFloorTile, Location[] floorTileLocation) {
		
	}
	
	//Just for testing
	public void randomizeBoard() {
		this.gameBoard = new FloorTile[this.width][this.length];
		this.populate();
	}
	
	private void populate() {
		//Populates non fixed spaces with random tiles
		//Somehow need to put the fixed tiles in first???
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < length; j++) {
				if (this.gameBoard[i][j] == null) {
					this.gameBoard[i][j] = silkBag.drawFloorTile();
				}
			}
		}
		this.assignPlayersToTiles();
	}
	
	public void assignPlayersToTiles() {
		for (Player p: this.players) {
			Location playerLocation = p.getLocation().copy();
			FloorTile tileAtLocation = this.gameBoard[playerLocation.getX()][playerLocation.getY()];
			tileAtLocation.setMyPlayer(p);
		}
	}
	
	public void startGame() {
		//while (!this.gameOver()) {
			this.getCurrentPlayer().takeTurn();
			this.advancePlayerTurn();
			this.draw();
		//}
	}
	
	//TODO players ejected can be placed on top of another player
	//TODO not allow insert if row/column contains fixed tiles
		//Currently doesn't factor in fixed tiles
	public void insertTile(FloorTile t, Location l) {
		//Checks you aren't inserting at corners of the board
		if (!(l.equals(0,0) || l.equals(0,this.length - 1) ||
				l.equals(this.width - 1, 0) || l.equals(this.width - 1, this.length - 1))) {
			//this.gameBoard[l.getX()][l.getY()] = t;
			
			FloorTile ejectedTile = null;
			if (l.getX() == 0) {
				ejectedTile = this.gameBoard[this.width-1][l.getY()];
				for (int i = this.width-1; i > 0; i--) {
					this.gameBoard[i][l.getY()] = this.gameBoard[i-1][l.getY()];
					if (this.gameBoard[i-1][l.getY()].hasPlayer()) {
						this.gameBoard[i-1][l.getY()].getMyPlayer().setLocation(new Location(i,l.getY()));
						//this.gameBoard[i][l.getY()].setMyPlayer(this.gameBoard[i-1][l.getY()].getMyPlayer());//Player pointer stuff ehhh iffy
						//this.gameBoard[i-1][l.getY()].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else if (l.getX() == this.width-1) {
				ejectedTile = this.gameBoard[0][l.getY()];
				for (int i = 0; i < this.length-1; i++) {
					this.gameBoard[i][l.getY()] = this.gameBoard[i+1][l.getY()];
					if (this.gameBoard[i+1][l.getY()].hasPlayer()) {
						this.gameBoard[i+1][l.getY()].getMyPlayer().setLocation(new Location(i,l.getY()));
						//this.gameBoard[i][l.getY()].setMyPlayer(this.gameBoard[i+1][l.getY()].getMyPlayer());//Player pointer stuff ehhh iffy
						//this.gameBoard[i+1][l.getY()].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else if (l.getY() == 0) {
				ejectedTile = this.gameBoard[l.getX()][this.width-1];
				for (int i = this.width-1; i > 0; i--) {
					this.gameBoard[l.getX()][i] = this.gameBoard[l.getX()][i-1];
					if (this.gameBoard[l.getX()][i-1].hasPlayer()) {
						this.gameBoard[l.getX()][i-1].getMyPlayer().setLocation(new Location(l.getX(),i));
						//this.gameBoard[l.getX()][i].setMyPlayer(this.gameBoard[l.getX()][i-1].getMyPlayer());//Player pointer stuff ehhh iffy
						//this.gameBoard[l.getX()][i-1].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else if (l.getY() == this.length-1) {
				ejectedTile = this.gameBoard[l.getX()][0];
				for (int i = 0; i < this.width-1; i++) {
					this.gameBoard[l.getX()][i] = this.gameBoard[l.getX()][i+1];
					if (this.gameBoard[l.getX()][i+1].hasPlayer()) {
						this.gameBoard[l.getX()][i+1].getMyPlayer().setLocation(new Location(l.getX(),i));
						//this.gameBoard[l.getX()][i].setMyPlayer(this.gameBoard[l.getX()][i+1].getMyPlayer());//Player pointer stuff ehhh iffy
						//this.gameBoard[l.getX()][i+1].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else {
				System.out.println("Invalid tile insertion location");
			}
			
			if (ejectedTile != null && ejectedTile.hasPlayer()) {
				t.setMyPlayer(ejectedTile.getMyPlayer());
				t.getMyPlayer().setLocation(l.copy());
			}
			System.out.println("Tile inserted at: " + l.toString());
		}
	}
	
	//TODO change to actually check for fixed tiles
	public Boolean canInsertAt(Location l) {
		//Is in bounds
		if ((this.isInBounds(l)) && //And isn't a corner
				!(l.equals(0,0) || l.equals(0,this.length - 1) ||
				l.equals(this.width - 1, 0) || l.equals(this.width - 1, this.length - 1))) {
			if (l.getY() == 0 || l.getX() == 0 || 
					l.getY() == this.length-1 || l.getX() == this.width-1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void returnToBag(FloorTile t) {
		//this.silkBag.returnTile(t);//TODO change returnTile(Tile t) to returnTile(FloorTile t) in silkbag class
	}
	
	public boolean canMove(Location l, Direction d) {
		FloorTile tileAtLocation = this.gameBoard[l.getX()][l.getY()];
		Location newLocation = l.copy();
		newLocation.update(d);
		
		if (this.isInBounds(newLocation)) {
			FloorTile oppositeTile = this.gameBoard[newLocation.getX()][newLocation.getY()];
			if (!oppositeTile.hasPlayer()) {//Isn't occupied
				Direction oppositeDirection = this.invertDirection(d);
				if (tileAtLocation.isValidMove(d) && oppositeTile.isValidMove(oppositeDirection)) { //Can exit/enter tiles
					return true;
				}
			}
		}
		return false; //Temp
	}
	
	private Direction invertDirection(Direction d) {
		switch (d) {
		case NORTH:
			return Direction.SOUTH;
		case EAST:
			return Direction.WEST;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		default:
			return null;
		}
	}
	
	public boolean isInBounds(Location l) {
		if (l.getX() >= 0 && l.getX() < this.width &&
				l.getY() >= 0 && l.getY() < this.length) {
			return true;
		} else {
			return false;
		}
	}
	
	public FloorTile[] tempTestCalculateArea() {
		return this.calculateArea(this.gameBoard[0][0]);
	}
	
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
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public Player[] getPlayers() {
		return this.players;
	}
	
	public void changePlayerOrder(Player[] playerOrder) {
		
	}
	
	public boolean gameOver() {
		return this.goalTile.hasPlayer();
	}
	
	//Sets player of FloorTile at given location
	//As in finds FloorTile at location and then sets the tile's player pointer
	public void setPlayer(Player p, Location l) {
		
	}
	
	public FloorTile getTileAt(Location l) {
		if (this.isInBounds(l)) {
			return this.gameBoard[l.getX()][l.getY()];
		} else {
			return null;
		}
	}
	
	public void setGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public GraphicsContext getGraphicsContext() {
		return this.gc;
	}
	
	public void draw() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				//System.out.println("Drawing tile at (" + i + "," + j + ")");
				FloorTile currentTile = this.gameBoard[i][j];
				currentTile.draw(i*TILE_WIDTH, j*TILE_WIDTH, gc, TILE_WIDTH);
				//Highlights current player
//				if (currentTile.getMyPlayer() == this.getCurrentPlayer()) {
//					currentTile.highlight(i*tileWidth, j*tileWidth, gc, tileWidth);
//				}
			}
		}
		
		//this.getCurrentPlayer().draw(gc, tileWidth);
		
		for (Player p: this.players) {
			p.draw(this.gc);
		}
		
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
	
	//Just for testing animation
	public Player getCurrentPlayer() {
		return this.players[this.currentPlayer];
	}
	
	public void advancePlayerTurn() {
		this.currentPlayer++;
		if (this.currentPlayer > 3) {
			this.currentPlayer = 0;
		}
	}
	
	public void randomizeAllPlayerLocations() {
		for (Player p: this.players) {
			p.randomizeLocation(this.width, this.length);
		}
	}
	
	public void insertRandomTile() {
		FloorTile tileToInsert = this.silkBag.drawFloorTile();
		Location insertLocation = this.getRandomInsertLocation();
		this.insertTile(tileToInsert, insertLocation);
	}
	
	public Location getRandomInsertLocation() {
		int x = 0;
		int y = 0;
		
		Random r = new Random();
		int bound = r.nextInt(5);
		
		switch (bound) {
		case 1:
			x = 0;
			y = r.nextInt(this.length-2)+1;
			break;
		case 2:
			y = this.length-1;
			x = r.nextInt(this.width-2)+1;
			break;
		case 3:
			y = 0;
			x = r.nextInt(this.width-2)+1;
			break;
		case 4:
			x = this.width-1;
			y = r.nextInt(this.length-2)+1;
			break;
		default:
			y = 1;
			x = 1;
		}
		
		return new Location(x,y);
	}
	
	public void movePlayer(Double xMouseClick, Double yMouseClick) {
		Location clickLocation = this.getCoordinateOfClick(xMouseClick, yMouseClick);
		int playerX = this.getCurrentPlayer().getLocation().getX();
		int playerY = this.getCurrentPlayer().getLocation().getY();
		
		Direction directionToMove = null;
		if (clickLocation.getY() == playerY+1 && clickLocation.getX() == playerX) {
			directionToMove = Direction.SOUTH;
		} else if (clickLocation.getY() == playerY-1 && clickLocation.getX() == playerX) {
			directionToMove = Direction.NORTH;
		} else if (clickLocation.getX() == playerX+1 && clickLocation.getY() == playerY) {
			directionToMove = Direction.EAST;
		} else if (clickLocation.getX() == playerX-1 && clickLocation.getY() == playerY) {
			directionToMove = Direction.WEST;
		}
		
		if (directionToMove != null) {
			//System.out.println("Attempting to move");
			if (this.canMove(this.getCurrentPlayer().getLocation(), directionToMove)) {
				this.getCurrentPlayer().move(directionToMove);
			} else {
				System.out.println("Can't move to selected tile");
			}
		} else {
			System.out.println("Selected tile is too far away from current player");
		}
	}
	
	public void highlightValidMoves() {
		Location playerLocation = this.getCurrentPlayer().getLocation();
		//FloorTile currentTile = this.getTileAt(playerLocation);
		
		if (this.canMove(playerLocation, Direction.NORTH)) {
			Location lNorth = playerLocation.check(Direction.NORTH);
			FloorTile tNorth = this.getTileAt(lNorth);
			tNorth.highlight(lNorth.getX()*this.TILE_WIDTH, lNorth.getY()*this.TILE_WIDTH, gc, this.TILE_WIDTH);
			//System.out.println("Valid move at: " + lNorth.toString());
		}
		if (this.canMove(playerLocation, Direction.EAST)) {
			Location lEast = playerLocation.check(Direction.EAST);
			FloorTile tEast = this.getTileAt(lEast);
			tEast.highlight(lEast.getX()*this.TILE_WIDTH, lEast.getY()*this.TILE_WIDTH, gc, this.TILE_WIDTH);
			//System.out.println("Valid move at: " + lEast.toString());
		}
		if (this.canMove(playerLocation, Direction.SOUTH)) {
			Location lSouth = playerLocation.check(Direction.SOUTH);
			FloorTile tSouth = this.getTileAt(lSouth);
			tSouth.highlight(lSouth.getX()*this.TILE_WIDTH, lSouth.getY()*this.TILE_WIDTH, gc, this.TILE_WIDTH);
			//System.out.println("Valid move at: " + lSouth.toString());
		}
		if (this.canMove(playerLocation, Direction.WEST)) {
			Location lWest = playerLocation.check(Direction.WEST);
			FloorTile tWest = this.getTileAt(lWest);
			tWest.highlight(lWest.getX()*this.TILE_WIDTH, lWest.getY()*this.TILE_WIDTH, gc, this.TILE_WIDTH);
			//System.out.println("Valid move at: " + lWest.toString());
		}
	}
	
	public void highlightValidInsertLocations() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				Location l = new Location(i,j);
				if (this.canInsertAt(l)) {
					this.gameBoard[i][j].highlight(i*this.TILE_WIDTH, j*this.TILE_WIDTH, this.gc, this.TILE_WIDTH);
				}
			}
		}
	}
	
	public Location getCoordinateOfClick(Double xClick, Double yClick) {
		int x = (int) Math.round(xClick)/this.TILE_WIDTH;
		int y = (int) Math.round(yClick)/this.TILE_WIDTH;
		return new Location(x,y);
	}
	
	public void setLastClickLocation(Double x, Double y) {
		System.out.println("Updated last click location");
		this.lastClickLocation = this.getCoordinateOfClick(x, y);
	}
	
	public void setLastClickLocation(Location l) {
		System.out.println("(Probably) Cleared last click location");
		this.lastClickLocation = l;
	}
	
	public Location getlastClickLocation() {
		return this.lastClickLocation;
	}
	
	public FloorTile getTileAtClick() {
		this.getLocationAtClick();
		return this.getTileAt(this.lastClickLocation);
	}
	
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
	
	@Override
	protected Void call() throws Exception {
//		System.out.println("Starting game");
//		this.getTileAtClick();
//		System.out.println("Getting another click");
//		this.getTileAtClick();
		
		System.out.println("Starting Game");
		while (!this.gameOver()) {
			this.getCurrentPlayer().takeTurn();
			System.out.println("Advancing player");
			this.advancePlayerTurn();
			System.out.println("Drawing board");
			//this.draw();
			System.out.println("Next Player's Turn");
		}
		return null;
	}
}
