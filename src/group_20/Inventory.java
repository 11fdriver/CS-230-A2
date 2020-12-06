package group_20;

import java.util.ArrayList;

public class Inventory{
	private ArrayList<ActionTile> inv;
	
	/**
	 * Constructor for inventory -> Creates an empty inventory
	 */
	public Inventory() {
		this.inv = new ArrayList<ActionTile>();
	}
	
	/**
	 * Constructor for inventory
	 * @param inventory As ArrayList to create inventory from
	 */
	public Inventory(ArrayList<ActionTile> inventory) {
		this.inv = inventory;
	}
	
	/**
	 * Adds an action tile to the inventory
	 * @param t Action tile to add to the inventory
	 */
	public void add(ActionTile t) {
		this.inv.add(t);
	}
	
	/**
	 * Gets the first action tile from inventory that matches the given action
	 * @param a Action to search for
	 * @return First action tile matching the given action
	 */
	public ActionTile getActionTile(Action a) {
		for (int i = 0; i < this.inv.size(); i++) {
			ActionTile t = this.inv.get(i);
			if (t.getAction().getClass() == a.getClass()) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Removes an action tile from the inventory
	 * @param t Action tile to remove from the inventory
	 */
	public void remove(ActionTile t) {
		this.inv.remove(t);
	}
	
	/**
	 * Removes the first action tile from the inventory that matches the given action
	 * @param a Action to search for
	 */
	public void remove(Action a) {
		for (int i = 0; i < this.inv.size(); i++) {
			ActionTile t = this.inv.get(i);
			if (t.getAction().getClass() == a.getClass()) {
				this.remove(t);
			}
		}
	}
	
	/**
	 * Checks if inventory contains any action tiles matching the given type
	 * @param a Action to search for
	 * @return True if inventory contains any action tiles matching the given action
	 */
	public boolean contains(Action a) {
		for (int i = 0; i < this.inv.size(); i++) {
			ActionTile t = this.inv.get(i);
			if (t.getAction().getClass() == a.getClass()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Counts the number of action tiles in the inventory matching a given action
	 * @param a Action to search for
	 * @return Number of action tiles in inventory matching given action
	 */
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
	
	/**
	 * Returns length of inventory
	 * @return Length of inventory
	 */
	public int getLength() {
		return this.inv.size();
	}
	
	/**
	 * Checks if list is empty
	 * @return True if list is empty
	 */
	public boolean isEmpty() {
		return this.inv.isEmpty();
	}
	
	/**
	 * Prints the inventory to the screen as readable string
	 */
	public void printString() {
		if (this.isEmpty()) {
			System.out.println("Inventory is empty.");
		} else {
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
	}
	
	/**
	 * Converts the inventory to a formatted string for saving to text file
	 * @return Formatted string
	 */
	public String saveFormat() {
		if (this.isEmpty()) {
			return "{Inventory,empty,Inventory}";
		} else {
			String str = "{Inventory,";
			for (int i = 0; i < this.inv.size()-1; i++) {
				str += this.inv.get(i).saveFormat() + ",";
			}
			str += this.inv.get(this.inv.size()-1).saveFormat() + ",Inventory}";
			return str;
		}
	}
	
	/**
	 * Just for testing
	 * @param agrs
	 */
	public static void main(String[] agrs) {
		Inventory i = new Inventory();
		i.printString();
		//System.out.println(i.contains(new IceAction()));
		ActionTile test = new ActionTile(new IceAction());
		i.add(new ActionTile(new FireAction()));
		//System.out.println(i.contains(new IceAction()));
		i.add(new ActionTile(new IceAction()));
		//System.out.println(i.contains(new IceAction()));
		i.add(new ActionTile(new FireAction()));
		i.add(test);
		i.add(test);
		i.add(new ActionTile(new DoubleMoveAction()));
		i.add(new ActionTile(new BacktrackAction()));
		i.add(new ActionTile(new DoubleMoveAction()));
		i.printString();
		
		System.out.println(i.saveFormat());

	}
}
