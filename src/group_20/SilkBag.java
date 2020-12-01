package group_20;

import java.util.ArrayList;
import java.util.Random;

/**
 * SilkBag is a statically-interacting class that can:
 * <br>
 * - Provide a random tile
 * <br>
 * - Re-add a tile from the {@link} Board
 * @author fd
 *
 */
public class SilkBag implements Saveable {
	/**
	 * Container for Tiles. This is static, no constructor needed.
	 */
	private static final ArrayList<Tile> TILES = new ArrayList<Tile>();
	
	/**
	 * Random number generator.
	 */
	private static final Random RAND = new Random();
	
	/**
	 * @return A randomly-chosen {@link Tile}.
	 */
	public static Tile removeTile() {
		return TILES.remove(RAND.nextInt(TILES.size() - 1));
	}
	
	/**
	 * Used either when constructing the SilkBag,
	 * <br>
	 * or returning a tile after inserting to {@link Board}.
	 * @param t A {@link Tile}
	 */
	public static void addTile(Tile t) {
		TILES.add(t);
	}

	@Override
	public String saveFormat() {
		String str = "[SilkBag]";
		for (Tile t : TILES) {
			str += ", " + t.saveFormat();
		}
		return str + "[/SilkBag]";
	}
}