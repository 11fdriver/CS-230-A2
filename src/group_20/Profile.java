import java.util.*;

public class Profile {
	private static int noOfBoard;
	private static int nextProfileID = 1;	//profileID starts at 1, for profileID "empty" check
	private int profileID;
	private String name;
	private int[] numGamesPlayed;
	private int[] numWins;
	private int[] numLosses;
	private int totalGamesPlayed;
	private int totalWins;
	private int totalLosses;
	private String[] lastGameDateTime;	//NOT implemented
	
	public static void main(String[] args) {
		//test code removed
	}

	public Profile(String name) {
		setProfileID();
		setName(name);
		numGamesPlayed = new int[noOfBoard];
		numWins = new int[noOfBoard];
		numLosses = new int[noOfBoard];

		Leaderboard.arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}

	public Profile(String name, int[] numGamesPlayed, int[] numWins, int[] numLosses) {
		setProfileID();
		setName(name);
		setNumGamesPlayed(numGamesPlayed);
		setNumWins(numWins);
		setNumLosses(numLosses);

		Leaderboard.arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}

	public void updateProfile(int boardID, boolean winLoss) {
		numGamesPlayed[boardID] ++;
		
		if (winLoss) {
			numWins[boardID] ++;
		} else {
			numLosses[boardID] ++;
		}
	}

	public String toString() {
		return String.valueOf(profileID) + ", " + name + ", " + Arrays.toString(numGamesPlayed) + ", " + Arrays.toString(numWins) + ", " + Arrays.toString(numLosses);
	}
	
	//Getters

	public int getProfileID() {
		return profileID;
	}
	
	public String getName() {
		return name;
	}
	
	/*public int[] getNumGamesPlayed() {
		return numGamesPlayed;
	}*/

	public int getNumGamesPlayed(int boardID) {
		return numGamesPlayed[boardID];
	}
	
	/*public int[] getNumWins() {
		return numWins;
	}*/

	public int getNumWins(int boardID) {
		return numWins[boardID];
	}

	/*public int[] getNumLosses() {
		return numLosses;
	}*/
	
	public int getNumLosses(int boardID) {
		return numLosses[boardID];
	}

	public int getTotalGamesPlayed() {
		return Arrays.stream(numGamesPlayed).sum();
	}

	public int getTotalWins() {
		return Arrays.stream(numWins).sum();
	}

	public int getTotalLosses() {
		return Arrays.stream(numLosses).sum();
	}

	public static int getNoOfBoard() {
		return noOfBoard;
	}
	
	//Setters

	public static void setNoOfBoard(int noOfBoard) {
		Profile.noOfBoard = noOfBoard;
	}

	public void setProfileID() {
		if (profileID == 0) {
			profileID = nextProfileID;
			nextProfileID++;
		}
	}

	public void setProfileID(int profileID) {
		//duplication check NOT implemented
		if (profileID == 0) {
			this.profileID = profileID;
			if (profileID > nextProfileID) {
				nextProfileID++;
			}
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNumGamesPlayed(int[] numGamesPlayed) {
		this.numGamesPlayed = numGamesPlayed;
	}

	public void setNumWins(int[] numWins) {
		this.numWins = numWins;
	}

	public void setNumLosses(int[] numLosses) {
		this.numLosses = numLosses;
	}
}