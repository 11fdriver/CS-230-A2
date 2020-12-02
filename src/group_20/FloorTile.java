package group_20;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Class for a Tile that can be placed onto the {@link Board}.
 */
public class FloorTile extends Tile implements Subscriber {
	/**
	 * ArrayList containing exit/entry points.
	 */
	private final ArrayList<Direction> DIRECTIONS;
	
	/**
	 * Location of tile.
	 */
	private Location location;
	
	/**
	 * Player that is currently on tile.
	 */
	private Player player;
	
	/**
	 * Set when a FloorAction is used on a FloorTile
	 */
	private FloorAction state;
	
	/**
	 * The length of time until the state expires.
	 */
	private int stateLifetime;
	
	/**
	 * Construct new FloorTile.
	 * <br>
	 * Will <b>not</b> set a non-player state when there is already a Player.
	 * @param directions	Directions which a player can enter or exit a tile via.
	 * @param location		Location at which this tile is on the {@link Board}.
	 * @param player		The {@link Player} on this tile.
	 * @param state			The current state of the tile, see {@link FloorAction}
	 * @param lifetime		Turns left until {@code state} expires
	 */
	public FloorTile(ArrayList<Direction> directions, Location location, Player player, FloorAction state, int lifetime) {
		this.DIRECTIONS = directions;
		this.location = location;
		this.player = player;
		if (null == player || (null != state && state.acceptsPlayer())) {
			this.state = state;
			TurnNotifier.addSubscriber(this);
			this.stateLifetime = lifetime;
		}
	}
	
	/**
	 * Whether a player can move to this tile via the given Direction,
	 * assuming that they can exit from their current tile.
	 * <br>
	 * A {@link Player} cannot move to a tile if a {@link FloorAction} prevents it, or if it already occupied.
	 * @param d Direction from which the Player would like to move
	 * @return True if a Player can move to this tile
	 */
	public boolean canEnterFrom(Direction d) {
		return (null == state || state.acceptsPlayer()) && null == player && DIRECTIONS.contains(d);
	}
	
	/**
	 * Whether a player can exit in the desired {@link Direction},
	 * assuming that they can enter to the next tile.
	 * @param d Direction to which the Player would like to move
	 * @return True if a Player can move from this tile
	 */
	public boolean canExitTo(Direction d) {
		return DIRECTIONS.contains(d);
	}
	
	/**
	 * Whether a FloorTile can be shifted across the {@link Board}.
	 * @return True if shifting is possible
	 */
	public boolean canShift() {
		return (null == state || state.acceptsShift());
	}
	
	/**
	 * Allows a FloorAction to affect the FloorTile, subscribing to the TurnNotifier.
	 * <br>
	 * Will <b>not</b> set a non-player state when there is already a Player.
	 * @param action Action being used on this FloorTile
	 */
	public void setState(FloorAction action) {
		if (null != player && !action.acceptsPlayer()) { // Don't set non-player state if already a player
			return;
		}
		this.state = action;
		this.stateLifetime = action.getLifetime();
		TurnNotifier.addSubscriber(this);
	}
	
	/**
	 * @return FloorTile's location.
	 */
	public Location getLocation() {
		return this.location.copy();
	}
	
	/**
	 * @param loc New location for FloorTile
	 */
	public void setLocation(Location loc) {
		this.location = loc;
	}
	
	/**
	 * Sets Player onto FloorTile.
	 * @param p Player to put onto FloorTile. Null if unsetting.
	 */
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	/**
	 * update(), in this context, decrements the remaining lifetime of the state,
	 * unsubscribing from the notifier, and resetting the state, when at 0.
	 */
	@Override
	public void update() {
		if (0 == --stateLifetime) {
			this.state = null;
			TurnNotifier.delSubscriber(this);
		}
	}

	@Override
	public void draw(Location loc) {
		boolean n = DIRECTIONS.contains(Direction.NORTH);
		boolean e = DIRECTIONS.contains(Direction.EAST);
		boolean s = DIRECTIONS.contains(Direction.SOUTH);
		boolean w = DIRECTIONS.contains(Direction.WEST);
		switch (DIRECTIONS.size()) {
		case 2:
			if (n && e) {
				// Draw Corner from North to East
			} else if (n && s) {
				// Draw Straight from East to West
			} else if (n && w) {
				// Draw Corner from North to West
			} else if (e && s) {
				// Draw Corner from East to South
			} else if (e && w) {
				// Draw Straight from East to West
			} else if (s && w) {
				// Draw Corner from South to West
			}

		case 3:
			if (n && e && s) {
				// Draw T-Shaped with North, East, South
			} else if (e && s && w) {
				// Draw T-Shaped with East, South, West
			} else if (s && w && n) {
				// Draw T-Shaped with South, West, North
			} else if (w && n && e) {
				// Draw T-Shaped with West, North, East
			}
		case 4:
			; // Draw goal;
		}
	}
	
	@Override
	public String saveFormat() {
		// TODO Auto-generated method stub
		String str = "FT{";
		for (Direction d : DIRECTIONS) {
			str += d.toString();
			str += ",";
		}
		str += // player.saveFormat() + ";" + // TODO: Player needs to implement Saveable
			state.saveFormat() + ";" +
			String.valueOf(stateLifetime) + "}";
		return str ;
	}
	
  // TODO: Comment me!
	public void highlight(int x, int y, GraphicsContext gc, int tileWidth) {
		gc.setStroke(Color.ANTIQUEWHITE);
		gc.strokeOval(x, y, tileWidth, tileWidth);
		gc.setStroke(Color.BLACK);
	}
  
  @Override
	public String toString() {
		// TODO: Write toString()
    return null;
	}
}
