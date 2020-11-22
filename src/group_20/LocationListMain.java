package group_20;


public class LocationListMain {
	public static void main(String[] agrs) {
		LocationList ls = new LocationList();
		ls.print();
		ls.add(new Location(0,0));
		ls.print();
		ls.add(new Location(1,0));
		ls.print();
		ls.add(new Location(1,1));
		ls.print();
		ls.add(new Location(1,2));
		ls.print();
		ls.add(new Location(1,3));
		ls.print();
		ls.add(new Location(2,3));
		ls.print();
		System.out.println("First element is: " + ls.getFirst());
		System.out.println("Second element is: " + ls.getSecond());
		System.out.println("Third element is: " + ls.getThird());
	}
}
