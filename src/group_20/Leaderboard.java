import java.util.*;
import java.io.*;

public class Leaderboard {

	public static ArrayList<Profile> arrayListOfProfileInstances = new ArrayList<Profile> ();

	public static void main(String[] args) {
		int[][] profile0 = {{1,2,3}, {1,1,1} ,{0,1,2}};	//Alice
		int[][] profile1 = {{6,5,4}, {3,3,3} ,{3,2,1}};	//Bob
		int[][] profile2 = {{7,8,9}, {1,1,1} ,{0,1,2}};	//Chuck
		int[][] profile3 = {{1,2,3}, {1,1,1} ,{0,1,2}};	//Craig
		int[][] profile4 = {{1,2,3}, {1,1,1} ,{0,1,2}};	//Charlie

		Profile testprofile0 = new Profile("Alice", profile0[0], profile0[1], profile0[2]);
		Profile testprofile1 = new Profile("Bob", profile1[0], profile1[1], profile1[2]);
		Profile testprofile2 = new Profile("Chuck", profile2[0], profile2[1], profile2[2]);
		Profile testprofile3 = new Profile("Craig", profile3[0], profile3[1], profile3[2]);
		Profile testprofile4 = new Profile("Charlie", profile4[0], profile4[1], profile4[2]);
		
		for(Profile n : getLeaderboard(2, false)) {
			System.out.print(n.getName());
			System.out.print("\t\t");
			System.out.println(n.getWins(0));
			System.out.println();
		}
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
			Collections.sort(sortedProfiles, new SortByWinsAscending(boardID));
		} else {
			Collections.sort(sortedProfiles, new SortByWinsDescending(boardID));
		}
		
		return sortedProfiles;
	}

	public static void draw() {
		
	}

}