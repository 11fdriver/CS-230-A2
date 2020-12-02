package group_20;

import java.util.ArrayList;
import java.util.Random;

public class SilkBag {
	int numberOfTiles;
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
		Direction orientation;
		
		switch (orientationID) {
		case 1:
			orientation = Direction.NORTH;
			break;
		case 2:
			orientation = Direction.EAST;
			break;
		case 3:
			orientation = Direction.SOUTH;
			break;
		case 4:
			orientation = Direction.WEST;
			break;
		default:
			orientation = Direction.NORTH;
		}
		
		//Random orientation
		switch (tileID) {
		case 1:
			return new Straight(TILE_WIDTH, "straight_tile_with_alligners.png", null, orientation, null, null, null, 0);
		case 2:
			return new Corner(TILE_WIDTH, "Corner_Tile_with_alligners.png", null, orientation, null, null, null, 0);
		case 3:
			return new TShaped(TILE_WIDTH, "T_Tile_With_alligners.png", null, orientation, null, null, null, 0);
		case 4:
			return new Goal(TILE_WIDTH, "Goal_Tile_Animated-with-carpet-noise.gif", null, orientation, null, null, null, 0);
		default:
			return new Straight(TILE_WIDTH, "straight_tile_with_alligners.png", null, orientation, null, null, null, 0);
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