import java.util.ArrayList;

public class SilkBag {
	
	int numberOfTiles;
	
	public SilkBag(ArrayList<Tile> tiles) {
		numberOfTiles = (tiles.length);	
	}
	
	public boolean isEmpty() {
		if (numberOfTiles == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public FloorTile drawFloorTile() {
		x = math.random(0,2);
		
		if (x = 0) {
			return Straight;
		}
		if (x = 1) {
			return Corner;
		}
		if (x = 2) {
			return T-Shaped;
		}
	}
	
	public Tile drawTile() {
		return(tiles.get(0));
		tiles.remove(0);
		numberOfTiles = (tiles.Length);
	}
	
	public void returnTile(Tile t) {
		tiles.add (t);
		numberOfTiles = (tiles.Length);
	}
}