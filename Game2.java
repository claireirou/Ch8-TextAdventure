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
    private Room hall, hallRight, hallLeft, hallForward, study, library, bathroom, kitchen, freezer, fridge, 
            upstairs, masterBedroom, masterBath, childBedroom, closet, basement, dining, lounge, exit, backyard;
    private Person player, blake, ruby, olive, ash, cole, ginger, jett;
    private Item coffeeTable, desk, bookcase, fireplace, drinkingCart, bookshelves, windowSeat, sideTable, 
            cabinet, toilet, tiles, ovens, stovetop, pot, cupboards, crates, metalShelves, boxes, carcass,
            table, vase, candle, bed, nightstand, tub, shower, jewelryBox, vanity, rug, floorboard, bunkBed,
            smallDesk, clothes, readingNook, book, woodShelves, mower, diningTable, plateSettings, candelabrum, 
            cigarBox,drinkingCart2;
    private Item canister, toolbox, basementKey, closetKey, blueprints, hammer, cigars, studyDrawer1, studyDrawer2, 
            childDrawer, nightDrawer, knife, freezerKey, kitchenKnife, placeholderKey, placeholderKey2, backpack;
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
    
    public static void main (String[] args)
    {
        Game2 game = new Game2();
        game.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        hall = new Room("in the main hall", false);
        hallRight = new Room("looking at the right wall of the main hall", false);
        hallLeft = new Room("looking at the left wall of the main hall", false);
        hallForward = new Room("looking at the back wall of the main hall", false);
        study = new Room("in the study", false);
        library = new Room("in the library", false);
        bathroom = new Room("in the bathroom", false);
        kitchen = new Room("in the kitchen", false);
        freezer = new Room("in the freezer", true);
        fridge = new Room("in the refrigerator", false);
        upstairs = new Room("on the landing of the second floor", false);
        masterBedroom = new Room("in the master bedroom", false);
        masterBath = new Room("in the master bathroom", false);
        childBedroom = new Room("in a child's bedroom", false);
        closet = new Room("in a closet", true);
        basement = new Room("in the basement", true);
        dining = new Room("in the dining room", false);
        lounge = new Room("in the lounge", false);
        exit = new Room("in the front yard", false);
        backyard = new Room("in the back yard", false);
        
        // initialise room exits
        hall.setExit("left", hallLeft);
        hall.setExit("forward", hallForward);
        hall.setExit("right", hallRight);
        hall.setExit("front door", exit);
        
        hallForward.setExit("right door", kitchen);
        hallForward.setExit("upstairs", upstairs);
        hallForward.setExit("back", hall);
        
        hallLeft.setExit("left door", lounge);
        hallLeft.setExit("dining room", dining);
        hallLeft.setExit("right door", basement);
        hallLeft.setExit("back", hall);
        
        hallRight.setExit("left door", bathroom);
        hallRight.setExit("middle door", library);
        hallRight.setExit("right door", study);
        hallRight.setExit("back", hall);
        
        study.setExit("main hall", hall);
        
        library.setExit("main hall", hall);
        
        bathroom.setExit("main hall", hall);
        
        kitchen.setExit("fridge", fridge);
        kitchen.setExit("freezer", freezer);
        kitchen.setExit("main hall", hall);
        
        fridge.setExit("back", kitchen);
        
        freezer.setExit("back", kitchen);
        
        upstairs.setExit("left", childBedroom);
        upstairs.setExit("right", masterBedroom);
        upstairs.setExit("downstairs", hall);
        
        masterBedroom.setExit("master bath", masterBath);
        masterBedroom.setExit("back", upstairs);
        
        masterBath.setExit("back", masterBedroom);
        
        childBedroom.setExit("closet", closet);
        childBedroom.setExit("back", upstairs);
        
        closet.setExit("back", childBedroom);
        
        basement.setExit("cellar door", backyard);
        basement.setExit("upstairs", hall);
        
        dining.setExit("main hall", hall);
        
        lounge.setExit("main hall", hall);
        
        exit.setExit("front door", hall);
        exit.setExit("gate", backyard);
        
        backyard.setExit("cellar door", basement);
        backyard.setExit("gate", exit);
        
        //assign detailed room description
        hall.setLookDescription("You are in a grand hall with marble flooring and a tall ceiling. you can see multiple" +
                                "\ndoors lining the walls on both the left and right side. At the back of the hall" +
                                "\nthere is a staircase going up on the left and another door to the right of it.");
        hallRight.setLookDescription("The right wall of the hall has three doors on it. All of them are closed.");
        hallLeft.setLookDescription("The left wall of the hall has two closed doors and a set of double doors propped" +
                                "\nopen that lead to what appears to be a dining room");
        hallForward.setLookDescription("At the back of the hall there is a large staircase to the upper floor to" +
                                "\nyour left and to the right is a single closed door.");
        study.setLookDescription("You are in a study with an ornate wooden desk towards the back of the room. In the" +
                                "\nmiddle of the room there are two couches facing each other with a glasstop coffee" +
                                "\ntable between them. There is a fireplace along the left wall and the right wall" +
                                "\nis lined with bookcases. You can see a small drinking cart to the left of the desk.");
        library.setLookDescription("You are in a library with floor to ceiling bookcases lining the walls. The back" +
                                "\nwall has a large window with a cozy looking window seat. In the center of the "+
                                "\nroom is a small couch and an armchair with a wooden side table next to it.");
        bathroom.setLookDescription("You are in a small half bath with dark floor tiling and a toilet, a sink, and" +
                                "\na medicine cabinet with a mirror door. Some of the tiles around the sink are" +
                                "\ncracked and a little lifted.");
        kitchen.setLookDescription("You are in a large kitchen with granite counters and dark wooden cupboards. An "+
                                "\n oversized center island takes up a large portion of the space and on it sits" +
                                "\na fruit bowl and a flower vase filled with peonies. Along the left wall are" +
                                "\nthree ovens each with six-place stovetops. One of the stovetops has a large" +
                                "\ncovered pot that appears to be simmering. The back wall has a large window with" +
                                "\na wide sink below it. To your right are two large metal doors with silver handles.");
        fridge.setLookDescription("You are standing inside a walk-in refrigerator that is lined with shelves on all" +
                                "\nthree walls. The shelves hold various crates filled with food and ingredients.");
        freezer.setLookDescription("You are inside of a walk-in freezer. It is unbelievably cold. There are shelves" +
                                "\nalong the right and back wall with boxes of food. There are two meat hooks " +
                                "\nhanging from the ceiling, one of which has the carcass of some long dead animal." +
                                "hooked onto it.");
        upstairs.setLookDescription("You are on the landing of the second floor of the house. There's a door to your" +
                                "\nleft and a bit down the hallway, there is a door on the right. On the left" +
                                "\nside of the hallway there is a small table holding a decorative vase with a" +
                                "\nlit candle next to it.");
        masterBedroom.setLookDescription("You are in a master bedroom. There is a large four poster bed against the" +
                                "\nleft wall with nightstands on both sides. There is a door to the master bath" +
                                "\non the right.");
        masterBath.setLookDescription("You are in the master bath. There is a clawfoot bathtub towards the back of" +
                                "\nthe room and a shower stall off to the right. A sink and an extravegant vanity" +
                                "\ntakes up the left wall. There is a small wooden jewelry box on the vanity.");
        childBedroom.setLookDescription("You are in a child's bedroom. Most of the floor is covered by a large rug" +
                                "\nThere is a small wooden desk to your left and a bunk bed in the far right" +
                                "\ncorner. There is a closet door to the right");
        closet.setLookDescription("The left wall of the closet is lined with hanging clothes. There are shelves on" +
                                "\nthe right wall filled with more clothes. Tucked into the far right corner appears" +
                                "\nto be a reading nook with pillows and a discarded book.");
        basement.setLookDescription("You descend down a set of stairs into a sparse basement. It appears to serve" +
                                "\nmainly as a storage space with shelves lining two of the walls and an old mower" +
                                "sitting to the left of you.");
        dining.setLookDescription("You are in a extravagant dinning room. The large table has ten plate settings "+
                                "\nput out and a candelabrum center piece.");
        lounge.setLookDescription("You are in a lounge with two large couches and two leather armchairs. There is a "+
                                "\nside table next to one of the arm chairs with a cigar box on it. There is a" +
                                "\nsmall table that serves as a drinking cart just to the left of the door.");
        exit.setLookDescription("You are outside in a courtyard, and in front of you stands an opulent mansion. To " +
                                 "\nthe left, you can see a gate that you assume leads to the backyard.");
        backyard.setLookDescription("You are in a inclosed side yard that streches the length of the house. There is" +
                                "\na stone wall along the left side that closes off the space at the far end of the" +
                                "\nof the yard. Towards the far edge of the house is a cellar door.");
    }
    
    /**
     * Create items and place them in a room
     */
    private void createItems()
    {
        // create the items
        coffeeTable = new Item("coffee table", 40, true, false); 
        desk = new Item("desk", 90, true, false);
        studyDrawer1 = new Item("top drawer", 15, false, false);
        studyDrawer2 = new Item("bottom drawer", 15, false, false);
        cigars = new Item("cigars", 3, false, true);
        bookcase = new Item("bookcase", 100, true, true);
        fireplace = new Item("fireplace", 1000, true, false);
        drinkingCart = new Item("drinking cart", 50, true, false);
        bookshelves = new Item("bookshelves", 100, true, false);
        windowSeat = new Item("window seat", 1000, true, false);
        sideTable = new Item("side table", 55, true, true);
        cabinet = new Item("cabinet", 55, true, false);
        toilet = new Item("toilet", 75, true, false);
        tiles = new Item("tiles", 1, true, false);
        closetKey = new Item("bronze key", 1, false, true);
        ovens = new Item("ovens", 1000, true, false);
        stovetop = new Item("stovetop", 1000, true, false);
        pot = new Item("pot", 10, true, true);
        cupboards = new Item("cupboards", 100, true, false);
        kitchenKnife = new Item("kitchen knife", 4, false, true);
        crates = new Item("crates", 20, true, false);
        metalShelves = new Item("shelves", 30, true, true);
        boxes = new Item("boxes", 20, true, false);
        carcass = new Item("carcass", 25, true, true);
        table = new Item("table", 40, true, false);
        vase = new Item("vase", 15, true, false);
        candle = new Item("candle", 2, true, true);
        freezerKey = new Item("silver key", 1, false, true);
        bed = new Item("bed", 100, true, true);
        nightstand = new Item("nightstand", 40, true, false);
        nightDrawer = new Item("drawer", 15, false, false);
        basementKey = new Item("gold key", 1, false, true);
        tub = new Item("bathtub", 100, true, true);
        shower = new Item("shower", 1000, true, true);
        jewelryBox = new Item("jewelry box", 5, true, false);
        vanity = new Item("vanity", 1000, true, false);
        rug = new Item("rug", 15, true, true);
        floorboard = new Item("floorboard", 10, false, true);
        placeholderKey2 = new Item("placeholder2", 1, false, true);
        bunkBed = new Item("bunk bed", 60, true, false);
        smallDesk = new Item("small desk", 30, true, false);
        childDrawer = new Item("drawer", 10, false, false);
        clothes = new Item("clothes",15, true, false);
        readingNook = new Item("reading nook", 1000, true, false);
        book = new Item("book", 3, false, true);
        backpack = new Item("backpack", 0, false, false);
        woodShelves = new Item("shelves", 40, true, false);
        mower = new Item("mower", 150, true, false);
        canister = new Item("canister tube", 5, false, false);
        toolbox = new Item("toolbox", 10, false, false);
        blueprints = new Item("blueprints", 2, false, true);
        hammer = new Item("hammer", 5, false, true);
        diningTable = new Item("dining table", 100, true, false);
        plateSettings = new Item("plate settings", 20, true, false);
        knife = new Item("steak knife", 4, false, true);
        candelabrum = new Item("candelabrum", 5, true, true);
        cigarBox = new Item("cigar box", 5, true, false);
        placeholderKey = new Item("placeholder", 1, false, true);
        drinkingCart2 = new Item("small table", 50, true, true);

        // put items inside items
        fillContainer(woodShelves, canister);
        fillContainer(woodShelves, toolbox);
        fillContainer(canister, blueprints);
        fillContainer(toolbox, hammer);
        fillContainer(tiles, closetKey);
        fillContainer(vase, freezerKey);
        fillContainer(desk, studyDrawer1);
        fillContainer(desk, studyDrawer2);
        fillContainer(smallDesk, childDrawer);
        fillContainer(rug, floorboard);
        fillContainer(readingNook, book);
        fillContainer(readingNook, backpack);
        fillContainer(nightstand, nightDrawer);
        fillContainer(studyDrawer2, cigars);
        fillContainer(nightDrawer, basementKey);
        fillContainer(plateSettings, knife);
        fillContainer(cupboards, kitchenKnife);
        fillContainer(cigarBox, placeholderKey);
        fillContainer(floorboard, placeholderKey2);

        //TODO assign details
        coffeeTable.setDetails("");
        desk.setDetails("");
        bookcase.setDetails("");
        fireplace.setDetails("");
        drinkingCart.setDetails("");
        studyDrawer1.setDetails("");
        studyDrawer2.setDetails("");
        cigars.setDetails("");
        bookshelves.setDetails("");
        windowSeat.setDetails("");
        sideTable.setDetails("");
        cabinet.setDetails("");
        toilet.setDetails("");
        tiles.setDetails("");
        closetKey.setDetails("");
        ovens.setDetails("");
        cupboards.setDetails("");
        kitchenKnife.setDetails("");
        stovetop.setDetails("");
        pot.setDetails("");
        crates.setDetails("");
        metalShelves.setDetails("");
        boxes.setDetails("");
        carcass.setDetails("");
        table.setDetails("");
        vase.setDetails("");
        candle.setDetails("");
        bed.setDetails("");
        nightstand.setDetails("");
        basementKey.setDetails("");
        tub.setDetails("");
        shower.setDetails("");
        vanity.setDetails("");
        jewelryBox.setDetails("");
        rug.setDetails("");
        bunkBed.setDetails("");
        smallDesk.setDetails("");
        childDrawer.setDetails("");
        floorboard.setDetails("");
        placeholderKey2.setDetails("");
        clothes.setDetails("");
        readingNook.setDetails("");
        book.setDetails("");
        freezerKey.setDetails("");
        backpack.setDetails("");
        woodShelves.setDetails("");
        mower.setDetails("");
        canister.setDetails("");
        toolbox.setDetails("");
        hammer.setDetails("");
        blueprints.setDetails("");
        diningTable.setDetails("");
        plateSettings.setDetails("");
        candelabrum.setDetails("");
        knife.setDetails("");
        cigarBox.setDetails("");
        drinkingCart2.setDetails("");
        placeholderKey.setDetails("");
    }
    
    /**
     * Assign items to their containers
     */
    private void fillContainer(Item container, Item item)
    {
        container.add(item);
        item.setContainer(container);
    }
    
    /**
     *  Put the items in the rooms
     */
    private void poplulateItems()
    {
        //Add items to rooms
        study.addItem("coffee table", coffeeTable);
        study.addItem("desk", desk);
        study.addItem("bookcase", bookcase);
        study.addItem("fireplace", fireplace);
        study.addItem("drinking cart", drinkingCart);
        study.addItem("top drawer", studyDrawer1);
        study.addItem("bottom drawer", studyDrawer2);
        study.addItem("cigars", cigars);
        
        library.addItem("bookshelves", bookshelves);
        library.addItem("window seat", windowSeat);
        library.addItem("side table", sideTable);
        
        bathroom.addItem("cabinet", cabinet);
        bathroom.addItem("toilet", toilet);
        bathroom.addItem("tiles", tiles);
        bathroom.addItem("bronze key", closetKey);
        
        kitchen.addItem("ovens", ovens);
        kitchen.addItem("cupboards", cupboards);
        kitchen.addItem("kitchen knife", kitchenKnife);
        kitchen.addItem("stovetop", stovetop);
        kitchen.addItem("pot", pot);
        
        fridge.addItem("crates", crates);
        
        freezer.addItem("metal shelves", metalShelves);
        freezer.addItem("boxes", boxes);
        freezer.addItem("carcass", carcass);
        
        upstairs.addItem("table", table);
        upstairs.addItem("vase", vase);
        upstairs.addItem("candle", candle);
        
        masterBedroom.addItem("bed", bed);
        masterBedroom.addItem("nightstand", nightstand);
        masterBedroom.addItem("drawer", nightDrawer);
        masterBedroom.addItem("gold key", basementKey);
        
        masterBath.addItem("bathtub", tub);
        masterBath.addItem("shower", shower);
        masterBath.addItem("vanity", vanity);
        masterBath.addItem("jewelry box", jewelryBox);
        
        childBedroom.addItem("rug", rug);
        childBedroom.addItem("bunk bed", bunkBed);
        childBedroom.addItem("small desk", smallDesk);
        childBedroom.addItem("small drawer", childDrawer);
        childBedroom.addItem("floorboard", floorboard);
        childBedroom.addItem("placeholder2", placeholderKey2);
        
        closet.addItem("clothes", clothes);
        closet.addItem("reading nook", readingNook);
        closet.addItem("book", book);
        closet.addItem("silver key", freezerKey);
        closet.addItem("backpack", backpack);
        
        basement.addItem("wooden shelves", woodShelves);
        basement.addItem("mower", mower);
        basement.addItem("canister tube", canister);
        basement.addItem("toolbox", toolbox);
        basement.addItem("hammer", hammer);
        basement.addItem("blueprints", blueprints);

        dining.addItem("dining table", diningTable);
        dining.addItem("plate settings", plateSettings);
        dining.addItem("candelabrum", candelabrum);
        dining.addItem("steak knife", knife);
        
        lounge.addItem("cigar box", cigarBox);
        lounge.addItem("small table", drinkingCart2);
        lounge.addItem("placeholder", placeholderKey);
    }
    
    /**
     *  Create characters and place them in the map
     */
    private void createCharacters()
    {
        player = new Person("Player", 36, exit);
        blake = new Person("Blake", 36, exit);
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
                    if(command.getSecondWord().equals("back")) {
                        player.goBack();
                    } else {
                        goRoom(command);
                    }
                    break;
                
                case LOOK:
                    System.out.println(player.getCurrentRoom().getLookDescription());
                    break;
                    
                case EXAMINE:
                    break;
                    
                case USE:
                    break;
                
                case TAKE:
                    break;
                    
                case GIVE:
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
        System.out.println("                           Welcome to the " + gameTitle);
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
            //ignore the fourth word
        }

        // Try to leave current room.
        player.goRoom(direction);
        
        //TODO remove person from room hashMap
    }
    
    /**
     * Examine an item
     */
    private void examineItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Examine what?");
        }
        
        String itemName = null;
        if(!command.hasThirdWord()) {
            // The item is only one word
            itemName = command.getSecondWord();
        } else {
            // the item has two words
            itemName = command.getSecondWord() + " " + command.getThirdWord();
            //ignore the fouth word
        }
        
        //Try to examine the item
        player.examineItem(itemName);
    }
    
    /**
     * Use an item.
     */
    private void useItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to use...
            System.out.println("Use what?");
            return;
        }
        
        String itemName = null;
        if(!command.hasThirdWord()) {
            //the item name is only one word
            itemName = command.getSecondWord();
        } else if(command.hasThirdWord()) {
            //the item name is two words
            itemName = command.getSecondWord() + " " + command.getThirdWord();
        }
        
        //TODO the actual use part
        Item item = player.getItem(itemName);
        if(item != null) {
            
        } else {
            System.out.println("Item not in inventory.");
        }
        /*
         * if return null, item in inventory, fail.
         * if item found is false, fail.
         * switch (item) case:
         *      if not in right room, fail.
         * 
         */
    }
    
    /**
     * Give an item to a person.
     */
    private void giveItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give what?");
            return;
        }
        
        String itemName = null;
        String personName = null;
        if(!command.hasThirdWord()) {
            //no person specified
            System.out.println("Give to who?");
        } else if(!command.hasFourthWord()) {
            //the item name is one word
            itemName = command.getSecondWord();
            personName = command.getThirdWord();
        } else if(command.hasFourthWord()) {
            //the item name is two words
            itemName = command.getSecondWord() + " " + command.getThirdWord();
            personName = command.getFourthWord();
        }
        
        //TODO add people hashMap to room + corresponding remove/add methods?
    }
    
    /**
     * Add item to inventory
     */
    private void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
        }
        
        String itemName = null;
        if(!command.hasThirdWord()) {
            // The item is only one word
            itemName = command.getSecondWord();
        } else {
            // the item has two words
            itemName = command.getSecondWord() + " " + command.getThirdWord();
        }
        
        player.takeItem(itemName);
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
        System.out.println("run");
    }
}
