import javafx.scene.canvas.GraphicsContext;

public class GoalTile extends FloorTile {
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
		g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth);
		g.strokeLine(x, y + tileWidth/2, x + tileWidth, y + tileWidth/2);
	}
}
