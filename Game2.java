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
            kitchen, upstairs, masterBedroom, childBedroom, closet,
            basement, dining, lounge, exit, backyard;
    private Person player,blake;
    private Item placeholder;
    private boolean wantToQuit;
    private boolean helped;
        
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
        hallRight = new Room("looking at the right wall of the main hall");
        hallLeft = new Room("looking at the left wall of the main hall");
        hallForward = new Room("looking at the back wall of the main hall");
        study = new Room("in the study");
        library = new Room("in the library");
        bathroom = new Room("in the bathroom");
        kitchen = new Room("in the kitchen");
        upstairs = new Room("on the landing of the second floor");
        masterBedroom = new Room("in the master bedroom");
        childBedroom = new Room("in a child's bedroom");
        closet = new Room("in a closet");
        basement = new Room("in the basement");
        dining = new Room("in the dining room");
        lounge = new Room("in the lounge");
        exit = new Room("in the front yard");
        backyard = new Room("in the back yard");
        
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
        
        upstairs.setExit("downstairs", hall);
        upstairs.setExit("right", masterBedroom);
        upstairs.setExit("left", childBedroom);
        
        masterBedroom.setExit("back", upstairs);
        
        childBedroom.setExit("upstairs landing", upstairs);
        childBedroom.setExit("closet", closet);
        
        closet.setExit("back", childBedroom);
        
        basement.setExit("upstairs", hall);
        basement.setExit("cellar door", backyard);
        
        dining.setExit("main hall", hall);
        
        lounge.setExit("main hall", hall);
        
        exit.setExit("front door", hall);
        exit.setExit("gate", backyard);
        
        backyard.setExit("cellar door", basement);
        backyard.setExit("gate", exit);
        
        //initialize room detailed description
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
        exit.setLookDescription("You are outside in a courtyard, and in front of you stands an opulent mansion. To " +
                                 "\nthe left you can see a gate that you assume leads to the backyard.");
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
        player = new Person("Player", 5, exit);
    }

    /**
     *  Main play routine.
     */
    public void play() 
    {            
        printWelcome();
        wait(2000);
        System.out.println("~Thank goodness you're here! I've been stuck in this game forever. " +
                            "\n    I can't figure out how to win. Will you help me?");
        System.out.println("\nHelp the trapped player? \nyes no");
        
        wantToQuit = false;
        boolean done = false;
        helped = true;
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
                            System.out.println("Trapped Player: Now come on, just type y e s, you can do it!\n");
                            wait(2000);
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 4:
                            System.out.println("Trapped Player: You know what buddy, don't worry about it." +
                                            "\n    I got you.\n");
                            wait(2500);
                            System.out.println("Help the trapped player? \nyes no");
                            System.out.print("> ");
                            wait(1000);
                            System.out.print("y");
                            wait(500);
                            System.out.print("e");
                            wait(500);
                            System.out.print("s");
                            wait(1500);
                            done = true;
                            break;
                    }
                    break;
                    
                case YES:
                    System.out.println("Trapped Player: Awesome! Thank you so much. Let's go!");
                    done = true;
                    wait(1500);
                    break;
                    
                case NO:
                    noCounter++;
                    switch (noCounter) {
                        case 1:
                            System.out.println("Trapped Player: Wait man, are you sure? I could " +
                                                "really use a hand.\n");
                            wait(2000);
                            System.out.println("Help the trapped player? \nyes no");
                            break;
                        case 2:
                            System.out.println("Trapped Player: Is there anything I can I say something to change " +
                                               "your mind?\n");
                            wait(2000); 
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
                    System.out.println("Are you sure you want to quit? \nyes no");
                    if(parser.getCommand().getCommandWord().toString().equals("yes")) {
                        System.out.println("Trapped Player: Already? Okay, I guess I'll figure it out on my own.\n");
                        done = true;
                        wantToQuit = true;
                        break;
                    } else {
                        System.out.println("Help the trapped player? \nyes no");
                        break;
                    }
                    
                default:
                    System.out.println("Trapped Player: You're getting ahead of yourself, there. Before that, " +
                                        "\n\twill you help me?\n");
                    System.out.println("Help the trapped player? \nyes no");
                    break;
            }
        }
        
        if(!wantToQuit) {
            if(helped) {
                playTogether();
            } else {
                playAlone();
            }
        } else {
            System.out.println("Thank you for playing.  Good bye.");
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
     * Play the game with the trapped player.
     */
    private void playTogether()
    {
        printWelcome();
        printTwoGameDescription();
        System.out.println(player.getCurrentRoom().getLookDescription());
        wait(6000);
        System.out.println();
        System.out.println("Blake: Haha, how was my acting? So, we need to find the murder weapon and figure out " +
                           "\n    who committed the murder in order to beat the game. I haven't been able to do either," +
                           "\n    and my quit command is broken, hence why I'm stuck here.\n");
        wait(7000);
        System.out.println(player.getCurrentRoom().getLongDescription());
        Command command;
        while(!wantToQuit) {
            command = parser.getCommand();
            switch(command.getCommandWord()) {
                case GO:
                    goRoom(command);
                    break;
                
                case LOOK:
                    System.out.println(player.getCurrentRoom().getLookDescription());
                    break;
                    
                case HELP:
                    break;
                
                case QUIT:
                    wantToQuit = quit();
                    break;
                
                case UNKNOWN:
                    System.out.println("I don't know what you mean...");
                    break;
                
                default:
                    System.out.println("You cannot do that right now.");
                
                
            }
            
        }
    }
    
    /**
     * Play the game in single player mode.
     */
    private void playAlone()
    {
        printWelcome();
        printGameDescription();
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
        System.out.println("                              Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println();
        
    }
    
    /**
     * Print out the two player game description message 
     * for the player.
     */
    private void printTwoGameDescription()
    {
        System.out.println("You are a detective looking into the unsolved murder of Mr. Charles Bodie. You've just" +
                           "\nreceived notice that your request for a warrant of Mr. Bodie's house has been granted," +
                           "\nso you and your partner head out to gather evidence.\n");
        wait(7000);
        System.out.println("Trapped Player: Oh hey! That's me; I'm your parnter! My name's Blake, by the way.");
        System.out.println();
        wait(4000);
        System.out.println("On your drive over to the house, you and your partner go over the case.");
        System.out.println();
        wait(3000);
        System.out.println("Blake: So apparently, Bodie was having a dinner party when the power in the house suddenly" +
                           "\n    went out. One of the guests says that she was in the study with Bodie when the lights" +
                           "\n    shut off and when they came back on, she claims that Bodie was dead on the ground." +
                           "\n    She didn't see anything or hear anything. There was no one else in the study to back" +
                           "\n    up her statements but we haven't found anything solid enough to convict her.");
        wait(11000);
        System.out.println("Blake: Some of the guests have been staying with Mr. Bodie; something about a vacation, so " +
                           "\n    we should be able to talk to some of them. Let's see if we can't close this case" +
                           "\n    tonight, I've got tacos waiting for me at home.");
        System.out.println();
        wait(7000);
        System.out.println("You arrive at the house and get out of the car.");
    }
    
    /**
     * Print out the single player game description message
     * for the player.
     */
    private void printGameDescription()
    {
        System.out.println("You are a detective looking into the unsolved murder of Mr. Charles Bodie. You've just" +
                           "\nreceived notice that your request for a warrant of Mr. Bodie's house has been granted," +
                           "\nso you head out to gather evidence.");
        wait(3000);
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
                wantToQuit = quit();
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
        System.out.println(player.getCurrentRoom().getLongDescription());
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
    private boolean quit() 
    {
        System.out.println("Are you sure you want to quit? \nyes no");
        if(parser.getCommand().getCommandWord().toString().equals("yes")) {
            if(helped) {
                System.out.println("Blake: Wait! Don't lea");
                wait(200);
            }
            System.out.println("Thank you for playing.  Good bye.");
            return true; // signal that we want to quit
        } else {
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
        return false;
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
    
    public void test()
    {
        
    }
}
