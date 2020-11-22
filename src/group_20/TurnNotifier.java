package group_20;

import java.util.ArrayList;

public class TurnNotifier {
	static ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();

	public static void notifySubscribers() {
		subscribers.forEach((s) -> s.update());
	}
	
	public static void addSubscriber(Subscriber s) {
		if (! subscribers.contains(s)) {
			subscribers.add(s);
		}
	}
	
	public static void delSubscriber(Subscriber s) {
		subscribers.remove(s);
	}
}
