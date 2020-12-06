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
	
	public static void main(String[] args) {
		setNoOfBoard(4);

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

		System.out.println(testprofile2.toString());
		testprofile2.updateProfile(2, true);
		System.out.println("testprofile2.updateProfile(2, true);");
		System.out.println(testprofile2.toString());
		
		System.out.print("\ntestprofile2.getProfileID(): ");
		System.out.println(testprofile2.getProfileID());

		System.out.print("testprofile2.getName(): ");
		System.out.println(testprofile2.getName());

		System.out.print("testprofile2.getGamesPlayed(): ");
		System.out.println(Arrays.toString(testprofile2.getGamesPlayed()));

		System.out.print("testprofile2.getGamesPlayed(2): ");
		System.out.println(testprofile2.getGamesPlayed(2));

		System.out.print("testprofile2.getWins(): ");
		System.out.println(Arrays.toString(testprofile2.getWins()));

		System.out.print("testprofile2.getWins(2): ");
		System.out.println(testprofile2.getWins(2));

		System.out.print("testprofile2.getLosses(): ");
		System.out.println(Arrays.toString(testprofile2.getLosses()));
		
		System.out.print("testprofile2.getLosses(2): ");
		System.out.println(testprofile2.getLosses(2));

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
	
	public int[] getGamesPlayed() {
		return gamesPlayed;
	}

	public int getGamesPlayed(int boardID) {
		return gamesPlayed[boardID];
	}
	
	public int[] getWins() {
		return wins;
	}

	public int getWins(int boardID) {
		return wins[boardID];
	}
	
	public int[] getLosses() {
		return losses;
	}
	
	public int getLosses(int boardID) {
		return losses[boardID];
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