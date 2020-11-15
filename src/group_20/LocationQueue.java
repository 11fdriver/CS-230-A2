
public class LocationQueue {
	private LocationQueueNode head;
	private LocationQueueNode middle;
	private LocationQueueNode tail;
	
	public LocationQueue() {
		this.head = null;
		this.middle = null;
		this.tail = null;
	}
	
	public boolean isEmpty() {
		return this.head == null 
				&& this.middle == null 
				&& this.tail == null;
	}
	
	public void enqueue(Location l) {
		this.tail = this.middle;
		this.middle = this.head;
		this.head = new LocationQueueNode(l);
	}
	
	public Location getMiddle() {
		return this.middle.getElem();
	}
	
	public Location getTail() {
		return this.tail.getElem();
	}
	
	public void print() {
		String headS = "null";
		String middleS = "null";
		String tailS = "null";
		
		if (this.head != null) {
			headS = this.head.getElem().toString();
		}
		if (this.middle != null) {
			middleS = this.middle.getElem().toString();
		}
		if (this.tail != null) {
			tailS = this.tail.getElem().toString();
		}
		
		System.out.println("[" + tailS + "," + middleS + "," + headS + "]");
	}
}
