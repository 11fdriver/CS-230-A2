package group_20;

public class BacktrackAction extends Action {

	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub
	}

	@Override
	public String saveFormat() {
		// TODO Auto-generated method stub
		return "Backtrack";
	}

	@Override
	public void apply(Player p, Board b) {
		System.out.println("Started apply");
		Player opponent = b.getTileAtClickMatching(t -> {
			System.out.println("I recieved a tile");
			Player chosen = t.getPlayer();
			return (null != chosen && chosen != p && !chosen.getHasBeenBacktracked());
		}).getPlayer();
		LocationList locs = opponent.getPreviousLocations();
        FloorTile second = b.getTileAt(locs.getSecond());
        FloorTile third = b.getTileAt(locs.getSecond());
        if (null != second && second.acceptsPlayer()) {
            opponent.setLocation(locs.getSecond());
        } else if (null != third && third.acceptsPlayer()) {
            opponent.setLocation(locs.getThird());
        } else {
            return;
		}
		opponent.setHasBeenBacktracked(true);
		System.out.println("Finished apply");
	}
	
	public String toString() {
		return "This is a backtrack action tile";
	}
}
