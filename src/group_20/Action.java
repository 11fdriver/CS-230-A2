package group_20;

public abstract class Action implements Drawable, Saveable {
	public abstract void apply(Player p, Board b);
}
