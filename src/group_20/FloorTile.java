import javafx.scene.canvas.GraphicsContext;

public class FloorTile extends Tile {
	public String s = "This is a floor tile";
	private Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
	}
	
	public boolean isOccupied() {
		return this.player != null;
	}
}
