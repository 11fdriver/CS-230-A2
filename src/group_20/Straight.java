package group_20;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Straight extends FloorTile {

	public Straight(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		super(side, isFixed, orientation, onFire, isFrozen, location, myPlayer, shapeType);
		this.loadSprite();
	}
	
	public String toString() {
		String result = "";
		result += super.toString();
		return result;
	}
	
//	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
//		g.strokeRect(x, y, tileWidth, tileWidth);
//		//g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
//		g.strokeLine(x, y+tileWidth/2, x + tileWidth, y + tileWidth/2);
//	}
	
//	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
//        if (getOrientation() == 90 || getOrientation() == 270) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
//        } else if (getOrientation() == 0 || getOrientation() == 180) {
//            g.strokeRect(x, y, tileWidth, tileWidth);
//            g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
//        }
//    }
	
	public void draw(int x, int y, GraphicsContext gc, int tileWidth) {
		//Image image = new Image("http://worldpress.org/images/maps/world_600w.jpg");
		//ImageView imageView = new ImageView(image);
		//imageView.setFitHeight(tileWidth);
		//imageView.setFitWidth(tileWidth);
		//imageView.setPreserveRatio(true);
		
		//gc.save();
		int varOrientation = 0;
		//System.out.println("Orientation = " + this.getOrientation());
		switch (this.getOrientation()) {
		case 0:
			//gc.rotate(0);
			varOrientation = 0+90;
			break;
		case 90:
			//gc.rotate(90);
			varOrientation = 90+90;
			break;
		case 180:
			//gc.rotate(180);
			varOrientation = 180+90;
			break;
		case 270:
			//gc.rotate(270);
			varOrientation = 270+90;
			break;
		default:
			//gc.rotate(0);
			varOrientation = 0+90;
		}
		
		ImageView iv = new ImageView(sprite);
		iv.setRotate(varOrientation);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);
		
		//gc.translate(x,y);
//		gc.rotate(270);
//		gc.drawImage(image,x,y);
//		gc.rotate(-270);
		//gc.translate(x, y);
		
//		gc.save();
//		Rotate r = new Rotate(90);
//		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
//		gc.drawImage(image, x, y);
//		gc.restore();
		
//		gc.save();
//		Rotate r = new Rotate(90);
//		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
//		gc.drawImage(image, x, y);
//		Rotate r2 = new Rotate(360-90);
//		gc.setTransform(r2.getMxx(), r2.getMyx(), r2.getMxy(), r2.getMyy(), r2.getTx(), r2.getTy());
//		gc.restore();
		
		//gc.save();
//		gc.rotate(90);
//		gc.drawImage(image, x, y);
//		gc.rotate(-90);
		//gc.restore();
	}

	public static void main(String args[]) {
		Straight s1 = new Straight (5, true, 90, false, false, new Location (0,0), null, "Straight");
		System.out.println(s1.toString());
		System.out.println(s1.isValidMove(Direction.NORTH));
		System.out.println(s1.isValidMove(Direction.SOUTH));
		System.out.println(s1.isValidMove(Direction.EAST));
		System.out.println(s1.isValidMove(Direction.WEST));
		s1.randomizeOrientation();
		System.out.println(s1.toString());
	}
	
	public void loadSprite() {
		Image image = null;
		try {
			image = new Image(new FileInputStream("straight_tile.png"),Main.TILE_WIDTH, Main.TILE_WIDTH,true,true);
		} catch (IOException e) {
		}
		this.sprite = image;
	}
}
