package group_20;


public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	/**
	 * Reverses a Direction. North -> South, East -> West.
	 * @return Opposite direction
	 */
	Direction opposite() {
		switch (this) {
		case NORTH: return Direction.SOUTH;
		case EAST: return Direction.WEST;
		case SOUTH: return Direction.NORTH;
		case WEST: return Direction.EAST;
		default: return null; // Needed for compilation
		}
	}
}
