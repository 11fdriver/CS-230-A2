
public class Board {
	private int boardID;
	private int length;
	private int width;
	private Tile[][] gameBoard;
	private SilkBag silkBag;
	
	public Board(int boardID, int length, int width, SilkBag silkBag) {
		this.boardID = boardID;
		this.length = length;
		this.width = width;
		this.silkBag = silkBag;
		this.gameBoard = new Tile[length][width];
		this.populate();
	}
	
	private void placeKnownTiles(FloorTile[] knownFloorTile, Location[] floorTileLocation) {
		
	}
	
	private void populate() {
		//Populates non fixed spaces with random tiles
		//Somehow need to put the fixed tiles in first???
		for (int i = 0; i < this.length; i++) {
			for (int j = 0; j < width; j++) {
				if (this.gameBoard[i][j] == null) {
					this.gameBoard[i][j] = silkBag.drawFLoorTile();
				}
			}
		}
	}
	
	public void insertTile(FloorTile t, Location l) {
		
	}
	
	public void returnToBag(FloorTile t) {
		
	}
	
	public boolean canMove(Location l, Direction d) {
		return true; //Temp
	}
	
	public boolean isOutOfBounds(Location l) {
		return true; //Temp
	}
	
	public FloorTile[] calculateArea(FloorTile t) {
		return null; //Temp
	}
	
	public void changePlayerOrder(Player[] playerOrder) {
		
	}
	
	public boolean isOver() {
		return false; //Temp
	}
	
	//Sets player of FloorTile at given location
	//As in finds FloorTile at location and then sets the tile's player pointer
	public void setPlayer(Player p, Location l) {
		
	}
}
