import java.util.*;

public class SortByWinsAscending implements Comparator<Profile> {
    private static int compareBoardID = 0;

    public SortByWinsAscending(int compareBoardID) {
        compareBoardID = compareBoardID;
    }
    
    public int compare(Profile x, Profile y) {
        return x.getWins(compareBoardID) - y.getWins(compareBoardID);
    }
}