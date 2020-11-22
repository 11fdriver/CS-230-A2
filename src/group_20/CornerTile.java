import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.shape.Path;
//import javafx.scene.shape.MoveTo;
//import javafx.scene.shape.LineTo;

public class CornerTile extends FloorTile {
	public void draw(int x, int y, GraphicsContext g, int tileWidth) {
		g.strokeRect(x, y, tileWidth, tileWidth);
		g.strokeLine(x + tileWidth/2, y, x + tileWidth/2, y + tileWidth/2);
		g.strokeLine(x + tileWidth/2, y + tileWidth/2, x, y + tileWidth/2);
		
		//g.strokeLine(x + tileWidth/3, y, x + tileWidth/3, y + tileWidth/3);
		//g.strokeLine(x + tileWidth/3, y + tileWidth/3, x, y + tileWidth/3);
		
		//g.strokeLine(x + (tileWidth/3)*2, y, x + (tileWidth/3)*2, y + (tileWidth/3)*2);
		//g.strokeLine(x + (tileWidth/3)*2, y + (tileWidth/3)*2, x, y + (tileWidth/3)*2);
	}
}
