package group_20;

public class ActionTile extends Tile {
	/**
	 * Action stored in ActionTile.
	 */
	private final Action ACTION;
	
	/**
	 * User-storable and playable Tile containing an action.
	 * @param a An action
	 */
	ActionTile (Action a) {
		this.ACTION = a;
	}
	
	/**
	 * Use the action associated with an ActionTile.
	 * @param p Player that's playing the tile
	 * @param b Board
	 */
	public void play(Player p, Board b) {
		ACTION.apply(p, b);
	}
	
	@Override
	public void draw(Location loc) {
		// TODO Draw tile
		ACTION.draw(loc);
	}

	@Override
	public String saveFormat() {
		return "ATile:" + ACTION.saveFormat();
	}
}
