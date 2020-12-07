package group_20;

import java.util.*;

public class Profile {
	private static int noOfBoard;	//final modifier NOT yet implemented
	private static int nextProfileID = 1;	//profileID starts at 1, for profileID "empty" check
	private int profileID;
	private String name;
	private Integer[] gamesPlayed;
	private Integer[] wins;
	private Integer[] losses;
	private String[] lastGameDateTime;	//NOT yet implemented

	//----------------------------------------------------------------------------------------------------
	//for TESTING Leaderboard class

	private static ArrayList<Profile> arrayListOfProfileInstances;

	public static ArrayList<Profile> getArrayListOfProfileInstances() {	//for TESTING Leaderboard class
		return arrayListOfProfileInstances;
	}
	//----------------------------------------------------------------------------------------------------
	
	public Profile(String name) {
		setProfileID();
		setName(name);
		gamesPlayed = new Integer[noOfBoard];
		wins = new Integer[noOfBoard];
		losses = new Integer[noOfBoard];

		arrayListOfProfileInstances.add(this);	
	}

	public Profile(String name,Integer[] gamesPlayed, Integer[] wins, Integer[] losses) {
		setProfileID();
		setName(name);
		setGamesPlayed(gamesPlayed);
		setWins(wins);
		setLosses(losses);

		arrayListOfProfileInstances.add(this);	//for TESTING Leaderboard class
	}
	
	public void updateProfile(int boardID, boolean winLoss) {
		gamesPlayed[boardID] ++;
		
		//somehow update lastGameDateTime
		
		if (winLoss) {
			wins[boardID] ++;
		} else {
			losses[boardID] ++;
		}
	}
	
	//Getters
	public List<Object> getStats(int boardID) {
		return Arrays.asList(name, gamesPlayed[boardID], wins[boardID], losses[boardID]);
	}
	
	public int getProfileID() {
		return profileID;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer[] getGamesPlayed() {
		return gamesPlayed;
	}

	public Integer getGamesPlayed(int boardID) {
		return gamesPlayed[boardID];
	}
	
	public Integer[] getWins() {
		return wins;
	}

	public Integer getWins(int boardID) {
		return wins[boardID];
	}
	
	public Integer[] getLosses() {
		return losses;
	}
	
	public Integer getLosses(int boardID) {
		return losses[boardID];
	}
	
	public String[] getLastGameDateTime() {
		return null;
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
	
	public void setGamesPlayed(Integer[] gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setWins(Integer[] wins) {
		this.wins = wins;
	}

	public void setLosses(Integer[] losses) {
		this.losses = losses;
	}

	public void setLastGameDateTime(String[] lastGameDateTime) {
		this.lastGameDateTime = lastGameDateTime;
	}

	public static Object getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}