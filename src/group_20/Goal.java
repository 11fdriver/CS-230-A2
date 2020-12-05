

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Goal extends FloorTile {
	
	public Goal(int TILE_WIDTH, String spriteFileLocation, ArrayList<Direction> directions, Direction orientation, Location location, Player player, FloorAction state, int lifetime, boolean isFixed) {
		super(TILE_WIDTH, spriteFileLocation, directions, orientation, location, player, state, lifetime, isFixed);
	}
	
	public String toString() {
		String result = "This is a goal tile!\n";
		result += super.toString();
		return result;
	}
	
	public void draw(GraphicsContext gc, int x, int y) {
		gc.drawImage(this.getSprite(), x, y);
	}
}
