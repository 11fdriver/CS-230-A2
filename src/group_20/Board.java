package group_20;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

//TODO currently tiles don't really keep track of players: There's a lot of null pointers and pointers which aren't valid any more. Fix this.
public class Board {
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
	private GraphicsContext gc;
	
	//For testing
	public Board(GraphicsContext gc, int TILE_WIDTH, int width, int length) {
		this.gc = gc;
		this.TILE_WIDTH = TILE_WIDTH;
		this.boardID = 1;
		this.length = length;
		this.width = width;
		this.silkBag = new SilkBag();
		this.gameBoard = new FloorTile[width][length];		
		//player1 = new Player(this, this.silkBag, new Location(0,0));
		Player[] newPlayers = {new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0))};
		this.players = newPlayers;
		this.currentPlayer = 0;
		this.populate();//TODO change from temp full population with random tiles
		this.goalTile = new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
		this.randomizeAllPlayerLocations();//For testing
	}
	
	//For testing too
	public Board(int width, int length) {
		this.gc = null;
		this.TILE_WIDTH = 30;
		this.boardID = 1;
		this.length = length;
		this.width = width;
		this.silkBag = new SilkBag();
		this.gameBoard = new FloorTile[width][length];		
		//player1 = new Player(this, this.silkBag, new Location(0,0));
		Player[] newPlayers = {new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0))};
		this.players = newPlayers;
		this.currentPlayer = 0;
		this.populate();//TODO change from temp full population with random tiles
		this.goalTile = new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
		this.randomizeAllPlayerLocations();//For testing
	}
	
	public Board(GraphicsContext gc, int TILE_WIDTH,int boardID, int width, int length, SilkBag silkBag, FloorTile[][] gameBoard) {
		this.gc = gc;
		this.TILE_WIDTH = TILE_WIDTH;
		this.boardID = boardID;
		this.length = length;
		this.width = width;
		this.silkBag = silkBag;
		this.gameBoard = gameBoard;
		//this.gameBoard = new FloorTile[width][length];		
		//player1 = new Player(this, this.silkBag, new Location(0,0));
		Player[] newPlayers = {new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0)),new Player(this, this.silkBag,new Location(0,0))};
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
						this.gameBoard[i][l.getY()].setMyPlayer(this.gameBoard[i-1][l.getY()].getMyPlayer());//Player pointer stuff ehhh iffy
						this.gameBoard[i-1][l.getY()].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else if (l.getX() == this.width-1) {
				ejectedTile = this.gameBoard[0][l.getY()];
				for (int i = 0; i < this.length-1; i++) {
					this.gameBoard[i][l.getY()] = this.gameBoard[i+1][l.getY()];
					if (this.gameBoard[i+1][l.getY()].hasPlayer()) {
						this.gameBoard[i+1][l.getY()].getMyPlayer().setLocation(new Location(i,l.getY()));
						this.gameBoard[i][l.getY()].setMyPlayer(this.gameBoard[i+1][l.getY()].getMyPlayer());//Player pointer stuff ehhh iffy
						this.gameBoard[i+1][l.getY()].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else if (l.getY() == 0) {
				ejectedTile = this.gameBoard[l.getX()][this.width-1];
				for (int i = this.width-1; i > 0; i--) {
					this.gameBoard[l.getX()][i] = this.gameBoard[l.getX()][i-1];
					if (this.gameBoard[l.getX()][i-1].hasPlayer()) {
						this.gameBoard[l.getX()][i-1].getMyPlayer().setLocation(new Location(l.getX(),i));
						this.gameBoard[l.getX()][i].setMyPlayer(this.gameBoard[l.getX()][i-1].getMyPlayer());//Player pointer stuff ehhh iffy
						this.gameBoard[l.getX()][i-1].setMyPlayer(null);//Player pointer stuff ehhh iffy
					}
				}
				this.gameBoard[l.getX()][l.getY()] = t;
			} else if (l.getY() == this.length-1) {
				ejectedTile = this.gameBoard[l.getX()][0];
				for (int i = 0; i < this.width-1; i++) {
					this.gameBoard[l.getX()][i] = this.gameBoard[l.getX()][i+1];
					if (this.gameBoard[l.getX()][i+1].hasPlayer()) {
						this.gameBoard[l.getX()][i+1].getMyPlayer().setLocation(new Location(l.getX(),i));
						this.gameBoard[l.getX()][i].setMyPlayer(this.gameBoard[l.getX()][i+1].getMyPlayer());//Player pointer stuff ehhh iffy
						this.gameBoard[l.getX()][i+1].setMyPlayer(null);//Player pointer stuff ehhh iffy
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
	
	
	public void returnToBag(FloorTile t) {
		//this.silkBag.returnTile(t);//TODO change returnTile(Tile t) to returnTile(FloorTile t) in silkbag class
	}
	
	public boolean canMove(Location l, Direction d) {
		FloorTile tileAtLocation = this.gameBoard[l.getX()][l.getY()];
		Location newLocation = l.copy();
		newLocation.update(d);
		
		if (this.isInBounds(newLocation)) {
			FloorTile oppositeTile = this.gameBoard[newLocation.getX()][newLocation.getY()];
			//if (!oppositeTile.hasPlayer()) {//Isn't occupied
				Direction oppositeDirection = this.invertDirection(d);
				if (tileAtLocation.isValidMove(d) && oppositeTile.isValidMove(oppositeDirection)) { //Can exit/enter tiles
					return true;
				}
			//}
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
	
	public void draw() {
		this.draw(this.gc,this.TILE_WIDTH);
	}
	
	public void draw(GraphicsContext gc, int tileWidth) {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.length; j++) {
				//System.out.println("Drawing tile at (" + i + "," + j + ")");
				this.gameBoard[i][j].draw(i*tileWidth, j*tileWidth, gc, tileWidth);
			}
		}
		
		//this.getCurrentPlayer().draw(gc, tileWidth);
		
		for (Player p: this.players) {
			p.draw(gc, tileWidth);
		}
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
}
