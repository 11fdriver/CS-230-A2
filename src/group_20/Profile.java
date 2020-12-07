package group_20;

import java.util.*;

/**
 * Profile stores user-specific data. 
 */
public class Profile implements Saveable {
	/**
	 * Stores the next available Profile ID.
	 */
	private static int nextID;
	
	/**
	 * A list of all profiles created.
	 */
	private static ArrayList<Profile> profiles;
	
	/**
	 * Unique Profile ID.
	 */
	private final int ID;
	
	/**
	 * Player's name.
	 */
	private String name;
	
	/**
	 * Amount of games Profile has won.
	 */
	private int wins;
	
	/**
	 * Amount of games Profile has lost.
	 */
	private int losses;
	
	/**
	 * Board IDs for games player has played.
	 */
	private ArrayList<Integer> playedBoards;

	/**
	 * Amount of games Profile has played overall.
	 */
	private int played;
	
	/**
	 * Constructor for <b>new</b> Profiles.
	 * @param name Name of user
	 */
	public Profile(String name) {
		this.ID = nextID++;
		this.name = name;
		this.playedBoards = new ArrayList<Integer>();
		profiles.add(this);
	}
	
	/**
	 * Constructor for <b>loading</b> Profiles.
	 * @param id		Unique Profile id
	 * @param name		Name
	 * @param wins		Amount of wins.
	 * @param losses	Amount of losses.
	 * @param played	Amount of games participated in.
	 * @param playedBoards ArrayList of boardIDs that a Profile has played on.
	 */
	public Profile(int id, String name, int wins, int losses, int played, ArrayList<Integer> playedBoards) {
		// Don't trust users to keep profile IDs unique.
		for (Profile p : profiles) {
			if (p.ID == id) {
				id = nextID++;
			}
		}
		
		// Guarantee new Profile IDs don't collide.
		// This means IDs drift upwards as they are removed,
		// but that's okay, they won't exceed int-limit for longtime.
		if (id > nextID) {
			nextID = id + 1;
		}
		
		this.ID = id;
		this.name = name;
		this.wins = wins;
		this.losses = losses;
		this.played = played;
		this.playedBoards = playedBoards;
		profiles.add(this);
	}
	
	/**
	 * Get list of all Profiles created.
	 * @return ArrayList of all Profiles
	 */
	public static ArrayList<Profile> getAll() {
		return profiles;
	}
	
	public static ArrayList<Profile> getLeaderboard(int boardID) {
		ArrayList<Profile> leaderboard = new ArrayList<Profile>();
		leaderboard.addAll(profiles);
		leaderboard.removeIf(p -> !p.hasPlayed(boardID));
		leaderboard.sort((p1, p2) -> new Integer(p1.getWins()).compareTo(p2.getWins()));
		return leaderboard;
	}
	
	/**
	 * @return Unique identifier for Profile.
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @param name New name for Profile.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return Name of Profile
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Amount of games won.
	 */
	public int getWins() {
		return wins;
	}
	
	/**
	 * Increase amount of games won.
	 * This should be used at the end of a game.
	 */
	public void incWins() {
		wins++;
	}
	
	/**
	 * @return Amount of games lost.
	 */
	public int getLosses() {
		return losses;
	}
	
	/**
	 * Increase amount of games lost.
	 * This should be used at the end of a game.
	 */
	public void incLosses() {
		losses++;
	}
	
	/**
	 * @return Amount of games played.
	 */
	public int getPlayed() {
		return played;
	}
	
	/**
	 * Increase amount of games played, 
	 * adding new boardID if board is not played before.
	 * @param boardID
	 */
	public void playOnBoard(int boardID) {
		if (!hasPlayed(boardID)) {
			playedBoards.add(boardID);
		}
		played++;
	}
	
	/**
	 * Whether a Profile has played on a certain Board
	 * @param boardID ID of Board template
	 * @return True if Profile has played on Board
	 */
	public boolean hasPlayed(int boardID) {
		return playedBoards.contains(boardID);
	}
	
	@Override
	public String saveFormat() {
		String str = String.format("{Profile, %03d, %s, %d, %d, %d, %s, %s, [",
				ID,name,wins,losses,played);
		for (int bID : playedBoards) {
			str += bID + ", ";
		}
		if (str.endsWith(", ")) { // Profile may have played no boards.
			str = str.substring(0, str.length() - 2);
		}
		str += "], Profile}";
		return str;
	}
	
}