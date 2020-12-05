

import java.util.ArrayList;

/**
 * Class predominantly allowing for {@link Action}-affected {@link FloorTiles} to understand the passing of turns.
 * Specifically, this class implements pseudo observer-pattern behaviour.
 * This should be used at the <b>end</b> of turns, probably by {@link Player}. 
 */
public class TurnNotifier {
	/**
	 * List of currently subscribed objects.
	 */
	static ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();

	/**
	 * Tell subscribers that a turn ended.
	 */
	public static void notifySubscribers() {
		subscribers.forEach((s) -> s.update());
	}
	
	/**
	 * Allow a class to receive updates when turns end.
	 * @param s	{@link Subscriber}
	 */
	public static void addSubscriber(Subscriber s) {
		if (! subscribers.contains(s)) {
			subscribers.add(s);
		}
	}
	
	/**
	 * Stop turn-end updates.
	 * @param s	{@link Subscriber}
	 */
	public static void delSubscriber(Subscriber s) {
		subscribers.remove(s);
	}
}
