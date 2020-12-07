package group_20;

public class IceAction extends FloorAction {
	/**
	 * Whether Action allows player movement. 
	 */
	private final boolean ACCEPTS_PLAYER = false;
	
	/**
	 * Whether Action allows tile-shifting.
	 */
	private final boolean CAN_SHIFT = true;

	/**
	 * How many turns for each player before IceAction expires.
	 */
	private final int LIFETIME_ROUNDS = 1;
	
	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub
	}

	@Override
	public String saveFormat() {
		return "Ice";
	}

	
	@Override
	public void apply(Player p, Board b) {
		FloorTile chosen = b.getTileAtClick();
		for (FloorTile tile : getTilesAround(chosen, b)) {
			if (null == tile)
				continue;
			tile.setState(this);
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
		return LIFETIME_ROUNDS * Player.amount();
	}
}
