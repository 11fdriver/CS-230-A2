package group_20;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LoadProfile {
    public String[] load() throws IOException { //needs a more appropriate name (see superclass)
        /*
        inherits from the load() method in the Load class and reads the save/Profile
        file for the Profiles and returns them as an array or dictionary or whatever
        if they have other data attached to their names
         */


        try {
            File Profiles = new File("profiles.txt");
            Scanner in = new Scanner(Profiles).useDelimiter(",");
            Path path = Paths.get(String.valueOf(Profiles)); //gets the number of lines in the text file profiles.txt
            int lineCount = (int) Files.lines(path).count(); //<-----┘
            String[] profileList = new String[lineCount];

            for(int i = 0; i< lineCount-1; i++){
                profileList[i] = in.next() + ": " + in.next();
                in.nextLine();
                /*
                The profiles file will be structured as:
                profileName1, 13
                profileName2, 14
                profileName3, 6

                when processed by the code it will be reformatted to a list as such:
                ["profileName1: 13, "profileName2: 14", "ProfileName3: 6"]
                 */
            }
        }catch (IOException e){
            e.printStackTrace();
        }




        return null;
    }
}
