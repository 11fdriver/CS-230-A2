package group_20;

public abstract class FloorAction extends Action {
	protected final int LIFETIME = 4;
	
	/**
	 * Get default lifetime of a FloorAction.
	 * @return Turns until action should expire.
	 */
	public int getLifetime() {
		return this.LIFETIME;
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
	public abstract boolean canShift();


}
