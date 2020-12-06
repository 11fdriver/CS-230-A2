package group_20;

public abstract class FloorAction extends Action {

	/**
	 * FloorActions often act on the circle around a selected tile;
	 * this allows the FloorAction to get that tile.
	 * @param f Central tile
	 * @param b Board upon which this acts
	 * @return Array of tiles to add 
	 */
	protected FloorTile[] getTilesAround(FloorTile f, Board b) {
		Location loc = f.getLocation();
		int xoffset = loc.getX();
		int yoffset = loc.getY();
		FloorTile[] result = new FloorTile[9];
		int pos = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				result[pos++] = b.getTileAt(new Location(xoffset + i, yoffset +j));
			}
		}
		result[4] = null; // Don't include central tile
		return result;
	}
	
	/**
	 * Get default lifetime of a FloorAction.
	 * @return Turns until action should expire.
	 */
	public abstract int getLifetime(); 
	
	/**
	 * Whether FloorAction allows a {@link Player} to go to the affected {@link FloorTile}.
	 * @return False if doesn't allow a player to move.
	 */
	public abstract boolean acceptsPlayer();
	
	/**
	 * Whether FloorAction allows affected {@link FloorTile} to shift.
	 * @return False if doesn't allow a tile to shift.
	 */
	public abstract boolean acceptsShift();
}
