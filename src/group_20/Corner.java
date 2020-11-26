package group_20;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Corner extends FloorTile {
	
	public Corner(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		super(side, isFixed, orientation, onFire, isFrozen, location, myPlayer, shapeType) ;
		this.loadSprite();
	}
	
	public String toString() {
		String result = "";
		result += super.toString();
		return result;
	}
	
//	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
//		g.strokeRect(x, y, tileWidth, tileWidth);
//		//g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
//		//g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
//		g.strokeLine(x + tileWidth/2, y +tileWidth/2, x + tileWidth, y + tileWidth/2);
//		g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth/2, y + tileWidth);
//	}
	
//	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
//        if (getOrientation() == 180) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
//        } else if (getOrientation() == 270) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//        } else if (getOrientation() == 0) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth/2, y + tileWidth);
//        } else if (getOrientation() == 90) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth/2, y + tileWidth);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
//        }
//    }
	
	public void draw(int x, int y, GraphicsContext gc, int tileWidth) {
		int varOrientation = 0;
		switch (this.getOrientation()) {
		case 0:
			varOrientation = 0;
			break;
		case 90:
			varOrientation = 90;
			break;
		case 180:
			varOrientation = 180;
			break;
		case 270:
			varOrientation = 270;
			break;
		default:
			varOrientation = 0;
		}
		
		ImageView iv = new ImageView(sprite);
		iv.setRotate(varOrientation);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);
	}
	
	public static void main(String args[]) {
		Corner c1 = new Corner (5, true, 90, false, false, new Location (0,0), null, "Corner");
		System.out.println(c1.toString());
		System.out.println(c1.isValidMove(Direction.NORTH));
		System.out.println(c1.isValidMove(Direction.SOUTH));
		System.out.println(c1.isValidMove(Direction.EAST));
		System.out.println(c1.isValidMove(Direction.WEST));
	}
	
	public void loadSprite() {
		Image image = null;
		try {
			image = new Image(new FileInputStream("Corner_Tile_with_alligners.png"),Main.TILE_WIDTH, Main.TILE_WIDTH,true,true);
		} catch (IOException e) {
		}
		this.sprite = image;
	}
}
