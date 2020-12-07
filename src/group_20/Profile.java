package group_20;

import java.util.*;

/**
 * <p><b>Filename:</b> Profile</p>
 * <p><b>Description:</b> Creates a profile.</p>
 * <p><b>Creation date:</b> 06/12/2020</p>
 * @since 07/12/2020
 * @author Inderpreet Sandhu - 852298, Edward Kong - 1916234
 * <p><b>Copyright:</b> no copyright</p>
 */

public class Profile {
	
	/**
	 * Determining number of board
	 */
	private static int noOfBoard;
	
	/**
	 * The next profile ID to use for the constructor 
	 */
	private static int nextProfileID = 1;	//profileID starts at 1, for profileID "empty" check
	
	/**
	 * Unique identifier for a profile
	 */
	private int profileID;
	
	/**
	 * Profile username
	 */
	private String name;
	
	/**
	 * Stores amount of games played on a persons profile
	 */
	private int[] numGamesPlayed;
	
	/**
	 * Stores number of wins on a persons profile
	 */
	private int[] numWins;
	
	/**
	 * Stores number of losses on a persons profile
	 */
	private int[] numLosses;
	
	/**
	 * Sum of number of games played 
	 */
	private int totalGamesPlayed;
    
	/**
	 * Sum of wins
	 */
	private int totalWins;
    
	/**
	 * Sum of losses
	 */
	private int totalLosses;
	
	/**
	 * Creates a profile with a given username
	 * 
	 * @param name 				Takes in a username for a profile		
	 */
	public Profile(String name) {
		setProfileID();
		setName(name);
		numGamesPlayed = new int[noOfBoard];
		numWins = new int[noOfBoard];
		numLosses = new int[noOfBoard];

		Leaderboard.arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}
	
	/**
	 * Creates a profile with a given username, number of games played, number of wins and number of losses
	 * 
	 * @param name 				Takes in a username for a profile	
	 * @param numGamesPlayed    Takes in number of games played
	 * @param numWins			Takes in number of wins
	 * @param numLosses	        Takes in number of losses
	 */
	public Profile(String name, int[] numGamesPlayed, int[] numWins, int[] numLosses) {
		setProfileID();
		setName(name);
		setNumGamesPlayed(numGamesPlayed);
		setNumWins(numWins);
		setNumLosses(numLosses);

		Leaderboard.arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}
	
	/**
	 * Updates profile wins and losses
	 * 
	 * @param boardID			ID of the board that was played on	
	 * @param winLoss           Boolean value determining win/loss, if true = win, if false = loss
	 */
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
	
	public int[] getNumGamesPlayed() {
		return numGamesPlayed;
	}

	public int getNumGamesPlayed(int boardID) {
		return numGamesPlayed[boardID];
	}
	
	public int[] getNumWins() {
		return numWins;
	}

	public int getNumWins(int boardID) {
		return numWins[boardID];
	}

	public int[] getNumLosses() {
		return numLosses;
	}
	
	public int getNumLosses(int boardID) {
		return numLosses[boardID];
	}

	public static int getNoOfBoard() {
		return noOfBoard;
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