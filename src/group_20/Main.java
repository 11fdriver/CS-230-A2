import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import javafx.scene.canvas.Canvas;
import javafx.stage.WindowEvent;

public class Main extends Component {
	BufferedImage img;
	private static final int WINDOW_HEIGHT = 900;
	private static final int WINDOW_WIDTH = 900;
	private static final int CANVAS_HEIGHT = 400;
	private static final int CANVAS_WIDTH = 400;
	private static final int SHAPE_SIZE = 20;
	private static final int SHAPE_SIZE_UPPER_BOUND = 100;
	private static final int TILE_WIDTH = 40;
	private static final int BOARD_WIDTH = 9;
	private static final int BOARD_LENGTH = 9;
	private Canvas canvas;
	Board currentBoard = new Board(1,BOARD_WIDTH,BOARD_LENGTH,new SilkBag());
	
	 public void paint(Graphics g) {
		
		 int i = 0;
		 int x1 = 0;
		 int x2 = 0;
		 int x3 = 0;
		 int x4 = 0;
		 int x5 = 0;
		 int x6 = 0;
		 int x7 = 0;
		 int x8 = 0;
		 int x9 = 0;
		 int j = 0;
		 int y = 0;
		 int a = 0;
		 int b = 0;
		 int c = 0;
		 int d = 0;
		 int e = 0;
		 int f = 0;
		 int h = 0;
	     while (i < BOARD_WIDTH) {   
			 g.drawImage(img, x1, 0, null);
			 x1 = x1 + 100;
			 i++;
	     }
	     while (j < BOARD_WIDTH) {  
			 g.drawImage(img, x2, 95, null);
			 x2 = x2 + 100;
			 j++;
	     }
	     while (a < BOARD_WIDTH) {  
			 g.drawImage(img, x3, 190, null);
			 x3 = x3 + 100;
			 a++;
		 }
	     while (b < BOARD_WIDTH) {  
			 g.drawImage(img, x4, 285, null);
			 x4 = x4 + 100;
			 b++;
		 }
	     while (c < BOARD_WIDTH) {  
			 g.drawImage(img, x5, 380, null);
			 x5 = x5 + 100;
			 c++;
		 }
	     while (d < BOARD_WIDTH) {  
			 g.drawImage(img, x6, 475, null);
			 x6 = x6 + 100;
			 d++;
		 }
	     while (e < BOARD_WIDTH) {  
			 g.drawImage(img, x7, 570, null);
			 x7 = x7 + 100;
			 e++;
		 }
	     while (f < BOARD_WIDTH) {  
			 g.drawImage(img, x8, 665, null);
			 x8 = x8 + 100;
			 f++;
		 }
	     while (h < BOARD_WIDTH) {  
			 g.drawImage(img, x9, 760, null);
			 x9 = x9 + 100;
			 h++;
		 }
	 }
	
	 public Main() {
		 Random r = new Random();
		 int tileType = r.nextInt(4);
		 switch (tileType) {
		 case 1:
			 try {
		           img = ImageIO.read(new File("C:\\Users\\Owner\\Pictures\\StraightTile.png"));
		       } catch (IOException e) {
		       }
		 case 2:
		 try {
	           img = ImageIO.read(new File("C:\\Users\\Owner\\Pictures\\CornerTile.png"));
	       } catch (IOException e) {
	       }
		 case 3:
			 try {
		           img = ImageIO.read(new File("C:\\Users\\Owner\\Pictures\\TShapedTile.png"));
		       } catch (IOException e) {
		       } 
		 case 4:
			 try {
		           img = ImageIO.read(new File("C:\\Users\\Owner\\Pictures\\GoalTile.png"));
		       } catch (IOException e) {
		       }
	 } 
	 }
	 
	 public Dimension getPreferredSize() {
	        if (img == null) {
	             return new Dimension(100,100);
	        } else {
	           return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	       }
	    }
	 
	public static void main(String[] args) {
	JFrame f = new JFrame("Labrynth");
	        
	        f.addWindowListener(new WindowAdapter(){
	                public void windowClosing(WindowEvent e) {
	                    System.exit(0);
	                }
	            });
	
	        int i = 0;
	        while (i < BOARD_WIDTH) {
	        f.add(new Main());
	        f.pack();
	        f.setVisible(true);
	        i++;
	        }	
	}
}
