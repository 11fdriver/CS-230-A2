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
	
	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub
	}

	@Override
	public String saveFormat() {
		return "IceAction";
	}

	@Override
	public void apply(Player p, Board b) {
		// TODO: Here we need to
		// - Get Player input for a center tile.
		// - Pass surrounding tiles 'this'.
	}

	@Override
	public boolean acceptsPlayer() {
		return this.ACCEPTS_PLAYER;
	}

	@Override
	public boolean acceptsShift() {
		return this.CAN_SHIFT;
	}
	
	public String toString() {
		return "This is an ice action tile";
	}
}
