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

public class Game2 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private String gameTitle;
    private Room hall, hallRight, hallLeft, hallForward, study, library, bathroom, 
            kitchen, upstairs, basement, dining, lounge, exit;
    private Person player,blake;
    private Item placeholder;
        
    /**
     * Create the game and initialise its internal map, items
     * and characters.
     */
    public Game2() 
    {
        createRooms();
        createItems();
        createCharacters();
        parser = new Parser();
        gameTitle = "*Game Title Pending*";
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        hall = new Room("in the main hall");
        hallRight = new Room("in the main hall");
        hallLeft = new Room("in the main hall");
        hallForward = new Room("in the main hall");
        study = new Room("in the study");
        library = new Room("in the library");
        bathroom = new Room("in the bathroom");
        kitchen = new Room("in the kitchen");
        upstairs = new Room("on the landing of the second floor");
        basement = new Room("in the basement");
        dining = new Room("in the dining room");
        lounge = new Room("in the lounge");
        exit = new Room("outside");
        
        // initialise room exits
        hall.setExit("right", hallRight);
        hall.setExit("left", hallLeft);
        hall.setExit("forward", hallForward);
        hall.setExit("front door", exit);
        
        hallForward.setExit("right door", kitchen);
        hallForward.setExit("upstairs", upstairs);
        hallForward.setExit("back", hall);
        
        hallLeft.setExit("right door", basement);
        hallLeft.setExit("middle door", dining);
        hallLeft.setExit("left door", lounge);
        hallLeft.setExit("back", hall);
        
        hallRight.setExit("right door", study);
        hallRight.setExit("middle door", library);
        hallRight.setExit("left door", bathroom);
        hallRight.setExit("back", hall);
        
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
    }
    
    /**
     * Create items and place them in a room
     */
    private void createItems()
    {
        placeholder = new Item("placeholder", null, 0);
    }
    
    /**
     *  Create characters and place them in the map
     */
    private void createCharacters()
    {
        player = new Person(5, exit);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        wait(2000);
        System.out.println("~Thank goodness you're here! I've been stuck in this game forever. " +
                            "\n    I can't figure out how to win. Will you help me?");
        System.out.println("\nHelp the trapped player? \nyes no");
        
        boolean wantToQuit = false;
        boolean done = false;
        boolean helped = true;
        int noCounter = 0;
        int unknownCounter = 0;
        
        while (!done) {
            switch (parser.getCommand().getCommandWord()) {
                case UNKNOWN:
                    unknownCounter ++;
                    switch (unknownCounter) {
                        case 1:
                            System.out.println("Trapped Player: Whoa pal, you need to use words the game " +
                                                "will recognize.\n");
                            wait(1000);
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 2:
                            System.out.println("Trapped Player: Buddy, you gotta work with me here. Type yes or no." +
                                                "\n    But preferably yes.\n");
                            wait(1000);
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 3:
                            System.out.println("Trapped Player: Now come on, just type y e s, you can do it!");
                            wait(2000);
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 4:
                            System.out.println("Trapped Player: You know what buddy, don't worry about it." +
                                            "\n    I got you.\n");
                            System.out.println("Help the trapped player? \nyes no");
                            System.out.print("> ");
                            wait(1000);
                            System.out.print("y");
                            wait(500);
                            System.out.print("e");
                            wait(500);
                            System.out.print("s");
                            done = true;
                            break;
                    }
                    break;
                    
                case YES:
                    System.out.println("Trapped Player: Awesome! Thank you so much. Let's go!");
                    done = true;
                    break;
                    
                case NO:
                    noCounter++;
                    switch (noCounter) {
                        case 1:
                            System.out.println("Trapped Player: Wait man, are you sure? I could " +
                                                "really use a hand.\n");
                            wait(2000);
                            System.out.print("P"); wait(200); System.out.print("l"); wait(200);
                            System.out.print("e"); wait(200); System.out.print("a"); wait(200);
                            System.out.print("s"); wait(200); System.out.print("e ");
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 2:
                            System.out.println("Trapped Player: Can I say something to change your mind?\n");
                            wait(2000);
                            System.out.print("P"); wait(200); System.out.print("r"); wait(200);
                            System.out.print("e"); wait(200); System.out.print("t"); wait(200);
                            System.out.print("t"); wait(200); System.out.print("y "); wait(200);
                            System.out.print("p"); wait(200); System.out.print("l"); wait(200);
                            System.out.print("e"); wait(200); System.out.print("a"); wait(200);
                            System.out.print("s"); wait(200); System.out.print("e "); 
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 3:
                            System.out.println("Trapped Player: Oh, alright. Well that's cool, I guess. Maybe I'll " +
                                            "see you later. \n    Thanks anyway.");
                            done = true;
                            helped = false;
                            break;
                    }
                    break;
                    
                case QUIT:
                    System.out.println("Trapped Player: Already? Okay, I guess I'll figure it out on my own.");
                    done = true;
                    wantToQuit = true;
                    break;
                    
                default:
                    System.out.println("Trapped Player: You're getting ahead of yourself, there. Before that, " +
                                        "\n\twill you help me?\n");
                    System.out.println("Help the trapped player? \nyes no");
                    break;
            }
        }
        
        while (!wantToQuit) {
            printWelcome();
            
        }
        
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        /*        
        boolean finished = false;
        while (! finished) {
            command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
        */
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("                           Welcome to the *Game Title Pending*");
        System.out.println("                                 By Claire Iroudayassamy");
        System.out.println("            *Game Title Pending* is a new, incredibly unfinished adventure game.");
        System.out.println("                     Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println();
        
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
                printObjective();
                printCommands();
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

    /**
     * Print out objective information.
     */
    private void printObjective() 
    {
        //TODO print more helpful info
        System.out.println("You are doing something, I reckon.");
        printCommands();
    }
    
    /**
     * Print out all command words and a brief description
     * of how they can be used.
     */
    private void printCommands()
    {
        System.out.println("Your command words are:");
        //TODO add commands
        
    }

    /** 
     * Go to a new room.
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
            //the direction is only one word
            direction = command.getSecondWord();
        } else if(command.hasThirdWord()) {
            //the direction is two words
            direction = command.getSecondWord() + " " + command.getThirdWord();
        }

        // Try to leave current room.
        player.goRoom(direction);
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
    
    /**
     *  Make the program "wait" for specified amount of time.
     *  @param amount The amount of time to wait.
     */
    private void wait(int amount)
    {
        try
        {
            Thread.sleep(amount);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Play the game alone.
     */
    private void playAlone()
    {
        
    }
    
    /**
     *  Play the game with the trapped player.
     */
    private void playTogether()
    {
        
    }
    
    public void test()
    {
        
    }
}
