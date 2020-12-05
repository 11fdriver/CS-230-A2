package group_20;

abstract public class Save extends IO {
    public abstract void save();

    public abstract String toString();
        /*
        will have the board data parsed into it and convert it to one big string
        that will then be passed back into the save method (of its respective subclass)
         and be committed to a file of the filename that was inherited from IO().
         saveProfile and saveGame take in different data and will need to perform
         different operations to do a successful toString() so it will override in
         each class
         */
}
