package group_20;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class leaderboardWindow extends BorderPane{
	
	
	public leaderboardWindow() {
		TableView table = new TableView();
		this.setCenter(table);
		
		FlowPane fp = new FlowPane();
		fp.setAlignment(javafx.geometry.Pos.CENTER);
		Button b1 = new Button("b1");
		Button b2 = new Button("b2");
		fp.getChildren().addAll(b1,b2);
		
		this.setTop(fp);
	}
}
