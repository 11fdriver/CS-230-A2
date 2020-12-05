package group_20;

import java.util.*;

/**
 * Profile stores user-specific data. 
 */
public class Profile {
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
		profiles.add(this);
	}
	
	/**
	 * Constructor for <b>loading</b> Profiles.
	 * @param id		Unique Profile id
	 * @param name		Name
	 * @param wins		Amount of wins.
	 * @param losses	Amount of losses.
	 * @param played	Amount of games participated in.
	 */
	public Profile(int id, String name, int wins, int losses, int played) {
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
		profiles.add(this);
	}
	
	public static ArrayList<Profile> getAll() {
		return profiles;
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
	 * <br>
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
	 * <br>
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
	 * Increase amount of games played.
	 * <br>
	 * This should be used at the <b>beginning</b> of a game.
	 */
	public void incPlayed() {
		played++;
	}
	
}