package group_20;

public abstract class FloorAction extends Action {
	private final int LIFETIME = 4;
	
	/**
	 * Get default lifetime of a FloorAction.
	 * @return Turns until action should expire.
	 */
	public int getLifetime() {
		return this.LIFETIME;
	}
	
	protected FloorTile[] getTilesAround(FloorTile f, Board b) {
		Location loc = f.getLocation();
		int xoffset = loc.getX();
		int yoffset = loc.getY();
		FloorTile[] result = new FloorTile[9];
		int pos = 0;
		for (int i = -1; i <= 1; i++, pos++) {
			for (int j = -1; j <= 1; j++, pos++) {
				result[pos] = b.getTileAt(new Location(xoffset + i, yoffset +j));
			}
		}
		result[4] = null; // Don't include center tile
		return result;
	}
	
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
