import java.util.Random;

public class SilkBag {
	//private Tile[] contents;
	
	public SilkBag() {
		
	}
	
	/**
	 * returns floor tiles AND action tiles
	 * @return
	 */
	public Tile drawTile() {
		return new Tile();
	}
	
	/**
	 * Used by board class to get random floor tiles (Specifically floor tiles -> not action tiles)
	 * @return random FloorTile object from silk bag
	 */
	public FloorTile drawFLoorTile() {
		Random r = new Random();
		int tileID = r.nextInt(4);
		
		//return new GoalTile();
		
		switch (tileID) {
		case 1:
			return new StraightTile();
		case 2:
			return new CornerTile();
		case 3:
			return new TShapedTile();
		case 4:
			return new GoalTile();
		default:
			return new StraightTile();
		}
	}
}
