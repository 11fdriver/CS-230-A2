
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
