package group_20;


public class LocationList {
	/**
	 * List of 3 locations
	 */
	private Location[] list;
	
	/**
	 * 
	 */
	public LocationList() {
		this.list = new Location[3];
	}
	
	public void add(Location l) {
		this.list[0] = this.list[1];
		this.list[1] = this.list[2];
		this.list[2] = l;
	}
	
	public Location getFirst() {
		return this.list[2];
	}
	
	public Location getSecond() {
		return this.list[1];
	}
	
	public Location getThird() {
		return this.list[0];
	}
	
	public void print() {
		System.out.println(this.toString());;
	}
	
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
