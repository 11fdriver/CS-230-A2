import java.util.*;

public class SortByWinsDescending implements Comparator<Profile> {
    public int compare(Profile x, Profile y) {
        return y.getWins(Leaderboard.getBoardIDToCompare()) - x.getWins(Leaderboard.getBoardIDToCompare());
    }
}