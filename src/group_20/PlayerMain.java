package group_20;

import java.util.ArrayList;

public class PlayerMain {
	public static void main(String[] args) {
		SilkBag sb = new SilkBag();
		Board b = new Board(1,1,1,sb);
		Location l = new Location(0,0);
		ArrayList<ActionTile> i = null;
		Player p = new Player(b,sb,l);
		
//		p.takeTurn();
		
//		l.print();
//		l.update(Direction.NORTH);
//		l.print();
		
		LocationList ls = new LocationList();
		ls.add(new Location(0,0));
		ls.add(new Location(1,0));
		ls.add(new Location(1,1));
		ls.add(new Location(1,2));
		ls.add(new Location(1,3));
		ls.add(new Location(2,3));
		
		p.setPreviousLocations(ls);
		
		System.out.println("Player's previous locations: " + p.getPreviousLocations().toString());
		System.out.println("First element is: " + p.getPreviousLocations().getFirst());
		System.out.println("Second element is: " + p.getPreviousLocations().getSecond());
		System.out.println("Third element is: " + p.getPreviousLocations().getThird());
	}
}
