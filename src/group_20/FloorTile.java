package group_20;

public class FloorTile extends Tile implements Subscriber {
	private Action state;
	private int stateLifetime = 0;
	
	public void setState(FloorAction a) {
		this.state = a;
		this.stateLifetime = a.getLifetime();
		TurnNotifier.addSubscriber(this);
	}	
	
	public void update() {
		stateLifetime--;
		if (0 == stateLifetime) {
			TurnNotifier.addSubscriber(this);
		}
	}
	
	@Override
	public void draw(Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public String saveFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
