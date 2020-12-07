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
		Player opponent = b.getTileAtClickMatching(t -> {
			Player chosen = t.getPlayer();
			return (null != chosen && chosen != p && !chosen.getHasBeenBacktracked());
		}).getPlayer();
		LocationList locs = opponent.getPreviousLocations();
		if (b.getTileAt(locs.getSecond()).acceptsPlayer()) {
			opponent.setLocation(locs.getSecond());
		} else if (b.getTileAt(locs.getThird()).acceptsPlayer()) {
			opponent.setLocation(locs.getThird());
		} else {
			return;
		}
		opponent.setHasBeenBacktracked(true);
	}
	
	public String toString() {
		return "This is a backtrack action tile";
	}
}
