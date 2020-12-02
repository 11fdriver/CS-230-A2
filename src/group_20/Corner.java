package group_20;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Corner extends FloorTile {
	
	public Corner(int TILE_WIDTH, String spriteFileLocation, ArrayList<Direction> directions, Direction orientation, Location location, Player player, FloorAction state, int lifetime) {
		super(TILE_WIDTH, spriteFileLocation, directions, orientation, location, player, state, lifetime);
	}
	
	public String toString() {
		String result = "This is a corner tile!\n";
		result += super.toString();
		return result;
	}
	
	public void draw(int x, int y, GraphicsContext gc, int tileWidth) {
		int varOrientation = 0;
		switch (this.getOrientation()) {
		case NORTH:
			varOrientation = 0;
			break;
		case EAST:
			varOrientation = 90;
			break;
		case SOUTH:
			varOrientation = 180;
			break;
		case WEST:
			varOrientation = 270;
			break;
		default:
			varOrientation = 0;
		}
		
		ImageView iv = new ImageView(this.getSprite());
		iv.setRotate(varOrientation);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);
	}
}
