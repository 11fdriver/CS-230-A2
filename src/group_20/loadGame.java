public class loadGame {

    public load(){ //needs a more appropriate name (see superclass)
        /*
        inherits from the load() method in the Load class
         */
    }

    public Board loadNewGame(String fileName){
        /*
        loads a new game from a template file and randomly places tiles
        in the template file's silk bag in the empty spaces on the board,
        it then returns the newly instantiate board data structure to the
        place from whence it was called.

        ***you do construct a board in this method***

        It is the responsibility of gameSetup to place the players on the board.

        will have parsed in template file names (which will have some kind of identifier
        in their filename to distinguish them from save files, or probably be kept in a
        separate folder) as opposed to save file names.
         */
        return null;//returns null for now because i dont like red lines under my code
    }

    public Board loadSavedGame(String fileName){
        /*
        loads a saved game file of the name of which is parsed in,
        including player positions
        board will have everything filled in
         */
    }

    //**********Stuff that isn't on the design but is probably useful:**********

    public String getSaveFileNames(){
        /*
        returns the names of all the save files so that
        the user can select which file to load
         */
        return null;
    }

    public String getTemplateFileNames(){
        /*
        returns a list of the template files or different
        "maps" a player can play so that they can select
        which "map" to set up a game for
         */
        return null;
    }
}

/*
need to pass a list of floor tiles and a list of locations (ordered list of floor tiles)
 */