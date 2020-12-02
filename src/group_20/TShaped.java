package group_20;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class TShaped extends FloorTile {
	
	public TShaped(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
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
//		g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
//		g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//	}
	
//	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
//        if (getOrientation() == 0) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
//            g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//        } else if (getOrientation() == 180) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth/2, y + tileWidth);
//            g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//        } else if (getOrientation() == 270) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
//        } else if (getOrientation() == 90) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
//            g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//        }
//    }
	
	public void draw(int x, int y, GraphicsContext gc, int tileWidth) {
		int varOrientation = 0;
		switch (this.getOrientation()) {
		case 0:
			varOrientation = 0+180;
			break;
		case 90:
			varOrientation = 90+180;
			break;
		case 180:
			varOrientation = 180+180;
			break;
		case 270:
			varOrientation = 270+180;
			break;
		default:
			varOrientation = 0+180;
		}
		
		ImageView iv = new ImageView(sprite);
		iv.setRotate(varOrientation);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);
	}

	public static void main(String args[]) {
		TShaped t1 = new TShaped (5, true, 90, false, false, new Location (0,0), null, "TShaped");
		System.out.println(t1.toString());
		System.out.println(t1.isValidMove(Direction.NORTH));
		System.out.println(t1.isValidMove(Direction.SOUTH));
		System.out.println(t1.isValidMove(Direction.EAST));
		System.out.println(t1.isValidMove(Direction.WEST));
	}
	
	public void loadSprite() {
		Image image = null;
		try {
			image = new Image(new FileInputStream("T_Tile.png"),this.TILE_WIDTH, this.TILE_WIDTH,true,true);
		} catch (IOException e) {
		}
		this.sprite = image;
	}
}
