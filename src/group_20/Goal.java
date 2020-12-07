package group_20;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Goal extends FloorTile {
	private static final String IMG_FILEPATH = TILE_IMG_DIR_PATH + "Goal_Tile_Animated-with-carpet-noise.gif";
	
	/**
	 * Used when creating a new goal tile
	 * @param location
	 */
	public Goal(Location location) {
		super(new ArrayList<Direction>(), location, null, null, 0, true);
		this.DIRECTIONS.add(Direction.NORTH);
		this.DIRECTIONS.add(Direction.EAST);
		this.DIRECTIONS.add(Direction.SOUTH);
		this.DIRECTIONS.add(Direction.WEST);
		this.loadSprite(IMG_FILEPATH);
	}
	
	/**
	 * Used when loading a goal tile
	 * @param directions
	 * @param location
	 * @param player
	 * @param state
	 * @param lifetime
	 * @param isFixed
	 */
	public Goal(ArrayList<Direction> directions, Location location, Player player, FloorAction state, int lifetime, boolean isFixed) {
		super(directions, location, player, state, lifetime, isFixed);
		this.loadSprite(IMG_FILEPATH);
	}
	
	/**
	 * Converts tile to a string
	 */
	public String toString() {
		String result = "This is a goal tile!\n";
		result += super.toString();
		return result;
	}
	
	/**
	 * Draws the tile onto a given graphics context
	 * @param gc GraphicsContext to draw onto
	 * @param x X coordinate to draw at
	 * @param y Y coordinate to draw at
	 */
	public void draw(GraphicsContext gc, int x, int y) {
		gc.drawImage(this.getSprite(), x, y);
	}
	
	/**
	 * Sets value for this.sprite to image at file location
	 * @param fileLocation File location of sprite image
	 */
	@Override
	public void loadSprite(String fileLocation) {
		Image image = null;
		try {
			image = new Image(new FileInputStream(fileLocation),Main.TILE_WIDTH, Main.TILE_WIDTH,true,true);
		} catch (IOException e) {
			//TODO add code
		}
		this.setSprite(image);
	}
}
