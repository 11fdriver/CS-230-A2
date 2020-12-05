package group_20;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Straight extends FloorTile {

	public Straight(int TILE_WIDTH, String spriteFileLocation, ArrayList<Direction> directions, Direction orientation, Location location, Player player, FloorAction state, int lifetime, boolean isFixed) {
		super(TILE_WIDTH, spriteFileLocation, directions, orientation, location, player, state, lifetime, isFixed);
	}
	
	public String toString() {
		String result = "This is a straight tile!\n";
		result += super.toString();
		return result;
	}
	
	public void draw(GraphicsContext gc, int x, int y) {
		int varOrientation = 0;
		switch (this.getOrientation()) {
		case NORTH:
			varOrientation = 90;
			break;
		case EAST:
			varOrientation = 180;
			break;
		case SOUTH:
			varOrientation = 270;
			break;
		case WEST:
			varOrientation = 0;
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
