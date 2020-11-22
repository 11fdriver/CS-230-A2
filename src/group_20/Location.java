package group_20;


public class Location {
	private int x;
	private int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void incrementY() {
		this.y++;
	}
	
	public void incrementX() {
		this.x++;
	}
	
	public void decrementY() {
		this.y--;
	}
	
	public void decrementX() {
		this.x--;
	}
	
	public Location check(Direction d) {
		Location temp = new Location(this.x, this.y);
		temp.update(d);
		return temp;
	}
	
	public Location copy() {
		Location temp = new Location(this.x, this.y);
		return temp;
	}
	
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
	
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
