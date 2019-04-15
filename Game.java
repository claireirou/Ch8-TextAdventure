/**
 *  This class is the main class of the "Mansion Detective" application. 
 *  "Mansion Detective" is a text based murder mystery game.  Players need 
 *  to find the body and the murder weapon to win.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, items, and people, and creates the parser and starts the game.  
 *  It also evaluates and executes the commands that the parser returns.
 * 
 * @author Claire Iroudayassamy
 * @version 2019.04.15
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private String gameTitle;
    private Room hall, hallRight, hallLeft, hallForward, study, library, bathroom, kitchen, freezer, fridge, 
            upstairs, masterBedroom, masterBath, childBedroom, closet, basement, dining, lounge, exit, backyard,
            dummyRoom1, dummyRoom2;
    private Person player, blake, ruby, olive, ash, cole;
    private Item coffeeTable, desk, bookcase, fireplace, drinkingCart, bookshelves, windowSeat, sideTable, 
            cabinet, toilet, tiles, ovens, stovetop, pot, cupboards, crates, metalShelves, boxes, carcass,
            table, vase, candle, bed, nightstand, tub, shower, jewelryBox, vanity, rug, floorboard, bunkBed,
            smallDesk, clothes, readingNook, book, woodShelves, mower, diningTable, plateSettings, candelabrum, 
            cigarBox,drinkingCart2, painting, body, plasticSheet, cardboardBox, canister, toolbox, closetKey, 
            blueprints, hammer, cigars, studyDrawer1, studyDrawer2, childDrawer, nightDrawer, knife, fridgeKey, 
            kitchenKnife, drawerKey, toolboxKey, backpack;
    private boolean wantToQuit;
    private boolean win;
    
    // Counters that determine game over
    private int eatCounter;
    private int timeCounter;
    private int carcassCounter;

    /**
     * Create the game and initialise its internal map, items
     * and characters.
     */
    public Game() 
    {
        createRooms();
        createItems();
        createCharacters();
        populateItems();
        parser = new Parser();
        gameTitle = "Mansion Detective";
        eatCounter = 0;
        timeCounter = 0;
        carcassCounter = 0;
    }
    
    public static void main (String[] args)
    {
        Game game = new Game();
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
        hallLeft.setExit("main hall", hall);
        
        hallRight.setExit("bathroom", bathroom);
        hallRight.setExit("library", library);
        hallRight.setExit("study", study);
        hallRight.setExit("main hall", hall);
        
        study.setExit("main hall", hall);
        
        library.setExit("main hall", hall);
        
        bathroom.setExit("main hall", hall);
        
        kitchen.setExit("fridge", fridge);
        kitchen.setExit("freezer", dummyRoom1);
        kitchen.setExit("main hall", hall);
        
        fridge.setExit("kitchen", kitchen);
        
        upstairs.setExit("left", childBedroom);
        upstairs.setExit("right", masterBedroom);
        upstairs.setExit("downstairs", hall);
        
        masterBedroom.setExit("master bath", masterBath);
        masterBedroom.setExit("landing", upstairs);
        
        masterBath.setExit("back", masterBedroom);
        
        childBedroom.setExit("closet", closet);
        childBedroom.setExit("landing", upstairs);
        
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
                                "\nin the far corner, you can see a plastic sheet covering something large.");
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
        coffeeTable = new Item("coffee table", 50, true); 
        desk = new Item("desk", 90, true);
        studyDrawer1 = new Item("top drawer", 55, false);
        studyDrawer2 = new Item("bottom drawer", 55, false);
        cigars = new Item("cigars", 3, false);
        bookcase = new Item("bookcase", 100, true);
        fireplace = new Item("fireplace", 1000, true);
        drinkingCart = new Item("drinking cart", 50, true);
        bookshelves = new Item("bookshelves", 100, false);
        windowSeat = new Item("window seat", 1000, true);
        sideTable = new Item("side table", 55, true);
        cabinet = new Item("cabinet", 55, true);
        toilet = new Item("toilet", 75, true);
        tiles = new Item("tiles", 5, true);
        closetKey = new Item("bronze key", 1, false);
        ovens = new Item("ovens", 1000, true);
        stovetop = new Item("stovetop", 1000, true);
        pot = new Item("pot", 100, true);
        cupboards = new Item("cupboards", 100, true);
        kitchenKnife = new Item("kitchen knife", 7, false);
        crates = new Item("crates", 20, true);
        metalShelves = new Item("shelves", 50, true);
        boxes = new Item("boxes", 20, true);
        carcass = new Item("carcass", 30, true);
        table = new Item("table", 50, true);
        vase = new Item("vase", 50, true);
        candle = new Item("candle", 2, true);
        fridgeKey = new Item("silver key", 1, false);
        bed = new Item("bed", 100, true);
        nightstand = new Item("nightstand", 50, true);
        nightDrawer = new Item("drawer", 15, false);
        drawerKey = new Item("gold key", 1, false);
        tub = new Item("bathtub", 100, true);
        shower = new Item("shower", 1000, true);
        jewelryBox = new Item("jewelry box", 10, true);
        vanity = new Item("vanity", 1000, true);
        rug = new Item("rug", 15, true);
        floorboard = new Item("floorboard", 10, false);
        toolboxKey = new Item("small key", 1, false);
        bunkBed = new Item("bunk bed", 60, true);
        smallDesk = new Item("small desk", 50, true);
        childDrawer = new Item("drawer", 10, false);
        clothes = new Item("clothes", 15, true);
        readingNook = new Item("reading nook", 1000, true);
        book = new Item("book", 5, false);
        backpack = new Item("backpack", 0, false);
        woodShelves = new Item("shelves", 50, true);
        mower = new Item("mower", 150, true);
        canister = new Item("canister tube", 10, false);
        toolbox = new Item("toolbox", 100, false);
        blueprints = new Item("blueprints", 5, false);
        hammer = new Item("hammer", 10, false);
        diningTable = new Item("dining table", 100, true);
        plateSettings = new Item("plate settings", 50, true);
        knife = new Item("steak knife", 4, false);
        candelabrum = new Item("candelabrum", 10, true);
        cigarBox = new Item("cigar box", 100, true);
        drinkingCart2 = new Item("small table", 50, true);
        painting = new Item("painting", 50, true);
        body = new Item("body", 100, false);
        plasticSheet = new Item("plastic sheet", 15, true);
        cardboardBox = new Item("cardboard box", 50, false);

        // put items inside items
        fillContainer(woodShelves, cardboardBox);
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
        fillContainer(plasticSheet, body);
        fillContainer(cardboardBox, toolbox);
        
        // Lock items that require keys to open/ use
        studyDrawer1.lock();
        jewelryBox.lock();
        cigarBox.lock();
        candelabrum.lock();
        bookcase.lock();
        painting.lock();
        toolbox.lock();

        // Assign item details
        coffeeTable.setDetails("It's an ordinary coffee table.");
        desk.setDetails("The desk is an old fashion desk crafted from dark wood.");
        bookcase.setDetails("It looks like a regular bookcase.");
        fireplace.setDetails("The fireplace is unlit.");
        drinkingCart.setDetails("It's an ordinary drinking cart.");
        studyDrawer1.setDetails("A -top drawer-.");
        studyDrawer2.setDetails("A -bottom drawer-.");
        cigars.setDetails("Five loose -cigars-.");
        bookshelves.setDetails("They are ordinary shelves.");
        windowSeat.setDetails("It's an ordinary window seat.");
        sideTable.setDetails("It's an ordinary table with nothing on it.");
        cabinet.setDetails("It's an ordinary cabinet filled with the usual things.");
        toilet.setDetails("It's a toilet.");
        tiles.setDetails("One of the tiles is slightly lifted and comes off easily to reveal a small compartment.");
        closetKey.setDetails("A -bronze key- that is slightly smaller the regular keys.");
        ovens.setDetails("They are regular ovens.");
        cupboards.setDetails("The cupboards are dark color with gold handles.");
        kitchenKnife.setDetails("A stocky -kitchen knife-. Could do some damage.");
        stovetop.setDetails("It's a regular stovetop with on burner ignited under a -pot-.");
        pot.setDetails("A large simmering pot filled with beef stew.");
        crates.setDetails("They're ordinary crates filled with various foods.");
        metalShelves.setDetails("They are ordinary wire shelves.");
        boxes.setDetails("There is nothing out of the ordinary in the boxes.");
        carcass.setDetails("It's the large bloody carcass of what you think might be a pig. The head is has been "+
                           "\nremoved and the insided is hollowed out.");
        table.setDetails("It's a regular table.");
        vase.setDetails("A intricately decorated -vase- with a lid.");
        candle.setDetails("A tall, thin, lit -candle-. It doesn't offer much in the way of light.");
        bed.setDetails("It's a king sized bed with comfy looking pillows and a fluffy comforter.");
        nightstand.setDetails("A sturdy wooden -nightstand-.");
        tub.setDetails("It's a large clawfoot -bathtub-.");
        shower.setDetails("It's an ordinary shower with tilted floor and a fancy showerhead.");
        vanity.setDetails("It's a long vanity with two sinks.");
        jewelryBox.setDetails("A purple velvet -jewelry box-.");
        rug.setDetails("It's a -rug- that has a cartoon city on it with roads and colorful buildings. Part of the rug \nis "+
                       "ballooned up as though there is something under it.");
        bunkBed.setDetails("They're regular bunk beds.");
        smallDesk.setDetails("A child's -small desk- with scattered drawings on it.");
        childDrawer.setDetails("The -small drawer- is empty.");
        floorboard.setDetails("It's a loose -floorboard- that can easily be pryed up to reveal a compartment.");
        toolboxKey.setDetails("A -small key-.");
        clothes.setDetails("They are regular children's -clothes-.");
        readingNook.setDetails("A cozy spot with large pillows and a blanket propped up overhead.");
        book.setDetails("A green -book- with gold trim.");
        fridgeKey.setDetails("An ordinary -silver key-");
        backpack.setDetails("A good quality -backpack- with nothing in it.");
        woodShelves.setDetails("They are old -wooden shelves- with dust covered knick knacks. One of the shelves has a beat "+
                               "\nup cardboard box.");
        cardboardBox.setDetails("An old -cardboard box- that's a bit worse for wear.");
        mower.setDetails("It's an old, decrepit mower covered in rust.");
        canister.setDetails("It's a long leather -canister tube- with twine wrapped around it.");
        toolbox.setDetails("A decent sized red -toolbox- with a small lock on it.");
        hammer.setDetails("A standard -hammer- covered in blood.");
        blueprints.setDetails("They are a set of -blueprints- of the main level of the house. One dates back to the " +
                              "\nconstruction of the house, and another is dated more recently.");
        diningTable.setDetails("It's an ordinary table.");
        plateSettings.setDetails("The -plate settings- appear to be set for a six-course meal with a set of all the " +
                              "\nrelated utensils.");
        candelabrum.setDetails("It's an intricate seven light -candelabrum-. It would make a good source of light.");
        knife.setDetails("An ordinary -steak knife-.");
        cigarBox.setDetails("An intricate wooden -cigar box-. On the bottom of the box on the inside there are five " +
                            "\n bold horizontal lines.");
        drinkingCart2.setDetails("It's a regular table with some bottles and empty glasses on it.");
        drawerKey.setDetails("A normal -gold key-; smaller than the average key.");
        painting.setDetails("It appears to be a portarait of Mr. Bodie and his spouse.");
        body.setDetails("A dead body. It seems to be Mr. Bodie. His head is caved in and covered in blood.");
        plasticSheet.setDetails("It's a large factory grade -platic sheet- that is wrapped around something. " +
                        "\nYou open the sheet.");
        nightDrawer.setDetails("An empty -drawer-.");
    }
    
    /**
     * Assign items to their containers
     * @param container The container item
     * @param item The item to put inside the container
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
        freezer.addItem("plastic sheet", plasticSheet);
        freezer.addItem("body", body);
        
        upstairs.addItem("table", table);
        upstairs.addItem("vase", vase);
        upstairs.addItem("candle", candle);
        upstairs.addItem("silver key", fridgeKey);
        
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
        closet.addItem("backpack", backpack);
        
        basement.addItem("wooden shelves", woodShelves);
        basement.addItem("mower", mower);
        basement.addItem("toolbox", toolbox);
        basement.addItem("hammer", hammer);
        basement.addItem("cardboard box", cardboardBox);

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
        // Create people
        player = new Person("Player", 29, exit);
        blake = new Person("blake", 36, exit);
        ruby = new Person("ruby", 50, study);
        olive = new Person("olive", 50, hall);
        ash = new Person("ash", 50, lounge);
        cole = new Person("cole", 50, library);
        
        // Add NPCs to rooms
        ruby.getCurrentRoom().addPerson("ruby", ruby);
        olive.getCurrentRoom().addPerson("olive", olive);
        ash.getCurrentRoom().addPerson("ash", ash);
        cole.getCurrentRoom().addPerson("cole", cole);
        
        // Set item key that will unlock clues.
        ruby.setItemKey("steak knife");
        ash.setItemKey("jewelry box");
        cole.setItemKey("clothes");
        
        
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
        System.out.println("\nHelp the trapped player? \n- yes - no -");
        
        wantToQuit = false;
        win = false;
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
                            System.out.println("Help the trapped player? \n- yes - no -");
                            break;
                        case 2:
                            System.out.println("Trapped Player: Buddy, you gotta work with me here. Type yes or no." +
                                                "\n    But preferably yes.\n");
                            wait(1000);
                            System.out.println("Help the trapped player? \n- yes - no -");
                            break;
                        case 3:
                            System.out.println("Trapped Player: Now come on, just type y e s, you can do it!\n");
                            wait(2000);
                            System.out.println("Help the trapped player? \n- yes - no -");
                            break;
                        case 4:
                            System.out.println("Trapped Player: You know what buddy, don't worry about it." +
                                            "\n    I got you...\n");
                            wait(2500);
                            System.out.println("Help the trapped player? \n- yes - no -");
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
                            System.out.println("Help the trapped player? \n- yes - no -");
                            break;
                        case 2:
                            System.out.println("Trapped Player: Is there anything I can I say something to change " +
                                               "your mind?\n");
                            wait(2000); 
                            System.out.println("Help the trapped player? \n- yes - no -");
                            break;
                        case 3:
                            System.out.println("Trapped Player: That's alright, I'll help you instead!");
                            wait(1500);
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
                        System.out.println("Help the trapped player? \n- yes - no -");
                        break;
                    }
                    
                default:
                    System.out.println("Trapped Player: You're getting ahead of yourself, there. Before that, " +
                                        "\n\twill you help me?\n");
                    System.out.println("Help the trapped player? \n- yes - no -");
                    break;
            }
        }
        
        if(!wantToQuit) {
            startGame();
        } else {
            System.out.println("Thank you for playing.  Good bye.");
        }
        
    }
    
    /**
     * Play the game. Loops until end of play.
     */
    private void startGame()
    {
        printWelcome();
        printGameDescription();
        System.out.println();
        System.out.println("Blake: Haha, how was my acting? So, we need to find the murder weapon and and the victim's " +
                           "\n    body in order to beat the game. I haven't been able to do either, and my quit command " +
                           "\n    is broken, hence why I'm stuck here.\n");
        wait(7000);
        System.out.println(player.getCurrentRoom().getLookDescription());
        
        // Enter the main command loop. Here we repeatedly read commands and 
        // execute them until the game is over.
        
        Command command;
        while(!wantToQuit && !win) {
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
                    break;
                    
                case TALK:
                    talk(command);
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
            if(body.isFound() && hammer.isFound()) {
                // Both the body and the murder weapon have been found
                win = true;     // signal game won
            }
        }
        
        //Print out game won message
        if(win) {
            System.out.println("\n\n\nBlake: you did it!");
            System.out.println("\n\n\n                       You have found the murder weapon and the victim's body!");
            System.out.println("                                             You Win!");
            System.out.println("                                      Thank you for playing!");
            wait(1500);
            System.out.println("\n\n\n\n\nBlake: Thanks for the help, man. I couldn't have done it without you.");
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
        System.out.println("                                  By Claire Iroudayassamy");
        System.out.println("   " + gameTitle +" is a new, definitely never done before, murder mystery adventure game.");
        System.out.println("                               Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println();
        
    }
    
    /**
     * Print out the game description message and objective
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
                           "\n    went out. One of the guests, Miriam, says that she was in the study with Bodie when " +
                           "\n    the lights shut off and when they came back on, she claims that Bodie just vanished " +
                           "\n    into thin air. She didn't see anything or hear anything, but when she left the study " +
                           "\n    one of the guests, Marvin, was covered in blood and was screaming about how he'd "+
                           "\n    finally killed Bodie. The guy was taken into custody, but we haven't found a body or " +
                           "\n    the murder weapon so a conviction won't stick until we have some evidence.");
        wait(13000);
        System.out.println("Blake: Some of the guests have been staying with Mr. Bodie; something about a vacation, so " +
                           "\n    we should be able to talk to some of them. Let's see if we can't close this case" +
                           "\n    tonight, I've got tacos waiting for me at home.");
        System.out.println();
        wait(7000);
        System.out.println("You arrive at the house and get out of the car.");
    }

    /**
     * Print out help information.
     */
    private void printHelp() 
    {
        System.out.println("\nYou are a detective trying to gather evidence for a murder case.");
        System.out.println("Find the murder weapon and the body.\n");
        wait(200);
        printCommands();
        System.out.println("Interactable item names are enclosed in dashes. Example: - old picture -");
        System.out.println("You must enter all words in the dashes to successfully interact with the environment.");
        System.out.println();
        wait(200);
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    /**
     * Print out all command words and use examples
     */
    private void printCommands()
    {
        System.out.println("Your command words are:\n");
        parser.showCommands();
        System.out.println("___________________________");
        System.out.println("Command examples:\n");
        System.out.println("take key - to take an item");
        System.out.println("give mike book - to give an item to someone");
        System.out.println("examine floor - to get more details about an item");
        System.out.println("go kitchen - to go through an exit");
        System.out.println("use pen - to use item");
        System.out.println("drop large book - to drop an item");
        System.out.println("talk joe - to talk to someone");
        System.out.println("look - to see what room you're in, its items and exits");
        System.out.println("___________________________\n");
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
            // if there is no second word we don't know what to examine
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
            // If item is blueprints, unlock the secret passageways.
            System.out.println();
            System.out.println(blueprints.getDetails());
            System.out.println("As you study them, you notice that there appears to be parts of the house that are missing" +
                            "\nfrom the more recent set. The original blueprints show small passage ways that connect " +
                            "\nfrom the study to the kitchen and from the lounge to below ground. On the back of the " +
                            "\nnewer blueprints is an illustration of a green book with gold trim and a bookcase with " +
                            "\nonly one empty spot on it.");
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
        
        switch(itemName) {
            case "pot":
                if(room != kitchen) {
                    System.out.println("Item is not in the room.");
                } else if(eatCounter > 5) {
                    System.out.println("The pot is empty. You ate eveything. How rude; that was someone's dinner.");
                } else {
                    System.out.println("You help yourself to a bowl of stew.");
                    System.out.println();
                    wait(1000);
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
                                        "before we can talk to anyone.\n");
                            carcassCounter++;
                            break;
                            
                        case 2:
                            player.useCarcass();
                            System.out.println();
                            wait(2000);
                            System.out.println("Blake: Seriously, again? I think I'm gonna throw up...\n");
                            carcassCounter++;
                            break;
                            
                        case 3:
                            player.useCarcass();
                            System.out.println();
                            wait(2000);
                            System.out.println("Blake: Stop that. You're so gross and weird.\n");
                            carcassCounter++;
                            break;
                            
                        default:
                            if(carcassCounter == 5) {
                                System.out.println("Blake: Great, now you've done it.\n");
                                wait(1000);
                                player.useCarcass();
                                wait(2000);
                                System.out.println("\nNo one will talk to you, and they kick you out because you " +
                                            "keep fondling the meat.");
                                System.out.println("\n\n\n                                          Game Over\n\n\n");
                                wantToQuit = true;
                                break;
                            }
                            player.useCarcass();
                            System.out.println();
                            wait(2000);
                            System.out.println("Blake: Is this, like, a thing for you?\n");
                            carcassCounter++;
                            break;
                    }
                }
                break;
                
            case "bathtub":
                if(room != masterBath) {
                    System.out.println("Item is not in the room.");
                } else {
                    System.out.println();
                    System.out.println("You draw a bath and start to undress.");
                    System.out.println();
                    wait(1000);
                    if(player.isDirty()) {
                        System.out.println("Blake: A bath? Really? There's a shower right there. Whatever, I'll be " +
                                    "outside.\n");
                    } else {
                        System.out.println("Blake: What are you doing! You're not even dirty?! Who just takes a bath " +
                                    "in a stranger's \nhouse?\n");
                    }
                    wait(1000);
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000); 
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000);
                    System.out.print("."); wait(1000); System.out.print("."); wait(1000);
                    System.out.print("."); wait(1000); System.out.println("."); wait(1000);
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
                    wait(1000);
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
                                System.out.println();
                                if(room.hasPerson("cole")) {
                                    System.out.println("You attempt to charge Cole armed with a steak knife. " +
                                                "Cole easily blocks you and punches \nyou in the face. You feel " +
                                                "blood gush out of your nose.");
                                } else if(room.hasPerson("ash")) {
                                    System.out.println("You raise you knife to strike Ash, but before you can bring "+
                                                "it down, Ash uppercuts you in \nthe chin. You feel your mouth fill " +
                                                "with blood.");
                                } else if(room.hasPerson("ruby")) {
                                    System.out.println("Before you can even get a firm grip on the knife, Ruby pulls "+
                                                "out a can of peper spray and \nnails you in the face with it. Your " +
                                                "eyes burn and you can barely see.");
                                } else if(room.hasPerson("olive")) {
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
                            
                        case "plastic sheet":
                            if(room != freezer) {
                                System.out.println("Nothing to use item on.");
                            } else {
                                System.out.println(plasticSheet.getDetails());
                                plasticSheet.findItems();
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
            System.out.println("Give to who?");
            return;
        }
        
        String itemName = null;
        String personName = null;
        if(!command.hasThirdWord()) {
            //no person specified
            System.out.println("Give what?");
            return;
        } else if(!command.hasFourthWord()) {
            //the item name is one word
            personName = command.getSecondWord();
            itemName = command.getThirdWord();
        } else if(command.hasFourthWord()) {
            //the item name is two wordsgo 
            personName = command.getSecondWord();
            itemName = command.getThirdWord() + " " + command.getFourthWord();
        }
        
        Person person = player.getCurrentRoom().getPerson(personName);
        if(person != null) {
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
        
        // Try to take item
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
        
        // Try to drop item
        player.dropItem(itemName);
    }
    
    /**
     * Talk to an NPC
     */
    private void talk(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Talk to who?");
        }
        
        String personName = command.getSecondWord();
        Room room = player.getCurrentRoom();
        Person person = room.getPerson(personName);
        
        if(person != null) {
            // Person is in the room
            System.out.println();
            if(player.isDirty()) {
                // player is dirty and must clean up before talking to anyone
                System.out.println(personName + ": Ew, you're covered in blood.");
            } else {
                // Talk to person
                switch(personName) {
                    case "ruby":
                        if(!ruby.isMet()) {
                            System.out.println("Ruby is a young woman currently crouched over a box.\n");
                            ruby.meet();
                            wait(500);
                        }
                        if(!ruby.hasItem("steak knife") && !ruby.hasItem("kitchen knife")) {
                            // Item key hasn't been given
                            System.out.println("Ruby: I'd love to help you, but I'm trying to open this package. " +
                                    "I'll talk to you once \nI get this open. If only I had a knife, or something.");
                        } else {
                            System.out.println("Ruby: You know, I heard Charles' daughter Lucy used to take his " +
                                    "things and hide them in \nher room.");
                            // Move the NPC
                            if(ruby.getCurrentRoom() != upstairs) {
                                System.out.println("Ruby gets up and leaves the room.");
                                ruby.setRoom(upstairs);
                                room.removePerson("ruby", ruby);
                                upstairs.addPerson("ruby", ruby);
                            }
                        }
                        break;
                    
                    case "olive":
                        if(!olive.isMet()) {
                            System.out.println("Olive is a middleaged woman with glasses.\n");
                            olive.meet();
                            wait(500);
                        }
                        System.out.println("Olive: I was in the main hall when the lights went out, but I could still " +
                                    "see because \nMr. Bodie had lit some candles. Nobody came in or out of that " +
                                    "study, except for Miriam, \nafter Marvin came running out of the kitchen covered "+
                                    "in blood saying he'd killed Mr. Bodie.");
                        break;
                        
                    case "ash":
                        if(!ash.isMet()) {
                            System.out.println("Ash is a strapping young man.\n");
                            ash.meet();
                            wait(500);
                        }
                        if(!ash.hasItem("jewelry box")) {
                            // Item key hasn't been given
                            System.out.println("Ash: I can't help you right now. I misplaced my wife's jewelry box, "+
                                        "she'll kill me if I \ndon't find it.");
                        } else {
                            System.out.println("Ash: I didn't see anything. I had just walked into the lounge looking " +
                                        "for a smoke when \nthe lights went out. I know Bodie has a cigar box in " +
                                        "there, but it's always empty.");
                        }
                        break;
                    
                    case "cole":
                        if(!cole.isMet()) {
                            System.out.println("Cole is a young man with what appears to be jam all over his hands.\n");
                            cole.meet();
                            wait(500);
                        }
                        if(!cole.hasItem("clothes")) {
                            // Item key hasn't been given
                            System.out.println("Cole: Hey, you wouldn't happen to have any kid's clothes on you, " +
                                        "would you? My kid \nmanaged to get jam all over her clothes and I need to " +
                                        "change her.");
                        } else {
                            System.out.println("Cole: The strangest thing happened the other day. Mr. Bodie keeps "+
                                        "the basement door locked \nall the time, but I could have sworn I heard " +
                                        "something down there.");
                            // Move the NPC
                            if(cole.getCurrentRoom() != kitchen) {
                                wait(500);
                                System.out.println("Cole leaves the room.");
                                cole.setRoom(kitchen);
                                room.removePerson("cole", cole);
                                kitchen.addPerson("cole", cole);
                            }
                        }
                        break;
                        
                    default:
                        System.out.println("They don't want to talk to you right now.");
                        break;
                }
            }
        }else {
            System.out.println("This person is not in the room.");
        }
    }

    /** 
     * "Quit" was entered. Check to see whether we really quit the game.
     * @return true, if quit is confirmed, false otherwise.
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
}
