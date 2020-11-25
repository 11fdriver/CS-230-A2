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
	public SilkBag() {
		
	}
	
	/**
	 * returns floor tiles AND action tiles
	 * @return
	 */
	public Tile drawTile() {
		return new ActionTile();
	}
	
	/**
	 * Used by board class to get random floor tiles (Specifically floor tiles -> not action tiles)
	 * @return random FloorTile object from silk bag
	 */
	public FloorTile drawFloorTile() {
		Random r = new Random();
		int tileID = r.nextInt(4);
		
		//return new GoalTile();
		
		switch (tileID) {
		case 1:
			return new Straight(0,false,0,false,false,new Location(0,0),null,"Straight");
		case 2:
			return new Corner(0,false,0,false,false,new Location(0,0),null,"Corner");
		case 3:
			return new TShaped(0,false,0,false,false,new Location(0,0),null,"TShaped");
		case 4:
			return new Goal(0,false,0,false,false,new Location(0,0),null,"Goal");
		default:
			return new Straight(0,false,0,false,false,new Location(0,0),null,"Straight");
		}
	}
	//===========================================================================================================//
}