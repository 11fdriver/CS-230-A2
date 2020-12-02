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
		ArrayList<Direction> directions = new ArrayList<Direction>();
		switch (tileID) {
		case 1:
			switch (orientation) {
			case NORTH:
				directions.add(Direction.EAST);
				directions.add(Direction.WEST);
				break;
			case EAST:
				directions.add(Direction.NORTH);
				directions.add(Direction.SOUTH);
				break;
			case SOUTH:
				directions.add(Direction.EAST);
				directions.add(Direction.WEST);
				break;
			case WEST:
				directions.add(Direction.NORTH);
				directions.add(Direction.SOUTH);
				break;
			}
			return new Straight(TILE_WIDTH, "straight_tile_with_alligners.png", directions, orientation, null, null, null, 0);
		case 2:
			switch (orientation) {
			case NORTH:
				directions.add(Direction.EAST);
				directions.add(Direction.SOUTH);
				break;
			case EAST:
				directions.add(Direction.SOUTH);
				directions.add(Direction.WEST);
				break;
			case SOUTH:
				directions.add(Direction.WEST);
				directions.add(Direction.NORTH);
				break;
			case WEST:
				directions.add(Direction.NORTH);
				directions.add(Direction.EAST);
				break;
			}
			return new Corner(TILE_WIDTH, "Corner_Tile_with_alligners.png", directions, orientation, null, null, null, 0);
		case 3:
			switch (orientation) {
			case NORTH:
				directions.add(Direction.NORTH);
				directions.add(Direction.EAST);
				directions.add(Direction.WEST);
				break;
			case EAST:
				directions.add(Direction.NORTH);
				directions.add(Direction.EAST);
				directions.add(Direction.SOUTH);
				break;
			case SOUTH:
				directions.add(Direction.EAST);
				directions.add(Direction.SOUTH);
				directions.add(Direction.WEST);
				break;
			case WEST:
				directions.add(Direction.NORTH);
				directions.add(Direction.SOUTH);
				directions.add(Direction.WEST);
				break;
			}
			return new TShaped(TILE_WIDTH, "T_Tile_With_alligners.png", directions, orientation, null, null, null, 0);
		case 4:
			directions.add(Direction.NORTH);
			directions.add(Direction.EAST);
			directions.add(Direction.SOUTH);
			directions.add(Direction.WEST);
			return new Goal(TILE_WIDTH, "Goal_Tile_Animated-with-carpet-noise.gif", directions, orientation, null, null, null, 0);
		default:
			switch (orientation) {
			case NORTH:
				directions.add(Direction.EAST);
				directions.add(Direction.WEST);
				break;
			case EAST:
				directions.add(Direction.NORTH);
				directions.add(Direction.SOUTH);
				break;
			case SOUTH:
				directions.add(Direction.EAST);
				directions.add(Direction.WEST);
				break;
			case WEST:
				directions.add(Direction.NORTH);
				directions.add(Direction.SOUTH);
				break;
			}
			return new Straight(TILE_WIDTH, "straight_tile_with_alligners.png", directions, orientation, null, null, null, 0);
		}
	}
	
	public static void main(String[] args) {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		directions.add(Direction.SOUTH);
		directions.add(Direction.WEST);
		Direction orientation = Direction.EAST;
		
		FloorTile t = new Straight(120, "straight_tile_with_alligners.png", directions, orientation, null, null, null, 0);
		System.out.println(t.getOrientation());
		switch (t.getOrientation()) {
		case NORTH:
			System.out.println("Facing NORTH");
			break;
		case EAST:
			System.out.println("Facing EAST");
			break;
		case SOUTH:
			System.out.println("Facing SOUTH");
			break;
		case WEST:
			System.out.println("Facing WEST");
			break;
		}
	}
	//===========================================================================================================//
}