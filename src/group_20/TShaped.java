import javafx.scene.canvas.GraphicsContext;

public class TShaped extends FloorTile {
	private String shapeType;
	
	public TShaped(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
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
					return true;
				} else {
					return true;
				}
			case SOUTH:
				if (getOrientation() == 0) {
					return true;
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
					return true;
				}
			case WEST:
				if (getOrientation() == 0) {
					return true;
				} else if (getOrientation() == 90) {
					return true;
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
		if (getOrientation() == 180) {
			g.strokeRect(x, y, tileWidth, tileWidth);
			g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
			g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
		} else if (getOrientation() == 0) {
			g.strokeRect(x, y, tileWidth, tileWidth);
			g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth/2, y + tileWidth);
			g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
		} else if (getOrientation() == 90) {
			g.strokeRect(x, y, tileWidth, tileWidth);
			g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
			g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
		} else if (getOrientation() == 270) {
			g.strokeRect(x, y, tileWidth, tileWidth);
			g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
			g.strokeLine(x + tileWidth/2, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
		}
	}

	public static void main(String args[]) {
		TShaped t1 = new TShaped (5, true, 90, false, false, new Location (0,0), null, "TShaped");
		System.out.println(t1.toString());
		System.out.println(t1.isValidMove(Direction.NORTH));
		System.out.println(t1.isValidMove(Direction.SOUTH));
		System.out.println(t1.isValidMove(Direction.EAST));
		System.out.println(t1.isValidMove(Direction.WEST));
	}
	
}
