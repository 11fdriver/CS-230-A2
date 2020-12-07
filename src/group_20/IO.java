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
	
	private static String currentItem;
	
	/**
	 * @return Next comma-delimited item
	 */
	private static String nextItem() {
		currentItem = in.next().trim();
		return currentItem;
	}
	
	/**
	 * Construct a new {@link Board} by reading in values.
	 * @return New Board
	 * @throws FileParseException
	 */
	private static Board loadSavedBoard(String fname) {
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
		
		ArrayList<Player> players = new ArrayList<Player>();
		while ("{Player".equals(nextItem())) {
			players.add(loadSavedPlayer());
		}
		
		int currentPlayer = Integer.parseInt(nextItem());
		
		nextItem(); // Consume "Board}"
		
		return new Board(fname, boardID, width, height, b, (Player[]) players.toArray(), currentPlayer);
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
				SilkBag.addTile(loadSavedFloorTile());
				break;
			case "{ActionTile":
				loadSavedActionTile();
				SilkBag.addTile(loadSavedActionTile());
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
		// List of Directions
		ArrayList<Direction> directions = loadDirectionList();

		Location location = null;
		
		Player player = null;
		
		FloorAction action = null;
		// Construct Action
		switch (nextItem()) {
		case "null":
			break;
		default:
			action = (FloorAction) stringToAction(currentItem);
		}
		
		// Remaining lifetime of action
		int lifetime = Integer.parseInt(nextItem());
		
		boolean isFixed = Boolean.parseBoolean(nextItem());
		
		// Consume "FloorTile}"
		nextItem();
		
		return new FloorTile(directions, location, player, action, lifetime, isFixed);
	}
	
	/**
	 * Construct a {@link Goal} by reading in values.
	 * @return
	 */
	private static Goal loadSavedGoal() {
		return new Goal(null);
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
	 * Construct a new {@link Location} by reading in values.
	 * @return New Location
	 */
	private static Location loadLocation() {
		return new Location(
				Integer.parseInt(nextItem().replace("(", "").trim()),
				Integer.parseInt(nextItem().replace(")", "").trim())
				);
	}
	
	/**
	 * Construct a new {@link Player} by reading in values.
	 * @return New Player
	 */
	private static Player loadSavedPlayer() {
		int playerNum = Integer.parseInt(nextItem());
		
		Location location = loadLocation();
		
		Inventory inventory = new Inventory();
		String[] iStrings = in.next("]").replace("[", "").replace("]", "").split(",");
		for (String str : iStrings) {
			inventory.add(new ActionTile(stringToAction(str.trim())));
		}
		
		LocationList locations = new LocationList();
		String[] lStrings = in.next("]").replace("[", "").replace("]", "").split(",");
		for (int i = 0; lStrings.length > i;) {
			locations.add(new Location(
					Integer.parseInt(lStrings[i++].replace("(", "").trim()), 
					Integer.parseInt(lStrings[i++].replace(")", "").trim())));
		}
		
		boolean backtracked = Boolean.parseBoolean(nextItem());

		Profile profile = null;
		if (!"null".equals(nextItem())) {
			int pID = Integer.parseInt(currentItem);
			for (Profile p : Profile.getAll()) {
				profile = (p.getID() == pID) ? p : null;
			}		
		}
		
		return new Player(playerNum, location, inventory, locations, backtracked, profile);
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
	private static Board loadSavedGameBoard(File f) throws FileNotFoundException {
		in = new Scanner(f);
		in.useDelimiter(",");
		
		Board b = null;
		if ("{Board".equals(nextItem())) {
			b = loadSavedBoard(f.getName());
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
		if (!nextItem().startsWith("[")) {
			
		}
		currentItem = currentItem.replace("[", "").trim();
		while (!currentItem.endsWith("]")) {
			directions.add(stringToDirection(currentItem));
			nextItem();
		}
		directions.add(stringToDirection(currentItem.replace("[", "").trim()));
		
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
	
	public static void loadProfiles() throws FileNotFoundException {
		loadProfiles(new File(PROFILE_FILE_PATH));
	}
	
	/**
	 * Recreate a saved game from it's save-file, by loading Profiles, filling the SilkBag, and creating the Board.
	 * @param fname Name of file within saved-game directory, default: {@code ".labyrinth/saved/"}.
	 * @return Loaded {@link Board} to start playing via.
	 * @throws FileNotFoundException
	 * @throws FileParseException
	 */
	public static Board loadSavedGame(String fname) throws FileNotFoundException, FileParseException {
		return loadSavedGameBoard(new File(SAVED_DIR_PATH + fname));
	}
	
	/**
	 * Create a new game from a template-file.
	 * @param f template-file to read, within templates directory (default: {@code ".lairofdagon/templates/"}
	 * @param profiles 
	 * @param numPlayers 
	 * @param saveFileName 
	 * @return A new Board
	 * @throws FileNotFoundException
	 */
	private static Board loadNewGameBoard(File f, String saveFileName, int numPlayers, Profile[] profiles) throws FileNotFoundException {
		in = new Scanner(f);
		in.useDelimiter(",");

		if ("{SilkBag".equals(nextItem())) {
			fillSilkBag();
		} else {
			// TODO: exception here
		}
		
		Board b = null;
		if ("{Board".equals(nextItem())) {
			b = loadNewBoard(saveFileName, numPlayers, profiles);
		} else {
			// TODO: exception here
		}
		
		in.close();
		return b;
	}
	
	/**
	 * Construct a new Board by reading in values.
	 * @param profiles 
	 * @param numPlayers 
	 * @return New {@link Board}
	 */
	private static Board loadNewBoard(String fname, int numPlayers, Profile[] profiles) {
		int boardID = Integer.parseInt(nextItem());
		int width = Integer.parseInt(nextItem());
		int height = Integer.parseInt(nextItem());
		FloorTile[][] b = new FloorTile[width][height];
		Location loc;
		while (!"{Player".equals(nextItem())) {
			switch (currentItem) {
			case "{FloorTile":
				FloorTile t = loadNewFloorTile();
				loc = t.getLocation();
				b[loc.getX()][loc.getY()] = t;
				break;
			case "{Goal":
				Goal g = loadNewGoal();
				loc = g.getLocation();
				b[loc.getX()][loc.getY()] = g;
				break;
			default:
				// TODO: exception here
			}
		}
		
		ArrayList<Player> players = new ArrayList<Player>();
		while ("{Players".equals(currentItem)) {
			players.add(loadNewPlayer(profiles));
			nextItem();
		}
		
		if ("Board}".equals(currentItem)) {
			
		}
		
		for (int x = 0; width > x; x++) {
			for (int y = 0; height > y; y++) {
				if (null == b[x][y]) {
					b[x][y] = SilkBag.removeFloorTile();
				}
			}
		}
		
		return new Board(fname, boardID, width, height, b, (Player[]) players.toArray(), height);
	}
	
	/**
	 * Constructs a <b>fixed</b> {@link FloorTile} from values read from a template file.
	 * @return FloorTile for a new game
	 */
	private static FloorTile loadNewFloorTile() {
		Location location = loadLocation();
		
		ArrayList<Direction> directions = loadDirectionList();
		
		nextItem(); // Consume "FloorTile}"
		
		return new FloorTile(directions, location, null, null, 0, true);
	}
		
	private static Player loadNewPlayer(Profile[] profiles) {
		int playerNum = Integer.parseInt(nextItem());
		Location startLocation = loadLocation();
		
		nextItem();
		
		return new Player(playerNum, startLocation, profiles[playerNum]);
	}
	
	/**
	 * Constructs a {@link Goal} from values read from a template file.
	 * @return Goal for a new game.
	 */
	private static Goal loadNewGoal() {
		Location location = loadLocation();
		
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
	public static Board loadNewGame(String fname, String saveFileName, int numPlayers, Profile[] profiles) throws FileNotFoundException, FileParseException {
		return loadNewGameBoard(new File(TEMPLATES_DIR_PATH + fname), saveFileName, numPlayers, profiles);
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
		writer.append(new SilkBag().saveFormat());
		writer.close();
		
		f = new File(PROFILE_FILE_PATH);
		f.createNewFile();
		writer = new FileWriter(f, true);
		writer.write(""); // Empty file
		for (Profile p : Profile.getAll()) {
			writer.append(p.saveFormat() + ",\n");
		}
		writer.close();
		
		return created;
	}
	
	public static void main(String args[]) {
		try {
			Profile[] profiles = {null, null, null, null};
			loadNewGame("new1", "save1", 4, profiles);
		} catch (FileNotFoundException | FileParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Loaded board");
	}
	
}
