import java.util.*;

public class Profile {
	private static int noOfBoard;
	private static int nextProfileID = 1;	//profileID starts at 1, for profileID "empty" check
	private int profileID;
	private String name;
	private int[] gamesPlayed;
	private int[] wins;
	private int[] losses;
	private String[] lastGameDateTime;	//NOT implemented

	public static int controllerBoardID;
	
	public static void main(String[] args) {
		//test code removed
	}

	public Profile(String name) {
		setProfileID();
		setName(name);
		gamesPlayed = new int[noOfBoard];
		wins = new int[noOfBoard];
		losses = new int[noOfBoard];

		Leaderboard.arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}

	public Profile(String name, int[] gamesPlayed, int[] wins, int[] losses) {
		setProfileID();
		setName(name);
		setGamesPlayed(gamesPlayed);
		setWins(wins);
		setLosses(losses);

		Leaderboard.arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}

	public void updateProfile(int boardID, boolean winLoss) {
		gamesPlayed[boardID] ++;
		
		if (winLoss) {
			wins[boardID] ++;
		} else {
			losses[boardID] ++;
		}
	}

	public String toString() {
		return String.valueOf(profileID) + ", " + name + ", " + Arrays.toString(gamesPlayed) + ", " + Arrays.toString(wins) + ", " + Arrays.toString(losses);
	}
	
	//Getters

	public int getProfileID() {
		return profileID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getGamesPlayed() {
		return gamesPlayed[controllerBoardID];
	}
	
	/*public int[] getGamesPlayed() {
		return gamesPlayed;
	}

	public int getGamesPlayed(int boardID) {
		return gamesPlayed[boardID];
	}*/
	
	public int getWins() {
		return wins[controllerBoardID];
	}
	
	/*public int[] getWins() {
		return wins;
	}

	public int getWins(int boardID) {
		return wins[boardID];
	}*/
	
	public int getLosses() {
		return losses[controllerBoardID];
	}

	/*public int[] getLosses() {
		return losses;
	}
	
	public int getLosses(int boardID) {
		return losses[boardID];
	}*/

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
	
	public void setGamesPlayed(int[] gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setWins(int[] wins) {
		this.wins = wins;
	}

	public void setLosses(int[] losses) {
		this.losses = losses;
	}
}