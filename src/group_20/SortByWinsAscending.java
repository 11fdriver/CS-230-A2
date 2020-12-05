

import java.util.*;

public class SortByWinsAscending implements Comparator<Profile> {
    public int compare(Profile x, Profile y) {
        return x.getWins(Leaderboard.getBoardIDToCompare()) - y.getWins(Leaderboard.getBoardIDToCompare());
    }
}