package group_20;

// player loading: board, pnum, loc, invent, loclist, backtracked, profile
// player new:     board, pnum, startloc, profile

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {
	private static final String SEP = File.separator;
	/**
	 * Directory path for configuration.
	 */
	private static final String CONF_DIR_PATH = ".lairofdagon" + SEP;
	/**
	 * Directory path for saved games. 
	 */
	private static final String SAVED_DIR_PATH = CONF_DIR_PATH + "saved" + SEP;
	/**
	 * Directory path for new-game templates.
	 */
	private static final String TEMPLATES_DIR_PATH = CONF_DIR_PATH + "templates" + SEP;
	/**
	 * File path for profile configuration file.
	 */
	private static final String PROFILE_FILE_PATH = CONF_DIR_PATH + "profiles";

	/**
	 * General input scanner.
	 */
	private static Scanner in;
	
	/**
	 * @return Next comma-delimited item
	 */
	private static String nextItem() {
		return in.next().trim();
	}
	
	/**
	 * Construct a new {@link Board} by reading in values.
	 * @return New Board
	 * @throws FileParseException
	 * TODO: Currently returns FloorTile[][]!
	 */
	private static FloorTile[][] loadSavedBoard() throws FileParseException {
		int boardID;
		int width;
		int height;
		String str = null;
		try {
			str = nextItem();
			boardID = Integer.parseInt(str);
			str = nextItem();
			width = Integer.parseInt(str);
			str = nextItem();
			height = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new FileParseException("Expected type int, got '" + str + "'");
		}
 		
		FloorTile[][] b = new FloorTile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				str = nextItem();
				if (!"{FloorTile".equals(str)) {
					throw new FileParseException("Expected '{FloorTile', got '" + str + "'");
				}
				b[x][y] = loadSavedFloorTile();
				b[x][y].setLocation(new Location(x, y));
			}
		}
		
		if (!"Board}".equals(nextItem())) {
			throw new FileParseException("Board not closed");
		}
		
		return b;
	}
	
	/**
	 * Populate {@link SilkBag} with {@link Tile}s.
	 * @throws FileParseException
	 */
	private static void fillSilkBag() throws FileParseException {
		String str = nextItem();
		while (!"SilkBag}".equals(str)) {
			switch (str) {
			case "{FloorTile":
				System.out.println("Constructing FloorTile");
				SilkBag.addTile(loadSavedFloorTile()); // TODO: Merged with SilkBag fixes
				break;
			case "{ActionTile":
				System.out.println("Constructing ActionTile");
				loadSavedActionTile();
				SilkBag.addTile(loadSavedActionTile()); // TODO: Merge with SilkBag fixes
				break;
			default:
				throw new FileParseException("Expected FloorTile or ActionTile, got '" + str + "'");
			}
			str = nextItem();
		}
	}

	/**
	 * Construct a {@link FloorTile} by reading in values.
	 * @return New FloorTile
	 * @throws FileParseException
	 */
	private static FloorTile loadSavedFloorTile() throws FileParseException {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		Location location = null;
		Player player = null;
		FloorAction action = null;
		int lifetime = 0;
		
		// List of Directions
		String str = nextItem();
		if (!str.startsWith("[")) {
			throw new FileParseException("Expected '[<Direction>]', got '" + str + "'");
		}
		str = str.replace("[", "").trim();
		do { // Ensure at least one direction.
			directions.add(stringToDirection(str));
			str = nextItem();
		} while (!str.endsWith("]"));
		directions.add(stringToDirection(str.substring(0, str.length()-1).trim()));
		directions.trimToSize();
		
		// Create player
		str = nextItem();
		switch (str) {
		case "null": 
			break;
		case "{Player": 
			player = loadSavedPlayer();
			break;
		default: 
			throw new FileParseException("Expected '{Player', got '" + str + "'");
		}
		
		// Construct Action
		str = nextItem();
		switch (str) {
		case "null":
			break;
		default:
			try {
				action = (FloorAction) stringToAction(str);
			} catch (ClassCastException e) {
				throw new FileParseException("Expected type 'FloorAction'", e);
			}
		}
		
		// Remaining lifetime of action
		str = nextItem();
		try {
			lifetime = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new FileParseException("Expected '<integer>', got '" + str + "'", e);
		}
		
		// Consume and check closing form
		if (!"FloorTile}".equals(nextItem())) {
			throw new FileParseException("FloorTile not closed");
		}
		
		// TODO: Maybe remove Location from FloorTile constructor.
		return new FloorTile(directions, location, player, action, lifetime);
	}

	/**
	 * Convert a String to a {@link Direction}.
	 * @param str {"NORTH"|"SOUTH"|"EAST"|"WEST"}
	 * @return Direction.{NORTH|SOUTH|EAST|WEST}
	 * @throws FileParseException
	 */
	private static Direction stringToDirection(String str) throws FileParseException {
		Direction d = null;
		switch (str) {
		case "NORTH":
			d = Direction.NORTH;
			break;
		case "EAST":
			d = Direction.EAST;
			break;
		case "SOUTH":
			d = Direction.SOUTH;
			break;
		case "WEST":
			d = Direction.WEST;
			break;
		default: 
			throw new FileParseException("Expected '<Direction>', got '" + str + "'");
		}
		return d;
	}
	
	/**
	 * Construct a new {@link Player} by reading in values.
	 * @return New Player
	 */
	private static Player loadSavedPlayer() {
		return null; // TODO: Check final constructor with Yoshan
	}
	
	/**
	 * Construct new {@link ActionTile} by reading in values.
	 * @return New ActionTile
	 * @throws FileParseException
	 */
	private static ActionTile loadSavedActionTile() throws FileParseException {
		Action action = stringToAction(nextItem());
		
		if (!"ActionTile}".equals(nextItem())) {
			throw new FileParseException("ActionTile not closed");
		}
		
		return new ActionTile(action);
	}
	
	/**
	 * Convert a String to an {@link Action}. 
	 * @param str Action identifier.
	 * @return New Action
	 * @throws FileParseException
	 */
	private static Action stringToAction(String str) throws FileParseException {
		switch (str) {
		case "Fire":
			return new FireAction();
		case "Ice":
			return new IceAction();
		case "Backtrack":
			return new BacktrackAction();
		case "DoubleMove":
			return new DoubleMoveAction();
		default:
			throw new FileParseException("Expected '<Action-Type>', got '" + str + "'");
		}
	}
	
	/**
	 * Re-create new game environment when given a save-file.
	 * <p>
	 * Creates a Board and returns it, and fills the SilkBag with Tiles.
	 * @param f File to read values from
	 * @return Board as part of game
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 */
	private static FloorTile[][] loadSavedGameBoard(File f) throws FileNotFoundException, FileParseException {
		in = new Scanner(f);
		in.useDelimiter(",");
		
		FloorTile[][] b = null;
		while (in.hasNext()) { // Might just change this to an if for better parsing.
			String str = nextItem();
			switch (str) {
			case "{Board":
				System.out.println("Constructing Board");
				b = loadSavedBoard();
				break;
			case "{SilkBag":
				fillSilkBag();
				break;
			default:
				throw new FileParseException("Expected '{SilkBag' or '{Board', got '" + str);
			}
		}
		System.out.println("Finished constructing game");
		in.close();
		return b;
	}
	
	/**
	 * Construct a {@link Profile} by reading in values.
	 * @return New Profile
	 */
	private static Profile loadProfile() {
		// TODO: Add exception throwers.
		int id = Integer.parseInt(nextItem());
		String name = nextItem();
		int wins = Integer.parseInt(nextItem());
		int losses = Integer.parseInt(nextItem());
		int played = Integer.parseInt(nextItem());
		ArrayList<Integer> playedBoards = new ArrayList<Integer>();
		if (0 < played) {
			String str = nextItem().replace("[", "").trim();
			while (!str.endsWith("]")) {
				playedBoards.add(Integer.parseInt(str));
				str = nextItem();
			}
			playedBoards.add(Integer.parseInt(str.replace("]", "").trim()));
		} else {
			nextItem(); // Get rid of '[]'
		}
		String str = nextItem();
		if (!"Profile}".equals(str)) {
			throw new FileParseException("Error reading Profile-file");
		}
		return new Profile(id, name, wins, losses, played);
	}
	
	/**
	 * Load profiles into game environment. This need not return anything; the list is available through {@link Profile}. 
	 * @param f File to read values from
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 */
	private static void loadProfiles(File f) throws FileNotFoundException, FileParseException{
		in = new Scanner(f);
		in.useDelimiter(",");
		while (in.hasNext()) {
			String str = nextItem();
			switch (str) {
			case "{Profile":
				loadProfile();
				break;
			default:
				throw new FileParseException("Expected '{Profile', got '" + str + "'");
			}
		}
		in.close();
	}
	
	/**
	 * Recreate a saved game from it's save-file, by loading Profiles, filling the SilkBag, and creating the Board.
	 * @param fname Name of file within saved-game directory, default: {@code ".labyrinth/saved/"}.
	 * @return Loaded {@link Board} to start playing via.
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 * TODO: returns FloorTile[][]!
	 */
	public static FloorTile[][] loadSavedGame(String fname) throws FileNotFoundException, FileParseException {
		loadProfiles(new File(PROFILE_FILE_PATH));
		return loadSavedGameBoard(new File(SAVED_DIR_PATH + fname));
	}
	
	private static FloorTile[][] loadNewGameBoard(File f) throws FileNotFoundException, FileParseException {
		in = new Scanner(f);
		in.useDelimiter(",");
		
		FloorTile[][] b = null;
		while (in.hasNext()) { // Might just change this to an if for better parsing.
			String str = nextItem();
			switch (str) {
			case "{Board":
				System.out.println("Constructing Board");
				b = loadNewBoard();
				break;
			case "{SilkBag":
				fillSilkBag();
				break;
			default:
				throw new FileParseException("Expected '{SilkBag' or '{Board', got '" + str);
			}
		}
		
		in.close();
		return b;
	}
	
	private static FloorTile[][] loadNewBoard() throws FileParseException {
		int width;
		int height;
		String str = null;
		try {
			str = nextItem();
			width = Integer.parseInt(str);
			str = nextItem();
			height = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new FileParseException("Expected type int, got '" + str + "'");
		}
		FloorTile[][] b = null;
		str = nextItem();
		while (!"Board}".equals(str)) {
			loadNewFloorTile();
			str = nextItem();
		}
		return b;
	}
	
	private static FloorTile loadNewFloorTile() throws FileParseException {
		String str;
		
		Location location = null;
		try {
			location = new Location(Integer.parseInt(nextItem()), Integer.parseInt(nextItem()));
		} catch (NumberFormatException e) {
			throw new FileParseException("Expected type int");
		}
		
		// List of Directions
		ArrayList<Direction> directions = new ArrayList<Direction>();
		str = nextItem();
		if (!str.startsWith("[")) {
			throw new FileParseException("Expected '[<Direction>]', got '" + str + "'");
		}
		str = str.replace("[", "").trim();
		while (!str.endsWith("]")) {
			directions.add(stringToDirection(str));
			str = nextItem();
		}
		directions.add(stringToDirection(str.replace("]", "").trim()));
		directions.trimToSize();
		
		boolean startPoint = "start".equals(nextItem());
		
		// Consume and check closing form
		if (!"FloorTile}".equals(nextItem())) {
			throw new FileParseException("FloorTile not closed");
		}
		
		return new FloorTile(directions, location, player, action, lifetime);
	}
	
	public static FloorTile[][] loadNewGame(String fname) throws FileNotFoundException, FileParseException {
		loadProfiles(new File(PROFILE_FILE_PATH));
		return loadNewGameBoard(new File(TEMPLATES_DIR_PATH + fname));
	}
	
	public static void main(String args[]) {
		
	}
}
