import javafx.scene.canvas.GraphicsContext;

public class Goal extends FloorTile {
	private String shapeType;
	
	public Goal(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		super(side, isFixed, orientation, onFire, isFrozen, location, myPlayer) ;
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
				return true;
			case SOUTH:
				return true;
			case EAST:
				return true;
			case WEST:
				return true;
			default:
				return false;
		} 
	}
	
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
		g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
		g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
	}
	
	public static void main(String args[]) {
		Goal g1 = new Goal (5, true, 90, false, false, new Location (0,0), null, "Goal");
		System.out.println(g1.toString());
		System.out.println(g1.isValidMove(Direction.NORTH));
		System.out.println(g1.isValidMove(Direction.SOUTH));
		System.out.println(g1.isValidMove(Direction.EAST));
		System.out.println(g1.isValidMove(Direction.WEST));
	}
	
}
