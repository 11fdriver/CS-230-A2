import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
import java.io.*;

public class Leaderboard {

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