package group_20;

/**
 * Implements behaviour for when a tile is set on fire.
 * This prevents players from moving to the affected tile, but still allows it to be shifted.
 */
public class FireAction extends FloorAction {
	/**
	 * Whether Action allows player movement. 
	 */
	private final boolean ACCEPTS_PLAYER = false;
	
	/**
	 * Whether Action allows tile-shifting.
	 */
	private final boolean CAN_SHIFT = true;
	
	/**
	 * How many turns for each Player before FireAction expires.
	 */
	private final int LIFETIME_ROUNDS = 2;
	
	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub
	}

	@Override
	public String saveFormat() {
		return "Fire";
	}

	@Override
	public void apply(Player p, Board b) {
		FloorTile chosen = b.getTileAtClick();
		for (FloorTile tile : getTilesAround(chosen, b)) {
			if (null != tile) {
				tile.setState(this);
			}
		}
	}

	@Override
	public boolean acceptsPlayer() {
		return this.ACCEPTS_PLAYER;
	}

	@Override
	public boolean acceptsShift() {
		return this.CAN_SHIFT;
	}

	@Override
	public int getLifetime() {
		return LIFETIME_ROUNDS + Player.amount();
	}
}
