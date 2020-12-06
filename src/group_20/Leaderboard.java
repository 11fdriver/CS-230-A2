import java.util.*;
import java.io.*;

public class Leaderboard {

	public static ArrayList<Profile> arrayListOfProfileInstances = new ArrayList<Profile> ();

	public static void main(String[] args) {
		//test code removed
	}

	public static ArrayList<Profile> getLeaderboard(int boardID, boolean order) {
		return sortedProfiles(boardID, order);
	}

	public static List<Profile> getLeaderboard(int boardID, boolean order, int limit) {
		return (sortedProfiles(boardID, order).subList(0, limit));
	}

	public static List<Profile> getLeaderboard(int boardID, boolean order, int lower, int upper) {
		return (sortedProfiles(boardID, order).subList(lower, upper));
	}
	
	private static ArrayList<Profile> sortedProfiles(int boardID, boolean order) {
		
		ArrayList<Profile> sortedProfiles = new ArrayList<Profile> ();
		sortedProfiles = (ArrayList<Profile>)arrayListOfProfileInstances.clone();

		if (order) {
			//Collections.sort(sortedProfiles, new SortByWinsAscending(boardID));
		} else {
			//Collections.sort(sortedProfiles, new SortByWinsDescending(boardID));
		}
		
		return sortedProfiles;
	}

	public static void draw() {
		
	}

}