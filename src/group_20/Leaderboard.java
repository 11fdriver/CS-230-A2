

import java.util.*;
import java.io.*;

public class Leaderboard {
	private static int boardIDToCompare;

	private static ArrayList<Profile> getLeaderboard(int boardID, boolean order) {
		return sortedProfiles(boardID, order);
	}

	private static List<Profile> getLeaderboard(int boardID, boolean order, int limit) {
		return (sortedProfiles(boardID, order).subList(0, limit));
	}

	private static List<Profile> getLeaderboard(int boardID, boolean order, int lower, int upper) {
		return (sortedProfiles(boardID, order).subList(lower, upper));
	}
	
	private static ArrayList<Profile> sortedProfiles(int boardID, boolean order) {
		Leaderboard.boardIDToCompare = boardIDToCompare;
		
		ArrayList<Profile> sortedProfiles = new ArrayList<Profile>();
		Profile.getArrayListOfProfileInstances().clone();

		if (order) {
			Collections.sort(sortedProfiles, new SortByWinsAscending());
		} else {
			Collections.sort(sortedProfiles, new SortByWinsDescending());
		}
		
		return sortedProfiles;
	}

	public static int getBoardIDToCompare() {
		return boardIDToCompare;
	}

	public static void draw() {
		//Canvas NOT yet implemented
		for(Profile n : getLeaderboard(0, true)) {
			System.out.print(n.getName());
			System.out.print("\t\t");
			System.out.println(n.getWins(0));
			System.out.println();
		}
	}
}