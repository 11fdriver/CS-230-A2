package group_20;

import java.util.ArrayList;
import java.util.Random;

public class SilkBag {
	
	int numberOfTiles;
	
//	public SilkBag(ArrayList<Tile> tiles) {
//		numberOfTiles = (tiles.length);	
//	}
//	
//	public boolean isEmpty() {
//		if (numberOfTiles == 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public FloorTile drawFloorTile() {
//		x = math.random(0,2);
//		
//		if (x = 0) {
//			return Straight;
//		}
//		if (x = 1) {
//			return Corner;
//		}
//		if (x = 2) {
//			return T-Shaped;
//		}
//	}
//	
//	public Tile drawTile() {
//		return(tiles.get(0));
//		tiles.remove(0);
//		numberOfTiles = (tiles.Length);
//	}
//	
//	public void returnTile(Tile t) {
//		tiles.add (t);
//		numberOfTiles = (tiles.Length);
//	}
	
//===========================================================================================================//	
	private int TILE_WIDTH;
	
	public SilkBag(int TILE_WIDTH) {
		this.TILE_WIDTH = TILE_WIDTH;
	}
	
	/**
	 * returns floor tiles AND action tiles
	 * @return
	 */
	public Tile drawTile() {
		return this.drawFloorTile();
		//return new ActionTile();
	}
	
	/**
	 * Used by board class to get random floor tiles (Specifically floor tiles -> not action tiles)
	 * @return random FloorTile object from silk bag
	 */
	public FloorTile drawFloorTile() {
		Random r = new Random();
		int tileID = r.nextInt(5);//ie x < 5
		int orientationID = r.nextInt(5);//ie x < 5
		int orientation = 0;
		
		switch (orientationID) {
		case 1:
			orientation = 0;
			break;
		case 2:
			orientation = 90;
			break;
		case 3:
			orientation = 180;
			break;
		case 4:
			orientation = 270;
			break;
		default:
			orientation = 0;
		}
		
		//Random orientation
		switch (tileID) {
		case 1:
			return new Straight(this.TILE_WIDTH,false,orientation,false,false,new Location(0,0),null,"Straight");
		case 2:
			return new Corner(this.TILE_WIDTH,false,orientation,false,false,new Location(0,0),null,"Corner");
		case 3:
			return new TShaped(this.TILE_WIDTH,false,orientation,false,false,new Location(0,0),null,"TShaped");
		case 4:
			return new Goal(this.TILE_WIDTH,false,orientation,false,false,new Location(0,0),null,"Goal");
		default:
			return new Straight(this.TILE_WIDTH,false,orientation,false,false,new Location(0,0),null,"Straight");
		}
		
		//Fixed orientation
//		switch (tileID) {
//		case 1:
//			return new Straight(0,false,0,false,false,new Location(0,0),null,"Straight");
//		case 2:
//			return new Corner(0,false,0,false,false,new Location(0,0),null,"Corner");
//		case 3:
//			return new TShaped(0,false,0,false,false,new Location(0,0),null,"TShaped");
//		case 4:
//			return new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
//		default:
//			return new Straight(0,false,0,false,false,new Location(0,0),null,"Straight");
//		}
	}
	//===========================================================================================================//
}