package group_20;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
import java.io.*;

/**
 * <p><b>Filename:</b> Leaderboard</p>
 * <p><b>Description:</b> Creates the leaderboard.</p>
 * <p><b>Creation date:</b> 06/12/2020</p>
 * @since 07/12/2020
 * @author Edward Kong - 1916234
 * <p><b>Copyright:</b> no copyright</p>
 */

public class Leaderboard {
    
    /**
     * Contains a list of profile instances
     */
    public static ArrayList<Profile> arrayListOfProfileInstances = new ArrayList<Profile> ();

    public static ObservableList<Profile> getProfilesByBoardID(int boardIDInput) {
        
        ArrayList<Profile> filteredProfiles = new ArrayList<Profile> ();
        filteredProfiles = (ArrayList<Profile>)arrayListOfProfileInstances.clone();
        
        for(int i = 0; i < filteredProfiles.size(); i++) {
            if(filteredProfiles.get(i).getNumGamesPlayed(boardIDInput) == 0) {
                filteredProfiles.remove(i);
            }
        }
        
        ObservableList<Profile> observableList = FXCollections.observableArrayList(filteredProfiles);
        return observableList;
    }

}
