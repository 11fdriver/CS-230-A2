
public class LocationQueueMain {
	public static void main(String[] agrs) {
		LocationQueue q = new LocationQueue();
		q.print();
		q.enqueue(new Location(0,0));
		q.print();
		q.enqueue(new Location(0,1));
		q.print();
		q.enqueue(new Location(0,2));
		q.print();
		q.enqueue(new Location(1,2));
		q.print();
		q.enqueue(new Location(1,3));
		q.print();
		q.enqueue(new Location(0,3));
		q.print();
		System.out.println("Player was at " + q.getMiddle().toString() + " 2 moves ago");
		System.out.println("Player was at " + q.getTail().toString() + " 3 moves ago");
	}
}
