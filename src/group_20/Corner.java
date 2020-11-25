package group_20;

import javafx.scene.canvas.GraphicsContext;

public class Corner extends FloorTile {
	
	public Corner(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		super(side, isFixed, orientation, onFire, isFrozen, location, myPlayer, shapeType) ;
	}
	
	public String toString() {
		String result = "";
		result += super.toString();
		return result;
	}
	
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
		//g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
		//g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
		g.strokeLine(x + tileWidth/2, y +tileWidth/2, x + tileWidth, y + tileWidth/2);
		g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth/2, y + tileWidth);
	}
	
	public static void main(String args[]) {
		Corner c1 = new Corner (5, true, 90, false, false, new Location (0,0), null, "Corner");
		System.out.println(c1.toString());
		System.out.println(c1.isValidMove(Direction.NORTH));
		System.out.println(c1.isValidMove(Direction.SOUTH));
		System.out.println(c1.isValidMove(Direction.EAST));
		System.out.println(c1.isValidMove(Direction.WEST));
	}
}
