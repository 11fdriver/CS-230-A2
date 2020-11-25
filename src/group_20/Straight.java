package group_20;

import javafx.scene.canvas.GraphicsContext;

public class Straight extends FloorTile {

	public Straight(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		super(side, isFixed, orientation, onFire, isFrozen, location, myPlayer, shapeType);
	}
	
	public String toString() {
		String result = "";
		result += super.toString();
		return result;
	}
	
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
		//g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
		g.strokeLine(x, y+tileWidth/2, x + tileWidth, y + tileWidth/2);
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
