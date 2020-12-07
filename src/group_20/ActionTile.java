package group_20;

import javafx.scene.canvas.GraphicsContext;

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
	public void draw(GraphicsContext gc, int x, int y) {
		// TODO Auto-generated method stub
		//ACTION.draw(GraphicsContext gc, int x, int y);
	}

	@Override
	public String saveFormat() {
		return "ATile:" + ACTION.saveFormat();
	}
	
	public String toString() {
		return ACTION.toString();
	}
	
	public Action getAction() {
		return this.ACTION;
	}
}
