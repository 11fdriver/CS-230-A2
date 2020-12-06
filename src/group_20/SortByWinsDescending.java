import java.util.*;

public class SortByWinsDescending implements Comparator<Profile> {
    private static int compareBoardID = 0;

    public SortByWinsDescending(int compareBoardID) {
        compareBoardID = compareBoardID;
    }
    
    public int compare(Profile x, Profile y) {
        return y.getWins(compareBoardID) - x.getWins(compareBoardID);
    }
}