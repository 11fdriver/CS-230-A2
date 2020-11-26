import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Straight extends FloorTile {
	private String shapeType;
	
	public Straight(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		super(side, isFixed, orientation, onFire, isFrozen, location, myPlayer);
		this.setShapeType(shapeType);
	}
	
	public String toString() {
		String result = "";
		result += "Its shape is " + getShapeType() + "\n";
		result += super.toString();
		return result;
	}
	
	/**
	 * @return the shapeType
	 */
	public String getShapeType() {
		return shapeType;
	}

	/**
	 * @param shapeType the shapeType to set
	 */
	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public boolean isValidMove(Direction d) {
		switch (d) {
			case NORTH:
				if (getOrientation() == 0) {
					return false;
				} else if (getOrientation() == 90) {
					return true;
				} else if (getOrientation() == 180) {
					return false;
				} else {
					return true;
				}
			case SOUTH:
				if (getOrientation() == 0) {
					return false;
				} else if (getOrientation() == 90) {
					return true;
				} else if (getOrientation() == 180) {
					return false;
				} else {
					return true;
				}
			case EAST:
				if (getOrientation() == 0) {
					return true;
				} else if (getOrientation() == 90) {
					return false;
				} else if (getOrientation() == 180) {
					return true;
				} else {
					return false;
				}
			case WEST:
				if (getOrientation() == 0) {
					return true;
				} else if (getOrientation() == 90) {
					return false;
				} else if (getOrientation() == 180) {
					return true;
				} else {
					return false;
				}
			default:
				return false;
		} 
	}
	
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		if (getOrientation() == 90 || getOrientation() == 270) {
			g.strokeRect(x, y, tileWidth, tileWidth);
			g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
		} else if (getOrientation() == 0 || getOrientation() == 180) {
			g.strokeRect(x, y, tileWidth, tileWidth);
			g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
		}
	}
	
	public void drawImage(BufferedImage img) throws IOException {
		img = ImageIO.read(new File("StraightTile.png"));
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
	
}
