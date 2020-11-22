import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class loadGame {

    public load(){ //needs a more appropriate name (see superclass)
        /*
        inherits from the load() method in the Load class
         */
    }

    public Board loadNewGame(String fileName) throws FileNotFoundException {
        /*
        loads a new game from a template file and randomly places tiles
        in the template file's silk bag in the empty spaces on the board,
        it then returns the newly instantiated board data structure to the
        place from whence it was called.
        ***you do construct a board in this method***
        It is the responsibility of gameSetup to place the players on the board.
        will have parsed in template file names (which will have some kind of identifier
        in their filename to distinguish them from save files, or probably be kept in a
        separate folder) as opposed to save file names.
         */

        File file = new File(fileName);
        Scanner in = new Scanner(file);
        ArrayList tilesArray = new ArrayList<Tile>();
        ArrayList indexArray = new ArrayList<(Integer[])>();
        try{
            //gets the x and y of the Board
            int[] size = in.next();
            int x = size[0];
            int y = size[1];

            //reads all the tiles and instantiates them in an ArrayList
            Scanner tileScanner = new Scanner(file).useDelimiter(",");
            for(int i = 0; i < (y)-1; i++ ){
                for(int j = 0; j < (x)-1; j++){
                    ArrayList tileIn = tileScanner.next();
                    tilesArray.add(new Tile(tileIn[0],tileIn[1],tileIn[2],tileIn[3],tileIn[4])); //tileIn[1] is the tile type
                    indexArray.add([i,j]);
                }
            }

            //reads all the tiles in the silk bag and returns them as an ArrayList
            Scanner silkScanner = new Scanner(file);
            ArrayList silkBag = new ArrayList<int>
            for(int i = 0; i < y-1; i++){
              silkScanner.nextLine();
            }
            String silkBagInOneString = silkScanner.nextLine();
            String silkBag = silkBagInOneString.split(",");


        }catch(Exception e){
            System.out.println("error, you probably entered the wrong file, idiot.")
        }
        return tilesArray, indexArray;
    }



    public Board loadSavedGame(String fileName) throws FileNotFoundException{
        /*
        loads a saved game file of the name of which is parsed in,
        including player positions
        board will have everything filled in
         */
         File file = new File(fileName);
         Scanner in = new Scanner(file);
         ArrayList tilesArray = new ArrayList<Tile>();
         ArrayList indexArray = new ArrayList<(Integer[])>();
         try{

           int[] size = in.next();
             int x = size[0];
             int y = size[1];


             for(int i = 0; i < (y)-1; i++ ){
                 for(int j = 0; j < (x)-1; j++){
                     ArrayList tileIn = tileScanner.next();
                     tilesArray.add(new Tile(tileIn[0],tileIn[1],tileIn[2],tileIn[3],tileIn[4])); //tileIn[1] is the tile type
                     indexArray.add([i,j]);
                 }
             }

         }catch(Exception e){
             System.out.println("error, you probably entered the wrong file, idiot.")
         }
         return tilesArray, indexArray;
     }
    }

    //**********Stuff that isn't on the design but is probably useful:**********


    //these two methods are made under the assumption that save files and template files are stored in separate folders
    //these two folders would be stored inside D:\\src\ for example

    public String getSaveFileNames(){

         File directoryPath = new File("src\saves");
         String saveFiles[] = directoryPath.list();
         return saveFiles;
    }

    public String getTemplateFileNames(){

         File directoryPath = new File("src\templates");
         String templateFiles = directoryPath.list();
         return templateFiles;
    }
}

/*
need to pass a list of floor tiles and a list of locations (ordered list of floor tiles)
 */
