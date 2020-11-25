package group_20;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Collections;

public class FloorTile extends Tile {
	private int side; 
	private boolean isFixed ;
	private int orientation;
	private boolean onFire;
	private boolean isFrozen;
	private Location location;
	private Player myPlayer;
	private int defSide;
	private boolean defFixed;
	private int defOrientation;
	private boolean defFire;
	private boolean defFrozen;
	private Location defLocation;
	private Player defPlayer;
	private String shapeType;
	private String defType;
	ArrayList <Integer> degrees = new ArrayList <Integer>();
	
	public FloorTile(int side, boolean isFixed, int orientation, boolean onFire, boolean isFrozen, Location location, Player myPlayer, String shapeType) {
		this.setSide(side);
		this.setDefSide(side); 
		this.setFixed(isFixed);
		this.setDefFixed(isFixed); 
		this.setOrientation(orientation);
		this.setDefOrientation(orientation); 
		this.setOnFire(onFire);
		this.setDefFire(onFire); 
		this.setFrozen(isFrozen);
		this.setDefFrozen(isFrozen);
		this.setLocation(location);
		this.setDefLocation(location);
		this.setMyPlayer(myPlayer);
		this.setDefPlayer(myPlayer);
		this.setShapeType(shapeType);
		this.setDefType(shapeType);
	}
	
	public String toString() {
		String result = "";
		result += "This is a " + getShapeType() +  " floor tile" + "\n";
		result += "Its side length is " + getSide() + "\n";
		if (getIsFixed() == true) {
			result += "It is fixed" + "\n";
		} else {
			result += "It is not fixed" + "\n";
		} 
		if (getIsFrozen() == true) {
			result += "It is frozen" + "\n";
		} else {
			result += "It is not frozen" + "\n";
		} 
		if (isOnFire() == true) {
			result += "It is on fire" + "\n";
		} else {
			result += "It is not on fire" + "\n";
		} 
		result += "Its orientation is " + getOrientation() + " degrees" + "\n";
		result += "Its position on the board is " + getLocation().getX() + "," + getLocation().getY() + "\n";
		if (getMyPlayer() == null) {
			result += "It does not contain a player" + "\n";
		} else {
			result += "It contains a player" + "\n";
		}
		return result;
	}

	/**
	 * @return the side
	 */
	public int getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(int side) {
		this.side = side;
	}
	
	/**
	 * @return the isFixed
	 */
	public boolean getIsFixed() {
		return isFixed;
	}

	/**
	 * @param isFixed the isFixed to set
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	/**
	 * @return the orientation
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the onFire
	 */
	public boolean isOnFire() {
		return onFire;
	}

	/**
	 * @param onFire the onFire to set
	 */
	public void setOnFire(boolean onFire) {
		this.onFire = onFire;
	}

	/**
	 * @return the isFrozen
	 */
	public boolean getIsFrozen() {
		return isFrozen;
	}

	/**
	 * @param isFrozen the isFrozen to set
	 */
	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	/**
	 * @return the defSide
	 */
	public int getDefSide() {
		return defSide;
	}

	/**
	 * @param defSide the defSide to set
	 */
	public void setDefSide(int defSide) {
		this.defSide = defSide;
	}

	/**
	 * @return the defFixed
	 */
	public boolean isDefFixed() {
		return defFixed;
	}

	/**
	 * @param defFixed the defFixed to set
	 */
	public void setDefFixed(boolean defFixed) {
		this.defFixed = defFixed;
	}

	/**
	 * @return the defOrientation
	 */
	public int getDefOrientation() {
		return defOrientation;
	}

	/**
	 * @param defOrientation the defOrientation to set
	 */
	public void setDefOrientation(int defOrientation) {
		this.defOrientation = defOrientation;
	}

	/**
	 * @return the defFire
	 */
	public boolean isDefFire() {
		return defFire;
	}

	/**
	 * @param defFire the defFire to set
	 */
	public void setDefFire(boolean defFire) {
		this.defFire = defFire;
	}

	/**
	 * @return the defFrozen
	 */
	public boolean isDefFrozen() {
		return defFrozen;
	}

	/**
	 * @param defFrozen the defFrozen to set
	 */
	public void setDefFrozen(boolean defFrozen) {
		this.defFrozen = defFrozen;
	}

	/**
	 * @return the defLocation
	 */
	public Location getDefLocation() {
		return defLocation;
	}

	/**
	 * @param defLocation the defLocation to set
	 */
	public void setDefLocation(Location defLocation) {
		this.defLocation = defLocation;
	}

	/**
	 * @return the defPlayer
	 */
	public Player getDefPlayer() {
		return defPlayer;
	}

	/**
	 * @param defPlayer the defPlayer to set
	 */
	public void setDefPlayer(Player defPlayer) {
		this.defPlayer = defPlayer;
	}
	
	/**
	 * @return the Location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param Location the Location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the myPlayer
	 */
	public Player getMyPlayer() {
		return myPlayer;
	}

	/**
	 * @param myPlayer the myPlayer to set
	 */
	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
	}
	
	public boolean hasPlayer() {
		if (getMyPlayer() == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void randomizeOrientation() {
		degrees.add(0);
		degrees.add(90);
		degrees.add(180);
		degrees.add(270);
		Collections.shuffle(degrees);
		this.setOrientation(degrees.get(0));
	}
	
	public void notifyMe() {
		if (getIsFrozen() == true) {
			setFrozen(false);
		} 
		if (isOnFire() == true) {
			setOnFire(false);
		}
	}
	
	public void reset() {
		this.setSide(defSide);
		this.setFixed(defFixed);
		this.setOrientation(defOrientation);
		this.setOnFire(defFire);
		this.setFrozen(defFrozen);
		this.setLocation(defLocation);
		this.setMyPlayer(defPlayer);
		this.setShapeType(defType);
	}

	public boolean isValidMove(Direction d) {
		switch (getShapeType()) {
			case "Straight": 
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
			case "Corner": 	
				switch (d) {
					case NORTH:
						if (getOrientation() == 0) {
							return false;
						} else if (getOrientation() == 90) {
							return false;
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
							return false;
						}
					case EAST:
						if (getOrientation() == 0) {
							return true;
						} else if (getOrientation() == 90) {
							return false;
						} else if (getOrientation() == 180) {
							return false;
						} else {
							return true;
						}
					case WEST:
						if (getOrientation() == 0) {
							return false;
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
			case "TShaped":
				switch (d) {
					case NORTH:
						if (getOrientation() == 0) {
							return true;
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
							return true;
						} else {
							return true;
						}
					case EAST:
						if (getOrientation() == 0) {
							return true;
						} else if (getOrientation() == 90) {
							return true;
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
							return true;
						}
					default:
						return false;
				} 
			case "Goal":
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
			default: 
				return false;
		}
	}
	
	public static void main(String args[]) {
		FloorTile f1 = new FloorTile (5, true, 90, false, false, new Location (0,0), new Player (new Location(0,0), false, false, "Jeff"), "Straight");
		FloorTile f2 = new FloorTile (5, false, 90, true, true, new Location (1,2), null, "Corner");
		System.out.println(f1.toString());
		f1.randomizeOrientation();
		System.out.println(f1.getOrientation());
		f1.reset();
		System.out.println(f1.toString());
		System.out.println(f2.toString());
		f2.notifyMe();
		f2.randomizeOrientation();
		System.out.println(f2.toString());
		System.out.println(f1.isValidMove(Direction.NORTH));
		System.out.println(f2.isValidMove(Direction.SOUTH));
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

	/**
	 * @return the defType
	 */
	public String getDefType() {
		return defType;
	}

	/**
	 * @param defType the defType to set
	 */
	public void setDefType(String defType) {
		this.defType = defType;
	}

	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String saveFormat() {
		// TODO Auto-generated method stub
		return null;
	}
}
