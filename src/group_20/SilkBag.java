
import java.util.Random;

public class SilkBag {
    //private Tile[] contents;
    
    public SilkBag() {
        
    }
    
    /**
     * returns floor tiles AND action tiles
     * @return
     */
    public Tile drawTile() {
        return new Tile();
    }
    
    /**
     * Used by board class to get random floor tiles (Specifically floor tiles -> not action tiles)
     * @return random FloorTile object from silk bag
     */
    public FloorTile drawFloorTile() {
        Random r = new Random();
        int tileID = r.nextInt(4);
        int orientationID = r.nextInt(4);
        int orientation = 0;
        //return new GoalTile();
        
        switch(orientationID) {
        case 1 :
        	orientation = 0;
        	break;
        case 2: 
        	orientation = 90;
        	break;
        case 3:
        	orientation = 180;
        	break;
        case 4:
        	orientation = 270;
        	break;
        default:
        	orientation = 0;
        }
        			
        switch (tileID) {
        case 1:
            return new Straight(5, false, orientation, false, false, null, null, "Straight");
        case 2:
            return new Corner(5, false, orientation, false, false, null, null, "Corner");
        case 3:
            return new TShaped(5, false, orientation, false, false, null, null, "TShaped");
        case 4:
            return new Goal(5, false, orientation, false, false, null, null, "Goal");
        default:
            return new Straight(5, false, orientation, false, false, null, null, "Straight");
        }
    }
}