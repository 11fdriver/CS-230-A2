package group_20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {
	
	private static Scanner in;
	
	private static String nextItem() {
		return in.next().trim();
	}
	
	private static FloorTile[][] constructBoard() throws FileParseException {
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
 		
		FloorTile[][] b = new FloorTile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				str = nextItem();
				if (!"{FloorTile".equals(str)) {
					throw new FileParseException("Expected '{FloorTile', got '" + str + "'");
				}
				b[x][y] = constructFloorTile();
				b[x][y].setLocation(new Location(x, y));
			}
		}
		
		if (!"Board}".equals(nextItem())) {
			throw new FileParseException("Board not closed");
		}
		
		return b;
	}
	
	private static void fillSilkBag() throws FileParseException {
		String str = nextItem();
		while (!"SilkBag}".equals(str)) {
			switch (str) {
			case "{FloorTile":
				System.out.println("Constructing FloorTile");
				constructFloorTile();
				// SilkBag.addTile(constructFloorTile());
				break;
			case "{ActionTile":
				System.out.println("Constructing ActionTile");
				constructActionTile();
				// SilkBag.addTile(constructActionTile());
				break;
			default:
				throw new FileParseException("Expected FloorTile or ActionTile, got '" + str + "'");
			}
			str = nextItem();
		} 
	}

	private static FloorTile constructFloorTile() throws FileParseException {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		Location location = null;
		Player player = null;
		FloorAction action = null;
		int lifetime = 0;
		
		// List of Directions
		String str = nextItem();
		if ('[' != str.charAt(0)) {
			throw new FileParseException("Expected '[<Direction>]', got '" + str + "'");
		}
		str = str.replace("[", "");
		do {
			directions.add(stringToDirection(str));
			str = nextItem();
		} while (!str.endsWith("]"));
		directions.add(stringToDirection(str.substring(0, str.length()-1)));
		directions.trimToSize();
		
		// Create player
		str = nextItem();
		switch (str) {
		case "null": 
			break;
		case "{Player": 
			player = constructPlayer();
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
	
	private static Player constructPlayer() {
		return null; //TODO
	}
	
	private static ActionTile constructActionTile() throws FileParseException {
		Action action = stringToAction(nextItem());
		
		if (!"ActionTile}".equals(nextItem())) {
			throw new FileParseException("ActionTile not closed");
		}
		
		return new ActionTile(action);
	}
	
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
	
	private static Profile constructProfile(Scanner in) {
		return null;
	}
	
	private static FloorTile[][] loadGame(File f) throws FileNotFoundException, FileParseException {
		in = new Scanner(f);
		in.useDelimiter(",");
		
		FloorTile[][] b = null;
		while (in.hasNext()) {
			String str = nextItem();
			switch (str) {
			case "{Board":
				System.out.println("Constructing Board");
				b = constructBoard();
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
	
	private static constructProfile() {
		int id = Integer.parseInt(nextItem());
		String name = nextItem();
		int wins = Integer.parseInt(nextItem());
		int losses = Integer.parseInt(nextItem());
		int played = Integer.parseInt(nextItem());
		return new Profile(id, name, wins, losses, played);
	}
	
	private static ArrayList<Profile> loadProfiles(File f) throws FileNotFoundException, FileParseException{
		in = new Scanner(f);
		in.useDelimiter(",");
		while (in.hasNext()) {
			String str = nextItem();
			switch (str) {
			case "{Profile":
				constructProfile();
				break;
			default:
				throw new FileParseException("Expected '{Profile', got '" + str + "'");
			}
		}
	}
	
	public static void main(String args[]) {
		
		try {
			loadGame(new File("GameParserTest"));
		} catch (Exception e) {
			System.out.println(e);
			//System.exit(1);
		}
		
	}
}
