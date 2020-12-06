package group_20;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	
	private static String currItem;
	
	/**
	 * @return Next comma-delimited item
	 */
	private static String nextItem() {
		currItem = in.next().trim();
		return currItem;
	}
	
	/**
	 * Construct a new {@link Board} by reading in values.
	 * @return New Board
	 * @throws FileParseException
	 * TODO: Currently returns FloorTile[][]!
	 */
	private static FloorTile[][] loadSavedBoard() {
		int boardID = Integer.parseInt(nextItem());
		int width = Integer.parseInt(nextItem());
		int height = Integer.parseInt(nextItem());
 		
		FloorTile[][] b = new FloorTile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				switch (nextItem()) { // Consume "{FloorTile"
				case "{FloorTile":
					b[x][y] = loadSavedFloorTile();
					break;
				case "{Goal":
					b[x][y] = loadSavedGoal();
					break;
				}
				b[x][y].setLocation(new Location(x, y));
			}
		}
		
		nextItem(); // Consume "Board}"
		
		return b;
	}
	
	/**
	 * Populate {@link SilkBag} with {@link Tile}s.
	 * @throws FileParseException
	 */
	private static void fillSilkBag() {
		String str = nextItem();
		while (!"SilkBag}".equals(str)) {
			switch (str) {
			case "{FloorTile":
				SilkBag.addTile(loadSavedFloorTile()); // TODO: Merged with SilkBag fixes
				break;
			case "{ActionTile":
				loadSavedActionTile();
				SilkBag.addTile(loadSavedActionTile()); // TODO: Merge with SilkBag fixes
				break;
			default:
			}
			str = nextItem();
		}
	}

	/**
	 * Construct a {@link FloorTile} by reading in values.
	 * @return New FloorTile
	 * @throws FileParseException
	 */
	private static FloorTile loadSavedFloorTile() {
		Location location = null;
		Player player = null;
		FloorAction action = null;
		int lifetime = 0;
		
		// List of Directions
		ArrayList<Direction> directions = loadDirectionList();
		
		// Create player
		switch (nextItem()) {
		case "null": 
			break;
		case "{Player": 
			player = loadSavedPlayer();
			break;
		default: 
		}
		
		// Construct Action
		switch (nextItem()) {
		case "null":
			break;
		default:
			action = (FloorAction) stringToAction(currItem);
		}
		
		// Remaining lifetime of action
		lifetime = Integer.parseInt(nextItem());
		
		// Consume "FloorTile}"
		nextItem();
		
		// TODO: Maybe remove Location from FloorTile constructor.
		return new FloorTile(directions, location, player, action, lifetime);
	}
	
	/**
	 * Construct a {@link Goal} by reading in values.
	 * @return
	 */
	private static Goal loadSavedGoal() {
		return new Goal();
	}
	
	/**
	 * Convert a String to a {@link Direction}.
	 * @param str {"NORTH"|"SOUTH"|"EAST"|"WEST"}
	 * @return Direction.{NORTH|SOUTH|EAST|WEST}
	 * @throws FileParseException
	 */
	private static Direction stringToDirection(String str) {
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
		}
		return d;
	}
	
	/**
	 * Construct a new {@link Player} by reading in values.
	 * @return New Player
	 */
	private static Player loadSavedPlayer() {
		// player loading: board, pnum, loc, invent, loclist, backtracked, profile
		// FIXME: This is a good reason why Board should be static,
		// it's difficult to pass a reference to a board I haven't finished parsing 
		Board board = null;
		
		int playerNum = Integer.parseInt(nextItem());
		
		Location location = null; // Set by FloorTile/Board loader
		
		Inventory inventory = new Inventory(); // TODO: Inventory saveFormat() = [Fire, Ice, etc]
		String[] iStrings = in.next("]").replace("[", "").replace("]", "").split(",");
		for (String str : iStrings) {
			inventory.add(new ActionTile(stringToAction(str.trim())));
		}
		
		LocationList locations = new LocationList();
		String[] lStrings = in.next("]").replace("[", "").replace("]", "").split(",");
		for (int i = 0; lStrings.length > i;) {
			locations.add(new Location(
					Integer.parseInt(lStrings[i++].trim()), 
					Integer.parseInt(lStrings[i++].trim())));
		}
		
		boolean backtracked = Boolean.parseBoolean(nextItem());
		
		int pID = Integer.parseInt(nextItem());
		Profile profile;
		for (Profile p : Profile.getAll()) {
			profile = (p.getID() == pID) ? p : null;
		}
		
		return new Player(board, playerNum, location, inventory, backtracked, profile);
	}
	
	/**
	 * Construct new {@link ActionTile} by reading in values.
	 * @return New ActionTile
	 * @throws FileParseException
	 */
	private static ActionTile loadSavedActionTile() {
		Action action = stringToAction(nextItem());
		nextItem(); // Consume "ActionTile}"
		return new ActionTile(action);
	}
	
	/**
	 * Convert a String to an {@link Action}. 
	 * @param str Action identifier.
	 * @return New Action
	 * @throws FileParseException
	 */
	private static Action stringToAction(String str) {
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
			return null; // TODO: Change to exception
		}
	}
	
	/**
	 * Re-create a game environment when given a save-file.
	 * <p>
	 * Creates a Board and returns it, and fills the SilkBag with Tiles.
	 * @param f File to read values from
	 * @return Board as part of game
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 */
	private static FloorTile[][] loadSavedGameBoard(File f) throws FileNotFoundException {
		in = new Scanner(f);
		in.useDelimiter(",");
		
		FloorTile[][] b = null;
		if ("{Board".equals(nextItem())) {
			b = loadSavedBoard();
		} else {
			// TODO: exception here
		}
		
		if ("{SilkBag".equals(nextItem())) {
			fillSilkBag();
		} else {
			// TODO: exception here
		}
		
		in.close();
		return b;
	}
	
	/**
	 * Construct a {@link Profile} by reading in values.
	 * @return New Profile
	 */
	private static Profile loadProfile() {
		int id = Integer.parseInt(nextItem());
		String name = nextItem();
		int wins = Integer.parseInt(nextItem());
		int losses = Integer.parseInt(nextItem());
		int played = Integer.parseInt(nextItem());
		ArrayList<Integer> playedBoards = new ArrayList<Integer>();
		String[] bidStrings = in.next("]").replace("[", "").replace("]", "").split(",");
		for (String str : bidStrings) {
			playedBoards.add(Integer.parseInt(str.trim()));
		}
		nextItem();
		return new Profile(id, name, wins, losses, played, playedBoards);
	}
	
	/**
	 * Extract a list of {@link Direction}s by reading in values.
	 * @return ArrayList of Directions
	 */
	private static ArrayList<Direction> loadDirectionList() {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		String[] dStrings = in.next("]").replace("[", "").replace("]", "").split(",");
		for (String str : dStrings) {
			directions.add(stringToDirection(str.trim()));
		}
		directions.trimToSize();
		return directions;
	}
	
	/**
	 * Load profiles into game environment. This need not return anything; the list is available through {@link Profile}. 
	 * @param f File to read values from
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 */
	private static void loadProfiles(File f) throws FileNotFoundException {
		in = new Scanner(f);
		in.useDelimiter(",");
		while (in.hasNext()) {
			switch (nextItem()) {
			case "{Profile":
				loadProfile();
				break;
			default:
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
	
	/**
	 * Create a new game from a template-file.
	 * @param f template-file to read, within templates directory (default: {@code ".lairofdagon/templates/"}
	 * @return A new Board
	 * @throws FileNotFoundException
	 */
	private static FloorTile[][] loadNewGameBoard(File f) throws FileNotFoundException {
		in = new Scanner(f);
		in.useDelimiter(",");
		
		FloorTile[][] b = null;
		if ("{Board".equals(nextItem())) {
			b = loadNewBoard();
		} else {
			// TODO: exception here
		}
		
		if ("{SilkBag".equals(nextItem())) {
			fillSilkBag();
		} else {
			// TODO: exception here
		}
		
		// This is going to have to be moved, it relies
		// on b being FloorTile[][], but it will be Board
		// Which doesn't provide such direct access.
		//
		// I propose that SilkBag declarations should come first.
		// It only means that the if statements should be swapped.
		for (int x = 0; b.length > x; x++) {
			for (int y = 0; b[0].length > y; y++) {
				if (null == b[x][y]) {
					continue;
				}
				Tile t = null;
				while (!(t instanceof FloorTile)) {
					// Maybe randomly getting tiles isn't ideal, but it should work.
					// Though this enters an infinite loop if not enough 
					// FloorTiles in SilkBag to fill the Board.
					t = SilkBag.removeTile();
				}
				b[x][y] = (FloorTile) t;
			}
		}
		
		in.close();
		return b;
	}
	
	/**
	 * Construct a new Board by reading in values.
	 * @return New {@link Board}
	 */
	private static FloorTile[][] loadNewBoard() {
		int width = Integer.parseInt(nextItem());
		int height = Integer.parseInt(nextItem());
		FloorTile[][] b = null;
		while (!"Board}".equals(nextItem())) {
			switch (currentItem) {
			case "{FloorTile":
				loadNewFloorTile();
				break;
			case "{Goal":
				loadNewGoal();
				break;
			default:
				// TODO: exception here
			}
		}
		return b;
	}
	
	/**
	 * Constructs a <b>fixed</b> {@link FloorTile} from values read from a template file.
	 * @return FloorTile for a new game
	 */
	private static FloorTile loadNewFloorTile() {
		Location location = new Location(Integer.parseInt(nextItem()), Integer.parseInt(nextItem()));
		
		ArrayList<Direction> directions = loadDirectionList();
		
		boolean startPoint = "start".equals(nextItem());
		
		nextItem(); // Consume "FloorTile}"
		
		return new FloorTile(directions, location);
	}
		
	/**
	 * Constructs a {@link Goal} from values read from a template file.
	 * @return Goal for a new game.
	 */
	private static Goal loadNewGoal() {
		Location location = new Location(Integer.parseInt(nextItem()), Integer.parseInt(nextItem()));
		
		return new Goal(location);
	}
	
	/**
	 * Create a new game from it's template-file, by loading Profiles, filling the SilkBag, and creating the
	 * Board (including filling empty spots from the SilkBag).
	 * @param fname Name of file within template director ({@value TEMPLATES_DIR_PATH})
	 * @return Loaded {@link Board} to start playing.
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 */
	public static FloorTile[][] loadNewGame(String fname) throws FileNotFoundException, FileParseException {
		loadProfiles(new File(PROFILE_FILE_PATH));
		return loadNewGameBoard(new File(TEMPLATES_DIR_PATH + fname));
	}
	
	/**
	 * Save a game to the file at a given path.
	 * @param fname Name of file within saved directory: ({@value SAVED_DIR_PATH})
	 * @param b Board to be saved
	 * @return true if file had to be created for saving
	 * @throws IOException
	 */
	public static boolean saveGame(String fname, Board b) throws IOException {
		File f = new File(SAVED_DIR_PATH + fname);
		boolean created = f.createNewFile();
		
		FileWriter writer = new FileWriter(f, true);
		writer.write(b.saveFormat());
		writer.append(",\n");
		writer.append(SilkBag.saveFormat());
		writer.close();
		
		f = new File(PROFILE_FILE_PATH);
		f.createNewFile();
		writer = new FileWriter(f, true);
		writer.write(""); // Empty file
		Profile.getAll().forEach(p -> writer.write(p.saveFormat() + ",\n"));
		
		return created;
	}
	
}
