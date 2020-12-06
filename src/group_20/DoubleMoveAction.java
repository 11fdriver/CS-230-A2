package group_20;

public class DoubleMoveAction extends Action {

	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub
	}

	@Override
	public String saveFormat() {
		return "DoubleMove";
	}

	@Override
	public void apply(Player p, Board b) {
		p.addMoves(1);
	}

}
