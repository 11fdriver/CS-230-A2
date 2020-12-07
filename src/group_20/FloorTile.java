package group_20;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for a Tile that can be placed onto the {@link Board}.
 */
public class FloorTile extends Tile implements Subscriber {
	/**
	 * 
	 */
	protected static final String SEP = File.separator;
	
	/**
	 * 
	 */
	protected static final String CONFIG_DIR_PATH = ".lairofdagon" + SEP;
	
	/**
	 * 
	 */
	protected static final String TILE_IMG_DIR_PATH = CONFIG_DIR_PATH + "img" + SEP;
	
	/**
	 * 
	 */
	protected static final String HIGHLIGHT_IMG_FILEPATH = TILE_IMG_DIR_PATH + "Circle_indicator.png";//TODO change to actual file name
	
	/**
	 * ArrayList containing exit/entry points.
	 */
	protected final ArrayList<Direction> DIRECTIONS;
	
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
	 * Orientation of tile clockwise from 12 o'clock
	 */
	private int rotation;
	
	/**
	 * Stores image of tile for drawing
	 */
	private Image sprite;
	
	/**
	 * Stores if the tile is fixed -> True if tile is fixed
	 */
	private boolean fixed;
	
	/**
	 * Stores image of sprite for highlighting tiles
	 */
	private Image highlightSprite;
	
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
	public FloorTile(ArrayList<Direction> directions, Location location, Player player, FloorAction state, int lifetime, boolean fixed) {
		this.DIRECTIONS = directions;
		this.calculateRotation();
		this.location = location;
		this.player = player;
		if (null == player || (null != state && state.acceptsPlayer())) {
			this.state = state;
			TurnNotifier.addSubscriber(this);
			this.stateLifetime = lifetime;
		}
		this.fixed = fixed;
		this.loadHighlightSprite();
	}
	
	private void calculateRotation() {
		boolean n = DIRECTIONS.contains(Direction.NORTH);
		boolean e = DIRECTIONS.contains(Direction.EAST);
		boolean s = DIRECTIONS.contains(Direction.SOUTH);
		boolean w = DIRECTIONS.contains(Direction.WEST);
		switch (DIRECTIONS.size()) {
		case 2:
			if (n && e) {
				// Draw Corner from North to East
				this.loadSprite(TILE_IMG_DIR_PATH + "Corner_Tile_with_alligners.png");
				this.rotation = 270;
			} else if (n && s) {
				// Draw Straight from North to South
				this.loadSprite(TILE_IMG_DIR_PATH + "straight_tile_with_alligners.png");
				this.rotation = 0;
			} else if (n && w) {
				// Draw Corner from North to West
				this.loadSprite(TILE_IMG_DIR_PATH + "Corner_Tile_with_alligners.png");
				this.rotation = 180;
			} else if (e && s) {
				// Draw Corner from East to South
				this.loadSprite(TILE_IMG_DIR_PATH + "Corner_Tile_with_alligners.png");
				this.rotation = 0;
			} else if (e && w) {
				// Draw Straight from East to West
				this.loadSprite(TILE_IMG_DIR_PATH + "straight_tile_with_alligners.png");
				this.rotation = 270;
			} else if (s && w) {
				// Draw Corner from South to West
				this.loadSprite(TILE_IMG_DIR_PATH + "Corner_Tile_with_alligners.png");
				this.rotation = 90;
			}
			break;
		case 3:
			if (n && e && s) {
				// Draw T-Shaped with North, East, South
				this.loadSprite(TILE_IMG_DIR_PATH + "T_Tile_With_alligners.png");
				this.rotation = 270;
			} else if (e && s && w) {
				// Draw T-Shaped with East, South, West
				this.loadSprite(TILE_IMG_DIR_PATH + "T_Tile_With_alligners.png");
				this.rotation = 0;
			} else if (s && w && n) {
				// Draw T-Shaped with South, West, North
				this.loadSprite(TILE_IMG_DIR_PATH + "T_Tile_With_alligners.png");
				this.rotation = 90;
			} else if (w && n && e) {
				// Draw T-Shaped with West, North, East
				this.loadSprite(TILE_IMG_DIR_PATH + "T_Tile_With_alligners.png");
				this.rotation = 180;
			}
			break;
		case 4:
			 // Draw goal;
			this.loadSprite(TILE_IMG_DIR_PATH + "X_tile_with_alligners.png");
			this.rotation = 0;
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
	 * Checks if tile is fixed
	 * @return True if tile is fixed
	 */
	public boolean isFixed() {
		return this.fixed;
	}
	
	/**
	 * Sets Player onto FloorTile.
	 * @param p Player to put onto FloorTile. Null if unsetting.
	 */
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	/**
	 * Getter for player
	 * @return Tile's player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Checks if tile contains a player
	 * @return True if tile contains a player
	 */
	public boolean hasPlayer() {
		return this.player != null;
	}
	
	/**
	 * Setter for sprite
	 * @param sprite New sprite
	 */
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Getter for sprite
	 * @return Sprite for this tile
	 */
	public Image getSprite() {
		return this.sprite;
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
	public void draw(GraphicsContext gc, int x, int y) {
		ImageView iv = new ImageView(this.getSprite());
		iv.setRotate(this.rotation);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);
	}
	
	/**
	 * Sets value for this.sprite to image at file location
	 * @param fileLocation File location of sprite image
	 */
	public void loadSprite(String fileLocation) {
		Image image = null;
		try {
			image = new Image(new FileInputStream(fileLocation),Main.TILE_WIDTH, Main.TILE_WIDTH,true,true);
		} catch (IOException e) {
			//TODO add code
		}
		this.setSprite(image);
	}
	
	@Override
	public String saveFormat() {
		// TODO Auto-generated method stub
		String str = "FT{";
		for (Direction d : DIRECTIONS) {
			str += d.toString();
			str += ",";
		}
		//str += // player.saveFormat() + ";" + // TODO: Player needs to implement Saveable
			//state.saveFormat() + ";" +
		if (this.hasPlayer()) {
			str += this.player.getPlayerNumber() + ",";
		} else {
			str += "null,";
		}
		
		if (this.state != null) {
			str += state.saveFormat() + ",";
		} else {
			str += "null,";
		}
		
		str += String.valueOf(stateLifetime) + "}";
		return str ;
	}
	
	/**
	 * Highlights this tile on the board
	 * @param x X coordinate to draw
	 * @param y Y coordinate to draw
	 * @param gc GraphicsContext to draw onto
	 */
	public void highlight(GraphicsContext gc, int x, int y, Image img) {
//		gc.setStroke(Color.ANTIQUEWHITE); //Can change colour if you want
//		gc.strokeOval(x, y, Main.TILE_WIDTH, Main.TILE_WIDTH);
//		gc.setStroke(Color.BLACK); //Just resets to black for now
		if (img == null) {
			gc.drawImage(this.highlightSprite, x, y);
		} else {
			gc.drawImage(img, x, y);
		}
	}
  
  @Override
	public String toString() {
		// TODO: Write toString()
    return "";
	}
  
  	public void loadHighlightSprite() {
  		Image image = null;
		try {
			image = new Image(new FileInputStream(HIGHLIGHT_IMG_FILEPATH),Main.TILE_WIDTH, Main.TILE_WIDTH,true,true);
		} catch (IOException e) {
			System.out.println("Unable to load highlight sprite");
			//TODO add code
		}
		this.highlightSprite = image;
  	}
}
