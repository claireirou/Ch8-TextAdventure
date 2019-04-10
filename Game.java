import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private final int version = new Random().nextInt(3);
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room hall, hallRight, hallLeft, hallForward, study, library, bathroom, 
        kitchen, upstairs, basement, dining, lounge, exit;
      
        // create the rooms
        hall = new Room("in the main hall", false);
        hallRight = new Room("in the main hall", false);
        hallLeft = new Room("in the main hall", false);
        hallForward = new Room("in the main hall", false);
        study = new Room("in the study", false);
        library = new Room("in the library", false);
        bathroom = new Room("in the bathroom", false);
        kitchen = new Room("in the kitchen", false);
        upstairs = new Room("on the landing of the second floor", false);
        basement = new Room("in the basement", false);
        dining = new Room("in the dining room", false);
        lounge = new Room("in the lounge", false);
        exit = new Room("outside", false);
        
        // initialise room exits
        hall.setExit("right", hallRight);
        hall.setExit("left", hallLeft);
        hall.setExit("forward", hallForward);
        hall.setExit("front door", exit);
        
        hallForward.setExit("right door", kitchen);
        hallForward.setExit("staircase", upstairs);
        
        hallLeft.setExit("right door", basement);
        hallLeft.setExit("middle door", dining);
        hallLeft.setExit("left door", lounge);
        
        hallRight.setExit("right door", study);
        hallRight.setExit("middle door", library);
        hallRight.setExit("left door", bathroom);
        
        study.setExit("main hall", hall);
        
        library.setExit("main hall", hall);
        
        bathroom.setExit("main hall", hall);
        
        kitchen.setExit("main hall", hall);
        
        upstairs.setExit("main hall", hall);
        
        basement.setExit("main hall", hall);
        
        dining.setExit("main hall", hall);
        
        lounge.setExit("main hall", hall);
        
        exit.setExit("front door", hall);
        
        // initialize room look description
        hall.setLookDescription("");
        hallRight.setLookDescription("");
        hallLeft.setLookDescription("");
        hallForward.setLookDescription("");
        study.setLookDescription("");
        library.setLookDescription("");
        bathroom.setLookDescription("");
        kitchen.setLookDescription("");
        upstairs.setLookDescription("");
        basement.setLookDescription("");
        dining.setLookDescription("");
        lounge.setLookDescription("");
        exit.setLookDescription("");

        currentRoom = exit;  // start game outside
    }
    
    /**
     * Create items and place them in a room
     */
    public void createItems()
    {
        
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the *Game Title Pending*");
        System.out.println("*Game Title Pending* is a new, incredibly unoriginal adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        String direction = null;
        if(!command.hasThirdWord()) {
             direction = command.getSecondWord();
        } else if(command.hasThirdWord()) {
             direction = command.getSecondWord() + " " + command.getThirdWord();
        }

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
