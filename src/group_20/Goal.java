package group_20;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class Goal extends FloorTile {
	
	public Goal(ArrayList<Direction> directions, Location location, Player player, FloorAction state, int lifetime, boolean isFixed) {
		super(directions, location, player, state, lifetime, isFixed);
	}
	
	public String toString() {
		String result = "This is a goal tile!\n";
		result += super.toString();
		return result;
	}
	
	public void draw(GraphicsContext gc, int x, int y) {
		gc.drawImage(this.getSprite(), x, y);
	}
}
