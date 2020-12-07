package group_20;


public class LocationList {
	/**
	 * List of 3 locations
	 */
	private Location[] list;
	
	/**
	 * Constructs an empty list of 3 locations
	 */
	public LocationList() {
		this.list = new Location[3];
	}
	
	/**
	 * Adds a location to the list, it becomes the first element and the other elements shuffle down by one
	 * @param l
	 */
	public void add(Location l) {
		this.list[0] = this.list[1];
		this.list[1] = this.list[2];
		this.list[2] = l;
	}
	
	/**
	 * Returns first location in list
	 * @return First location in list
	 */
	public Location getFirst() {
		return this.list[2];
	}
	
	/**
	 * Returns second location in list
	 * @return Second location in list
	 */
	public Location getSecond() {
		return this.list[1];
	}
	
	 /**
	  * Returns third location in list
	  * @return Third location in list
	  */
	public Location getThird() {
		return this.list[0];
	}
	
	/**
	 * Prints list to screen
	 */
	public void print() {
		System.out.println(this.toString());;
	}
	
	/**
	 * Converts list into a readable string
	 * @return Location list as readable string
	 */
	public String toString() {
		String headS = "null";
		String middleS = "null";
		String tailS = "null";
		
		if (this.getFirst() != null) {
			headS = String.valueOf(this.getFirst());
		}
		if (this.getSecond() != null) {
			middleS = String.valueOf(this.getSecond());
		}
		if (this.getThird() != null) {
			tailS = String.valueOf(this.getThird());
		}
		
		return ("[" + tailS + "," + middleS + "," + headS + "]");
	}
}
