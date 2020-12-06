package group_20;


public class Location {
	/**
	 * X coordinate
	 */
	private int x;
	
	/**
	 * Y coordinate
	 */
	private int y;
	
	/**
	 * Constructor for Location
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Setter for X
	 * @param x New X value
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Getter for X
	 * @return X
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Setter for Y
	 * @param y New Y value
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Getter for Y
	 * @return Y
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Increases Y value by 1
	 */
	public void incrementY() {
		this.y++;
	}
	
	/**
	 * Increases X value by 1
	 */
	public void incrementX() {
		this.x++;
	}
	
	/**
	 * Decreases Y value by 1
	 */
	public void decrementY() {
		this.y--;
	}
	
	/**
	 * Decreases X value by 1
	 */
	public void decrementX() {
		this.x--;
	}
	
	/**
	 * Returns a copy of the current location moved in the given direction
	 * @param d Direction to move in
	 * @return Copy of current location moved in given direction
	 */
	public Location check(Direction d) {
		Location temp = new Location(this.x, this.y);
		temp.update(d);
		return temp;
	}
	
	/**
	 * Returns a copy of this Location object
	 * @return COpy of this object
	 */
	public Location copy() {
		Location temp = new Location(this.x, this.y);
		return temp;
	}
	
	/**
	 * Moves this location in a given direction
	 * @param d Direction to move in
	 */
	public void update(Direction d) {
		switch (d) {
		case NORTH:
			this.decrementY();
			break;
		case EAST:
			this.incrementX();
			break;
		case SOUTH:
			this.incrementY();
			break;
		case WEST:
			this.decrementX();
			break;
		}
	}
	
	/**
	 * Compares this location to given coordinates
	 * @param x X coordinate to compare against
	 * @param y Y coordinate to compare against
	 * @return if given coordinates are equivalent to this location then true, else false
	 */
	public boolean equals(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Visually prints Location as String
	 * - Just for testing
	 */
	public void print() {
		System.out.println(this.toString());
	}
	
	/**
	 * Converts the location to a readable string
	 */
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
