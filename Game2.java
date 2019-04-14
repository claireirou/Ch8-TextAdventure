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
            upstairs, masterBedroom, masterBath, childBedroom, closet, basement, dining, lounge, exit, backyard,
            dummyRoom1, dummyRoom2;
    private Person player, blake, ruby, olive, ash, cole;
    private Item coffeeTable, desk, bookcase, fireplace, drinkingCart, bookshelves, windowSeat, sideTable, 
            cabinet, toilet, tiles, ovens, stovetop, pot, cupboards, crates, metalShelves, boxes, carcass,
            table, vase, candle, bed, nightstand, tub, shower, jewelryBox, vanity, rug, floorboard, bunkBed,
            smallDesk, clothes, readingNook, book, woodShelves, mower, diningTable, plateSettings, candelabrum, 
            cigarBox,drinkingCart2, painting, body;
    private Item canister, toolbox, closetKey, blueprints, hammer, cigars, studyDrawer1, studyDrawer2, 
            childDrawer, nightDrawer, knife, fridgeKey, kitchenKnife, drawerKey, toolboxKey, backpack;
    private boolean wantToQuit;

    /**
     * Create the game and initialise its internal map, items
     * and characters.
     */
    public Game2() 
    {
        createRooms();
        createItems();
        createCharacters();
        populateItems();
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
        fridge = new Room("in the refrigerator", true);
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
        dummyRoom1 = new Room("", true);
        dummyRoom2 = new Room(" ", true);
        
        // initialise room exits
        hall.setExit("left", hallLeft);
        hall.setExit("forward", hallForward);
        hall.setExit("right", hallRight);
        hall.setExit("front door", exit);
        
        hallForward.setExit("kitchen", kitchen);
        hallForward.setExit("upstairs", upstairs);
        hallForward.setExit("back", hall);
        
        hallLeft.setExit("lounge", lounge);
        hallLeft.setExit("dining room", dining);
        hallLeft.setExit("basement", dummyRoom2);
        hallLeft.setExit("back", hall);
        
        hallRight.setExit("bathroom", bathroom);
        hallRight.setExit("library", library);
        hallRight.setExit("study", study);
        hallRight.setExit("back", hall);
        
        study.setExit("main hall", hall);
        
        library.setExit("main hall", hall);
        
        bathroom.setExit("main hall", hall);
        
        kitchen.setExit("fridge", fridge);
        kitchen.setExit("freezer", dummyRoom1);
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
        
        //Block secret passages
        basement.blocked(true);
        freezer.blocked(true);
        
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
        fridge.setLookDescription("You are standing inside a walk-in refrigerator that is lined with shelves on two " +
                                "\nsides. The shelves hold various crates filled with food and ingredients. There are" +
                                "\ntwo meat hooks hanging from the ceiling, one of which has the carcass of some long " +
                                "\ndead animal hooked onto it.");
        freezer.setLookDescription("You walk down a narrow passage way. At the end of it is a crude door. You push open "+
                                "the door and you are inside of a walk-in freezer. It is unbelievably cold. There are " +
                                "\nshelves along the walls in front and to the left of you with boxes of food. The door " +
                                "\nto the freezer is on the wall to your right and has been soldered shut. Just past it " +
                                "\nin the far corner, you can see a hunched body.");
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
        basement.setLookDescription("You walk down a narrow set of stairs and down a small passageway. At the end there " +
                                "\nis a ladder leading up to a hatch door. You climb the ladder and you are in a sparse " +
                                "\nbasement. It appears to serve mainly as a storage space with wooden shelves lining " +
                                "\ntwo of the walls and an old mower sitting to the left of you. On the far right of the " +
                                "\nroom is a set of stairs that heads back up to the main hall. Past the mower is another " +
                                "set of stairs that heads to a cellar door.");
        dining.setLookDescription("You are in a extravagant dinning room. The large table has ten plate settings "+
                                "\nput out and an unlit candelabrum center piece.");
        lounge.setLookDescription("You are in a lounge with two large couches and two leather armchairs. There is a "+
                                "\nside table next to one of the arm chairs with a cigar box on it. There is a" +
                                "\nsmall table that serves as a drinking cart just to the left of the door and a large "+
                                "\npainting is hanging on the far side of the right wall.");
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
        coffeeTable = new Item("coffee table", 40, true); 
        desk = new Item("desk", 90, true);
        studyDrawer1 = new Item("top drawer", 15, false);
        studyDrawer2 = new Item("bottom drawer", 15, false);
        cigars = new Item("cigars", 3, false);
        bookcase = new Item("bookcase", 100, true);
        fireplace = new Item("fireplace", 1000, true);
        drinkingCart = new Item("drinking cart", 50, true);
        bookshelves = new Item("bookshelves", 100, false);
        windowSeat = new Item("window seat", 1000, true);
        sideTable = new Item("side table", 55, true);
        cabinet = new Item("cabinet", 55, true);
        toilet = new Item("toilet", 75, true);
        tiles = new Item("tiles", 1, true);
        closetKey = new Item("bronze key", 1, false);
        ovens = new Item("ovens", 1000, true);
        stovetop = new Item("stovetop", 1000, true);
        pot = new Item("pot", 10, true);
        cupboards = new Item("cupboards", 100, true);
        kitchenKnife = new Item("kitchen knife", 4, false);
        crates = new Item("crates", 20, true);
        metalShelves = new Item("shelves", 30, true);
        boxes = new Item("boxes", 20, true);
        carcass = new Item("carcass", 25, true);
        table = new Item("table", 40, true);
        vase = new Item("vase", 15, true);
        candle = new Item("candle", 2, true);
        fridgeKey = new Item("silver key", 1, false);
        bed = new Item("bed", 100, true);
        nightstand = new Item("nightstand", 40, true);
        nightDrawer = new Item("drawer", 15, false);
        drawerKey = new Item("gold key", 1, false);
        tub = new Item("bathtub", 100, true);
        shower = new Item("shower", 1000, true);
        jewelryBox = new Item("jewelry box", 5, true);
        vanity = new Item("vanity", 1000, true);
        rug = new Item("rug", 15, true);
        floorboard = new Item("floorboard", 10, false);
        toolboxKey = new Item("small key", 1, false);
        bunkBed = new Item("bunk bed", 60, true);
        smallDesk = new Item("small desk", 30, true);
        childDrawer = new Item("drawer", 10, false);
        clothes = new Item("clothes",15, true);
        readingNook = new Item("reading nook", 1000, true);
        book = new Item("book", 3, false);
        backpack = new Item("backpack", 0, false);
        woodShelves = new Item("shelves", 40, true);
        mower = new Item("mower", 150, true);
        canister = new Item("canister tube", 5, false);
        toolbox = new Item("toolbox", 10, false);
        blueprints = new Item("blueprints", 2, false);
        hammer = new Item("hammer", 5, false);
        diningTable = new Item("dining table", 100, true);
        plateSettings = new Item("plate settings", 20, true);
        knife = new Item("steak knife", 4, false);
        candelabrum = new Item("candelabrum", 5, true);
        cigarBox = new Item("cigar box", 5, true);
        drinkingCart2 = new Item("small table", 50, true);
        painting = new Item("painting", 50, true);
        body = new Item("body", 100, false);

        // put items inside items
        fillContainer(woodShelves, toolbox);
        fillContainer(canister, blueprints);
        fillContainer(toolbox, hammer);
        fillContainer(tiles, closetKey);
        fillContainer(vase, fridgeKey);
        fillContainer(desk, studyDrawer1);
        fillContainer(desk, studyDrawer2);
        fillContainer(smallDesk, childDrawer);
        fillContainer(rug, floorboard);
        fillContainer(readingNook, book);
        fillContainer(readingNook, backpack);
        fillContainer(nightstand, nightDrawer);
        fillContainer(studyDrawer1, canister);
        fillContainer(studyDrawer2, cigars);
        fillContainer(plateSettings, knife);
        fillContainer(cupboards, kitchenKnife);
        fillContainer(cigarBox, drawerKey);
        fillContainer(floorboard, toolboxKey);
        
        // Lock items that require keys to open/ use
        studyDrawer1.lock();
        jewelryBox.lock();
        cigarBox.lock();
        candelabrum.lock();
        bookcase.lock();
        painting.lock();

        // Assign item details
        coffeeTable.setDetails("It's an ordinary coffee table.");
        desk.setDetails("The desk is an old fashion desk crafted from dark wood.");
        bookcase.setDetails("It looks like a regular bookcase.");
        fireplace.setDetails("The fireplace is unlit.");
        drinkingCart.setDetails("It's an ordinary drinking cart.");
        studyDrawer1.setDetails("A top drawer.");
        studyDrawer2.setDetails("A bottom drawer.");
        cigars.setDetails("Five loose cigars.");
        bookshelves.setDetails("They are ordinary shelves.");
        windowSeat.setDetails("It's an ordinary window seat.");
        sideTable.setDetails("It's an ordinary table with nothing on it.");
        cabinet.setDetails("It's an ordinary cabinet filled with the usual things.");
        toilet.setDetails("It's a toilet.");
        tiles.setDetails("One of the tiles is slightly lifted comes off easily to reveal a small compartment.");
        closetKey.setDetails("A bronze key that is slightly smaller the regular keys.");
        ovens.setDetails("They are regular ovens.");
        cupboards.setDetails("The cupboards are dark color with gold handles.");
        kitchenKnife.setDetails("A stocky kitchen knife. Could do some damage.");
        stovetop.setDetails("It's a regular stovetop with on burner ignited under a pot.");
        pot.setDetails("A large simmering pot filled with beef stew.");
        crates.setDetails("They're ordinary crates filled with various foods.");
        metalShelves.setDetails("They are ordinary wire shelves.");
        boxes.setDetails("There is nothing out of the ordinary in the boxes.");
        carcass.setDetails("It's the large bloody carcass of what you think might be a pig. The head is has been "+
                           "\nremoved and the insided is hollowed out.");
        table.setDetails("It's a regular table.");
        vase.setDetails("A intricately decorated vase with a lid.");
        candle.setDetails("A tall, thin, lit candle. It doesn't offer much in the way of light.");
        bed.setDetails("It's a king sized bed with comfy looking pillows and a fluffy comforter.");
        nightstand.setDetails("A sturdy wooden nightstand.");
        tub.setDetails("It's a large clawfoot tub.");
        shower.setDetails("It's an ordinary shower with tilted floor and a fancy showerhead.");
        vanity.setDetails("It's a long vanity with two sinks.");
        jewelryBox.setDetails("A purple velvet box.");
        rug.setDetails("It's a rug that has a cartoon city on it with roads and colorful buildings. Part of the rug is "+
                       "ballooned up as though there is something under it.");
        bunkBed.setDetails("They're regular bunk beds.");
        smallDesk.setDetails("A small child's desk with scattered drawings on it.");
        childDrawer.setDetails("The drawer is empty.");
        floorboard.setDetails("It's a loose floorboard that can easily be pryed up to reveal a compartment.");
        toolboxKey.setDetails("A small metal key.");
        clothes.setDetails("They are regular children's clothes.");
        readingNook.setDetails("A cozy spot with large pillows and a blanket propped up overhead.");
        book.setDetails("A green book with gold trim.");
        fridgeKey.setDetails("An ordinary silver key");
        backpack.setDetails("It's a good quality backpack with nothing in it.");
        woodShelves.setDetails("They are old wood shelves with dust covered knick knacks. One of the shelves has a beat "+
                               "\nup cardboard box.");
        mower.setDetails("It's an old, decrepit mower covered in rust.");
        canister.setDetails("It's a long leather canister tube with twine wrapped around it.");
        toolbox.setDetails("A decent sized red toolbox with a small lock on it.");
        hammer.setDetails("A standard hammer covered in blood.");
        blueprints.setDetails("They are a set of blueprints of the main level of the house. One dates back to the " +
                              "\nconstruction of the house, and another is dated more recently.");
        diningTable.setDetails("It's an ordinary table.");
        plateSettings.setDetails("The plate settings appear to be set for a six-course meal with a set of all the " +
                              "\nrelated utensils.");
        candelabrum.setDetails("It's an intricate seven light candelabrum. It would make a good source of light.");
        knife.setDetails("An ordinary steak knife.");
        cigarBox.setDetails("An intricate wooden box. On the bottom of the box on the inside there are five " +
                            "\n bold horizontal lines.");
        drinkingCart2.setDetails("It's a regular table with some bottles and empty glasses on it.");
        drawerKey.setDetails("A normal gold key; smaller than the average key.");
        painting.setDetails("It appears to be a portarait of Mr. Bodie and his spouse.");
        body.setDetails("It seems to be Mr. Bodie. His head is caved in and covered in blood.");
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
    private void populateItems()
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
        study.addItem("canister tube", canister);
        study.addItem("blueprints", blueprints);
        
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
        fridge.addItem("carcass", carcass);
        
        freezer.addItem("metal shelves", metalShelves);
        freezer.addItem("boxes", boxes);
        freezer.addItem("body", body);
        
        upstairs.addItem("table", table);
        upstairs.addItem("vase", vase);
        upstairs.addItem("candle", candle);
        
        masterBedroom.addItem("bed", bed);
        masterBedroom.addItem("nightstand", nightstand);
        masterBedroom.addItem("drawer", nightDrawer);
        
        masterBath.addItem("bathtub", tub);
        masterBath.addItem("shower", shower);
        masterBath.addItem("vanity", vanity);
        masterBath.addItem("jewelry box", jewelryBox);
        
        childBedroom.addItem("rug", rug);
        childBedroom.addItem("bunk bed", bunkBed);
        childBedroom.addItem("small desk", smallDesk);
        childBedroom.addItem("small drawer", childDrawer);
        childBedroom.addItem("floorboard", floorboard);
        childBedroom.addItem("small key", toolboxKey);
        
        closet.addItem("clothes", clothes);
        closet.addItem("reading nook", readingNook);
        closet.addItem("book", book);
        closet.addItem("silver key", fridgeKey);
        closet.addItem("backpack", backpack);
        
        basement.addItem("wooden shelves", woodShelves);
        basement.addItem("mower", mower);
        basement.addItem("toolbox", toolbox);
        basement.addItem("hammer", hammer);

        dining.addItem("dining table", diningTable);
        dining.addItem("plate settings", plateSettings);
        dining.addItem("candelabrum", candelabrum);
        dining.addItem("steak knife", knife);
        
        lounge.addItem("cigar box", cigarBox);
        lounge.addItem("small table", drinkingCart2);
        lounge.addItem("gold key", drawerKey);
        lounge.addItem("painting", painting);
    }
    
    /**
     *  Create characters and place them in the map
     */
    private void createCharacters()
    {
        player = new Person("Player", 36, exit);
        blake = new Person("Blake", 36, exit);
        ruby = new Person("Ruby", 50, dining);
        olive = new Person("Olive", 50, hall);
        ash = new Person("Ash", 50, lounge);
        cole = new Person("Cole", 50, library);
        
        ruby.getCurrentRoom().addPerson("Ruby", ruby);
        olive.getCurrentRoom().addPerson("Olive", olive);
        ash.getCurrentRoom().addPerson("Ash", ash);
        cole.getCurrentRoom().addPerson("Cole", cole);
        
        //TODO assign person descriptions & talk points *************************************************************
    }

    /**
     *  Main play routine.
     */
    public void play() 
    {            
        player.getCurrentRoom().visit();
        printWelcome();
        wait(2000);
        System.out.println("~Thank goodness you're here! I've been stuck in this game forever. " +
                            "\n    I can't figure out how to win. Will you help me?");
        System.out.println("\nHelp the trapped player? \nyes no");
        
        wantToQuit = false;
        boolean done = false;
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
                            System.out.println("Trapped Player: That's alright, I'll help you instead!");
                            done = true;
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
                playTogether();
        } else {
            System.out.println("Thank you for playing.  Good bye.");
        }
    }
    
    /**
     * Play the game with the trapped player.
     */
    private void playTogether()
    {
        //printWelcome();
        //printGameDescription();
        System.out.println(player.getCurrentRoom().getLookDescription());
        //wait(6000);
        System.out.println();
        System.out.println("Blake: Haha, how was my acting? So, we need to find the murder weapon and figure out " +
                           "\n    who committed the murder in order to beat the game. I haven't been able to do either," +
                           "\n    and my quit command is broken, hence why I'm stuck here.\n");
        //wait(7000);
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
                    System.out.println(player.getCurrentRoom().getLongDescription());
                    break;
                    
                case EXAMINE:
                    examineItem(command);
                    break;
                    
                case USE:
                    useItem(command);
                    break;
                
                case TAKE:
                    takeItem(command);
                    break;
                    
                case DROP:
                    dropItem(command);
                    break;
                    
                case GIVE:
                    giveItem(command);
                    break;
                    
                case INVENTORY:
                    player.showInventory();
                    
                case TALK:
                    break;
                    
                case HELP:
                    printHelp();
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
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("                             Welcome to the " + gameTitle);
        System.out.println("                                   By Claire Iroudayassamy");
        System.out.println("              *Game Title Pending* is a new, incredibly unfinished adventure game.");
        System.out.println("                                Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println();
        
    }
    
    /**
     * Print out the two player game description message 
     * for the player.
     */
    private void printGameDescription()
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
                           "\n    shut off and when they came back on, she claims that Bodie just vanished into thin air." +
                           "\n    She didn't see anything or hear anything, but when she left the study one of the guests" +
                           "\n    was covered in blood and was screaming about how he'd finally killed Bodie. The guy "+
                           "\n    was taken into custody, but we haven't found a body or the murder weapon so a " +
                           "\n    conviction won't stick until we have some evidence.");
        wait(13000);
        System.out.println("Blake: Some of the guests have been staying with Mr. Bodie; something about a vacation, so " +
                           "\n    we should be able to talk to some of them. Let's see if we can't close this case" +
                           "\n    tonight, I've got tacos waiting for me at home.");
        System.out.println();
        wait(7000);
        System.out.println("You arrive at the house and get out of the car.");
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
                wantToQuit = quit();
                break;
        }
        return wantToQuit;
    }

    /**
     * Print out help information.
     */
    private void printHelp() 
    {
        System.out.println("You are a detective trying to gather evidence for a murder case.");
        System.out.println("Find the murder weapon and the body.");
        printCommands();
    }
    
    /**
     * Print out all command words and a brief description
     * of how they can be used.
     */
    private void printCommands()
    {
        System.out.println("Your command words are:");
        //TODO add commands**************************************************************************************
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
        
        if(player.hasItem("candelabrum") && !candelabrum.isLocked()) {
            basement.blocked(false);
            freezer.blocked(false);
        } else {
            basement.blocked(true);
            freezer.blocked(true);
        }
        
        // Try to leave current room.
        player.goRoom(direction);
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
        
        if(itemName.equals("blueprints") && (player.hasItem("blueprints") || player.getCurrentRoom().hasItem("blueprints"))){
            System.out.println(blueprints.getDetails());
            System.out.println("As you study them, you notice that there appears to be parts of the house that are missing" +
                            "\nfrom the more recent set. The original blueprints show small passage ways that connect " +
                            "\nfrom the study to the kitchen and from the lounge to below ground. On the back of the " +
                            "\nnewer blueprints is an illustration of a green book with gold trim and a bookcase with " +
                            "only one empty spot on it.");
            study.setExit("bookcase", freezer);
            freezer.setExit("hidden door", study);
            
            lounge.setExit("painting", basement);
            basement.setExit("hatch door", lounge);
            basement.unlock();
        } else {
            //Try to examine the item
            player.examineItem(itemName);
        }
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
        Room room = player.getCurrentRoom();
        int eatCounter = 0;
        int timeCounter = 0;
        int carcassCounter = 0;
        switch(itemName) {
            case "pot":
                if(room != kitchen) {
                    System.out.println("Item is not in the room.");
                } else if(eatCounter > 5) {
                    System.out.println("The pot is empty. You ate eveything. How rude; that was someone's dinner.");
                } else {
                    System.out.println("You help yourself to a bowl of stew.");
                    System.out.println();
                    System.out.println("Blake: By all means, treat yourself. It's not like we're busy or anything.");
                    System.out.println();
                    player.eat();
                    eatCounter ++;
                }
                break;
                
            case "bed":
                if(room != masterBedroom) {
                    System.out.println("Item is not in the room.");
                } else {
                    System.out.println("You get into bed and settle in for a power nap.");
                    System.out.println();
                    System.out.println("Blake: Are you serious right now?\n");
                    System.out.println();
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000); 
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000);
                    System.out.print("."); wait(1000); System.out.println(".\n"); wait(1000);
                    player.sleep();
                    timeCounter += 3;
                    if(timeCounter >= 15) {
                        System.out.println("Blake: I hope you're happy.\n");
                        wait(1500);
                        System.out.println("You look out the window and you see it's daytime. Your phone rings. It's " +
                                    "your boss and \nthey're furious. You haven't gathered enough evidence to close " +
                                    "the case and the killer \ngot away while you were sleeping. You are suspended " +
                                    "indefinitely without pay...");
                        wait(10000);
                        System.out.println("\n\n\n                                          Game Over\n\n\n");
                        wantToQuit = true;
                    } else {
                        System.out.println("Blake: Feel better? Can we get back to work now?\n");
                    }
                }
                break;
                
            case "carcass":
                if(room != fridge) {
                    System.out.println("Item is not in the room.");
                } else {
                    switch(carcassCounter) {
                        case 0:
                            System.out.println("Blake: DON'T! Trust me it's not worth it. There's nothing in it " +
                                        "and you'll get blood all \nover you. And then no one will talk to you " + 
                                        "until you clean up.\n");
                            carcassCounter++;
                            break;
                            
                        case 1:
                            player.useCarcass();
                            System.out.println();
                            wait(3000);
                            System.out.println("Blake: What did I tell you? Now you'll need to take a shower " +
                                        "before we can do anything\n");
                            carcassCounter++;
                            break;
                            
                        case 2:
                            player.useCarcass();
                            System.out.println();
                            wait(2000);
                            System.out.println("Blake: Seriously, again? I think I'm gonna throw up...\n");
                            carcassCounter++;
                            break;
                            
                        default:
                            if(carcassCounter > 10) {
                                System.out.println("Blake: Great, now you've done it.\n");
                                wait(1500);
                                player.useCarcass();
                                System.out.println("\nNo one will talk to you because you keep fondling the meat.");
                                System.out.println("\n\n\n                                          Game Over\n\n\n");
                                wantToQuit = true;
                                break;
                            }
                            player.useCarcass();
                            System.out.println();
                            wait(2000);
                            System.out.println("Blake: Stop that. You're so gross and weird.\n");
                            carcassCounter++;
                            break;
                    }
                }
                break;
                
            case "bathtub":
                if(room != masterBath) {
                    System.out.println("Item is not in the room.");
                } else {
                    System.out.println("You draw a bath and start to undress.");
                    System.out.println();
                    wait(500);
                    if(player.isDirty()) {
                        System.out.println("Blake: A bath? Really? There's a shower right there. Whatever, I'll be " +
                                    "outside.\n");
                    } else {
                        System.out.println("Blake: What are you doing! You're not even dirty?! Who just takes a bath " +
                                    "in a stranger's house?\n");
                    }
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000); 
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000);
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000);
                    System.out.print("."); wait(1000); System.out.println(".\n"); wait(1000);
                    player.takeBath();
                    timeCounter += 5;
                    if(timeCounter >= 15) {
                        System.out.println("Blake: Hope it was worth it.\n");
                        wait(1000);
                        System.out.println("You head out of the bathroom. Your phone rings. It's your boss and " +
                                "they're furious. \nOne of the witness called and complained about a deadbeat cop " +
                                "who was just wasting \ntime in the tub. You are suspended indefinitely without pay...");
                        wait(10000);
                        System.out.println("\n\n\n                                          Game Over\n\n\n");
                        wantToQuit = true;
                    }
                }
                break;
                        
            case "shower":
                if(room != masterBath) {
                    System.out.println("Item is not in the room.");
                } else {
                    System.out.println("You start the shower.");
                    System.out.println();
                    wait(500);
                    if(player.isDirty()) {
                        System.out.println("Blake: Be quick, okay? If you waste too much time, we lose the game.\n");
                    } else {
                        System.out.println("Blake: What are you doing! You're not even dirty?! Whatever, I'll be " +
                                    "outside.\n");
                    }
                    System.out.print("."); wait(500); System.out.print("."); wait(500); 
                    System.out.print("."); wait(500); System.out.print("."); wait(500);
                    System.out.print("."); wait(500); System.out.print("."); wait(500);
                    System.out.print("."); wait(500); System.out.println(".\n"); wait(500);
                    player.shower();
                    timeCounter += 1;
                    if(timeCounter >= 15) {
                        System.out.println("Blake: Congratulations, moron.\n");
                        wait(1000);
                        System.out.println("You head out of the bathroom. Your phone rings. It's your boss and " +
                                "they're furious. \nOne of the witness called and complained about a deadbeat cop " +
                                "who was just wasting \ntime. You are suspended indefinitely without pay...");
                        wait(10000);
                        System.out.println("\n\n\n                                          Game Over\n\n\n");
                        wantToQuit = true;
                    }
                }
                break;
                        
            case "rug":
                if(room != childBedroom) {
                    System.out.println("Item is not in the room.");
                } else {
                    if(floorboard.isFound()) {
                        System.out.println("You cannot use this item again.");
                    } else {
                        System.out.println("You lift up the rug and see a loose floorboad under it.");
                        floorboard.found();
                    }
                }
                break;
                        
            case "floorboard":
                if(room != childBedroom) {
                    System.out.println("Item is not in the room.");
                } else {
                    player.examineItem("floorboard");
                }
                break;
                
            case "stovetop":
                if(room != kitchen) {
                    System.out.println("Item is not in the room.");
                } else if(player.hasItem("candelabrum") && candelabrum.isLocked()) {
                    System.out.println("You use the stove to light the candelabrum.");
                    candelabrum.unlock();
                } else {
                    System.out.println("Nothing to use item for.");
                }
                break;
                
            default:
                if(item != null) {
                    switch(itemName) {
                        case "cigars":
                            if(room != lounge) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                System.out.println("You place the cigars in the cigar box and a small compartment " +
                                        "pops out of the side.");
                                player.useItem("cigars");
                                cigarBox.unlock();
                                //cigarBox.setDetails("")?????????????????????????????????????
                            }
                            break;
                            
                        case "bronze key":
                            if(room != childBedroom) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                System.out.println("You place the key in the closet door and turn it. The " +
                                        "door unlocks.");
                                player.useItem("bronze key");
                                closet.unlock();
                            }
                            break;

                        case "kitchen knife":
                            if(room.isEmpty()) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                if(room.hasPerson("Cole")) {
                                    System.out.println("You attempt to charge Cole armed with a kitchen knife. " +
                                                "Cole easily blocks you and punches \nyou in the face. You feel " +
                                                "blood gush out of your nose.");
                                } else if(room.hasPerson("Ash")) {
                                    System.out.println("You raise you knife to strike Ash, but before you can bring "+
                                                "it down, Ash uppercuts you in \nthe chin. You feel your mouth fill " +
                                                "with blood.");
                                } else if(room.hasPerson("Ruby")) {
                                    System.out.println("Before you can even get a firm grip on the knife, Ruby pulls "+
                                                "out a can of peper spray and \nnails you in the face with it. Your " +
                                                "eyes burn and you can barely see.");
                                } else if(room.hasPerson("Olive")) {
                                    System.out.println("You try to attack with your knife, but Olive kicks your " +
                                                "legs out from under you. You fall \nto your knees and Olive hits you " +
                                                "in the face with her clutch purse. You feel blood drip \ndown your " +
                                                "face.");
                                }
                                
                                player.attack();
                                System.out.println();
                                wait(2500);
                                System.out.println("Blake: Yeah, no. Attacking the NPC's is a no-no. They don't " +
                                                "hold it against you, \nbut they'll always win. And now you need " +
                                                "to clean up.\n");
                                
                                if(!player.isConscious()) {
                                    System.out.println("You feel exceptionally dizzy. The room spins around "+
                                                "and the floor rushes up to meet you. \nEverything goes dark. You " +
                                                "are unconscious.");
                                    System.out.println("\n\n\n                                          Game " +
                                                "Over\n\n\n");
                                    wantToQuit = true;
                                }
                            }
                            break;

                        case "candle":
                            if(player.hasItem("candelabrum") || room.hasItem("candelabrum")) {
                                candelabrum.unlock();
                                System.out.println("You use the candle to light the candelabrum.");
                            }
                            break;
                        
                        case "silver key": 
                            if(room != kitchen) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                System.out.println("You place the key in the refrigerator door and turn it. The " +
                                        "door unlocks.");
                                player.useItem("silver key");
                                fridge.unlock();
                            }
                            break;
                        
                        case "gold key":
                            if(room != study) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                System.out.println("You place the key in the top drawer's keyhole and turn it. The " +
                                        "drawer unlocks.");
                                player.useItem("gold key");
                                studyDrawer1.unlock();
                            }
                            break;

                        case "small key":
                            if(room != basement) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                if(toolbox.isFound()) {
                                    System.out.println("You use the small key to unlock the toolbox.");
                                    player.useItem("small key");
                                    toolbox.unlock();
                                } else {
                                    System.out.println("Nothing to use item on.");
                                }   
                            }
                            break;
                        
                        case "book":
                            if(room != study) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                if(blueprints.isFound()) {
                                    System.out.println("You place the book into the empty spot on the shelf. The " +
                                                "bookcase swings open to reveal a \nvery dark passageway.");
                                    player.useItem("book");
                                    freezer.unlock();
                                }
                            }
                            break;
                        
                        case "hammer":
                            System.out.println("You cannot use this item. It is evidence.");
                            break;
                        
                        case "steak knife":
                            if(room.isEmpty()) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                if(room.hasPerson("Cole")) {
                                    System.out.println("You attempt to charge Cole armed with a steak knife. " +
                                                "Cole easily blocks you and punches \nyou in the face. You feel " +
                                                "blood gush out of your nose.");
                                } else if(room.hasPerson("Ash")) {
                                    System.out.println("You raise you knife to strike Ash, but before you can bring "+
                                                "it down, Ash uppercuts you in \nthe chin. You feel your mouth fill " +
                                                "with blood.");
                                } else if(room.hasPerson("Ruby")) {
                                    System.out.println("Before you can even get a firm grip on the knife, Ruby pulls "+
                                                "out a can of peper spray and \nnails you in the face with it. Your " +
                                                "eyes burn and you can barely see.");
                                } else if(room.hasPerson("Olive")) {
                                    System.out.println("You try to attack with your knife, but Olive kicks your " +
                                                "legs out from under you. You fall \nto your knees and Olive hits you " +
                                                "in the face with her clutch purse. You feel blood drip \ndown your " +
                                                "face.");
                                }
                                
                                player.attack();
                                System.out.println();
                                wait(2500);
                                System.out.println("Blake: Yeah, no. Attacking the NPC's is a no-no. They don't " +
                                                "hold it against you, \nbut they'll always win. And now you need " +
                                                "to clean up.\n");
                                
                                if(!player.isConscious()) {
                                    System.out.println("You feel exceptionally dizzy. The room spins around "+
                                                "and the floor rushes up to meet you. \nEverything goes dark. You " +
                                                "are unconscious.");
                                    System.out.println("\n\n\n                                          Game " +
                                                "Over\n\n\n");
                                    wantToQuit = true;
                                }
                            }
                            break;
                        
                        case "candelabrum":
                            if(candelabrum.isLocked()){ 
                                System.out.println("The candelabrum is unlit. You cannot use it.");
                            } else {
                                basement.blocked(false);
                                freezer.blocked(false);
                                if(room != study && room != lounge) {
                                    System.out.println("Nothing to use item on.");
                                }
                            }
                            break;
                            
                        default:
                            System.out.println("This item cannot be used.");
                            break;
                    } 
                } else {
                    System.out.println("Item not in inventory");
                } // end of default use case
        } // end of use switch case
    } // end of useItem method
    
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

        if(player.getCurrentRoom().hasPerson(personName)) {
            Person person = currentRoom.getPerson(personName);
            player.giveItem(itemName, person);
        }else {
            System.out.println("This person is not in the room.");
        }
    }
    
    /**
     * Add an item to inventory
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
     * Drop an item from inventory
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
        }
        
        String itemName = null;
        if(!command.hasThirdWord()) {
            // The item is only one word
            itemName = command.getSecondWord();
        } else {
            // the item has two words
            itemName = command.getSecondWord() + " " + command.getThirdWord();
        }
        
        player.dropItem(itemName);
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
            System.out.println("Blake: Wait! Don't lea");
            wait(200);
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
    
    /**
     * Move an NPC around the map. Remove person from their current
     * room, set their new current room and add them to that room.
     * @param room The room to move to
     * @param person The person to move.
     */
    private void moveNPC(Room room, Person person)
    {
        person.getCurrentRoom().removePerson(person.getName(), person);
        person.setRoom(room);
        person.getCurrentRoom().addPerson(person.getName(), person);
    }
    
    public void test()
    {
        System.out.println("You try to attack with your knife, but Olive kicks your " +
                                                "legs out from under you. You fall \nto your knees and Olive hits you " +
                                                "in the face with her clutch purse. You feel blood drip \ndown your " +
                                                "face.");
        System.out.println("Before you can even get a firm grip on the knife, Ruby pulls "+
                                                "out a can of peper spray and \nnails you in the face with it. Your " +
                                                "eyes burn and you can barely see.");
        System.out.println("You raise you knife to strike Ash, but before you can bring "+
                                                "it down, Ash uppercuts you in \nthe chin. You feel your mouth fill " +
                                                "with blood.");   
        System.out.println("You attempt to charge Cole armed with a kitchen knife. " +
                                                "Cole easily blocks you and punches \nyou in the face. You feel " +
                                                "blood gush out of your nose.");                                        
    }
}
