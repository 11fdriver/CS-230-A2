package group_20;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<ActionTile> inv;
	
	public Inventory() {
		this.inv = new ArrayList<ActionTile>();
	}
	
	public void add(ActionTile t) {
		this.inv.add(t);
	}
	
	public void remove(ActionTile t) {
		this.inv.remove(t);
	}
	
	public boolean contains(Action a) {
		for (int i = 0; i < this.inv.size(); i++) {
			ActionTile t = this.inv.get(i);
			if (t.getAction().getClass() == a.getClass()) {
				return true;
			}
		}
		return false;
	}
	
	public int getNum(Action a) {
		int numFound = 0;
		for (int i = 0; i < this.inv.size(); i++) {
			ActionTile t = this.inv.get(i);
			if (t.getAction().getClass() == a.getClass()) {
				numFound++;
			}
		}
		return numFound;
	}
	
	public int getLength() {
		return this.inv.size();
	}
	
	public void printString() {
		System.out.println("Inventory contains " + this.getLength() + " elements.");
		System.out.println("- " + this.getNum(new FireAction()) + " fire action tile(s).");
		System.out.println("- " + this.getNum(new IceAction()) + " ice action tile(s).");
		System.out.println("- " + this.getNum(new BacktrackAction()) + " backtrack tile(s).");
		System.out.println("- " + this.getNum(new DoubleMoveAction()) + " double move action tile(s).");
		System.out.println("Full contents of inventory: ");
		for (int i = 0; i < this.inv.size(); i++) {
			System.out.println(this.inv.get(i).toString());
		}
	}
	
	public static void main(String[] agrs) {
		Inventory i = new Inventory();
		//i.printString();
		ActionTile test = new ActionTile(new IceAction());
		i.add(new ActionTile(new FireAction()));
		i.add(new ActionTile(new IceAction()));
		i.add(new ActionTile(new FireAction()));
		i.add(test);
		i.add(test);
		i.add(new ActionTile(new DoubleMoveAction()));
		i.add(new ActionTile(new BacktrackAction()));
		i.add(new ActionTile(new DoubleMoveAction()));
		i.printString();

	}
}
