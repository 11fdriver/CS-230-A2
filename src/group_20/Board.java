package group_20;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class Board {
	private int boardID;
	private int length;
	private int width;
	private FloorTile[][] gameBoard;
	private SilkBag silkBag;
	//private Player player1;
	private Goal goalTile;
	private Player[] players;
	private int currentPlayer;
	
	public Board(int boardID, int length, int width, SilkBag silkBag, FloorTile[][] gameBoard) {
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
	}
	
	//redundant as of this version
	private void placeKnownTiles(FloorTile[] knownFloorTile, Location[] floorTileLocation) {
		
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
	
	public void insertTile(FloorTile t, Location l) {
		//Checks you aren't inserting at corners of the board
		if (!(l.equals(0,0) || l.equals(0,this.length - 1) ||
				l.equals(this.width - 1, 0) || l.equals(this.width - 1, this.length - 1))) {
			
		}
	}
	
	
	public void returnToBag(FloorTile t) {
		this.silkBag.returnTile(t);//TODO change returnTile(Tile t) to returnTile(FloorTile t) in silkbag class
	}
	
	public boolean canMove(Location l, Direction d) {
		FloorTile tileAtLocation = this.gameBoard[l.getX()][l.getY()];
		Location newLocation = l.copy();
		l.update(d);
		
		if (this.isInBounds(newLocation)) {
			FloorTile oppositeTile = this.gameBoard[newLocation.getX()][newLocation.getY()];
			Direction oppositeDirection = this.invertDirection(d);
			if (tileAtLocation.isValidMove(d) && oppositeTile.isValidMove(oppositeDirection)) {
				return true;
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
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public Player[] getPlayers() {
		return this.players;
	}
	
	public void changePlayerOrder(Player[] playerOrder) {
		
	}
	
	public boolean isOver() {
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
		this.advancePlayerTurn();
		return this.players[this.currentPlayer];
	}
	
	public void advancePlayerTurn() {
		this.currentPlayer++;
		if (this.currentPlayer > 3) {
			this.currentPlayer = 0;
		}
	}
}
