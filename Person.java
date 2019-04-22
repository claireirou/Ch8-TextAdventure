import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

/**
 * Class Person - a character in a text-based game
 * 
 * This class is part of the "Mansion Detective" application. 
 * "Mansion Detective" is a text based murder mystery adventure game.
 * 
 * A "Person" represents any character (player or NPC) in the game.
 * They have an inventory that stores item names and their object
 *
 * @author Claire Iroudayassamy
 * @version 2019.04.15
 */
public class Person
{
    private HashMap<String, Item> inventory;    // stores items this person has
    private int inventoryMax;                   // determines the max amount person can carry
    private int inventoryWeight;
    private String name;
    private Room currentRoom;                   
    private Room nextRoom;
    private Room previousRoom;
    private int decked;                         // rudimentary health system
    private boolean dirty;
    private boolean conscious;                  // determines if person is "dead"
    private String itemKey; 
    private boolean met;

    /**
     * Create a person. Initially they only have a name,
     * their inventory capacity, and the room they start in.
     * @param name The name of the person
     * @param maxWeight The max weight a person can carry.
     * @param startingRoom The room the person starts in.
     */
    public Person(String name, int maxWeight, Room startingRoom)
    {
        this.name = name;
        inventory = new HashMap<String, Item>();
        inventoryMax = maxWeight;
        currentRoom = startingRoom;
        previousRoom = null;
        inventoryWeight = 0;
        decked = 0;
        dirty = false;
        conscious = true;
        met = false;
    }
    
    /**
     * Take an item from the room and add it to inventory.
     * @param itemName The name of the item to take
     */
    public void takeItem(String itemName)
    {
        System.out.println();
        if(currentRoom.hasItem(itemName)) {
            Item item = currentRoom.getItem(itemName);
            if(item.isFound()) {
                if(itemName.equals("backpack")) {
                    inventoryMax += 20;
                    System.out.println("You have found a backpack! You can now carry more items");
                    System.out.println("Inventory weight: " + inventoryWeight + "lbs/ " + inventoryMax +"lbs\n");
                    currentRoom.removeItem(itemName, item);
                    return;
                }
                
                if(addItem(itemName, item)) {
                    // Item was successfully added
                    currentRoom.removeItem(itemName, item);     // remove from room
                    if(item.getContainer() != null) {
                        // Item was in a container
                        item.getContainer().remove(item);       // take item out of its container
                        item.setContainer(null);                
                    }
                    System.out.println("Item added to inventory.");
                }
                
                // Print out inventory weight
                System.out.println("Inventory weight: " + inventoryWeight + "lbs/ " + inventoryMax +"lbs\n");
            } else {
                System.out.println("Item not found.");
            }
        } else {
            System.out.println("This item is not in the room.");
        }
    }
    
    /**
     * Drop item from inventory and add it to current room.
     * @param itemName The name of the item to drop
     */
    public void dropItem(String itemName)
    {
        System.out.println();
        if(itemName.equals("backpack")) {
            System.out.println("You cannot drop this item.");
            return;
        }
        
        if(inventory.containsKey(itemName)) {
            Item item = inventory.get(itemName);
            removeItem(itemName, item);
            currentRoom.addItem(itemName, item);
            System.out.println(item.getName() + " dropped.");
            System.out.println("Inventory weight: " + inventoryWeight + " lbs/ " + inventoryMax +" lbs\n");
        } else {
            System.out.println("You do not have this item in your inventory!");
        }
    }
    
    /**
     * Remove a used item from inventory. Feed it to the void.
     *      (Item will no longer have any location reference)
     * @param itemName The name of the item to remove
     */
    public void useItem(String itemName)
    {
        Item item = inventory.get(itemName);
        removeItem(itemName, item);
    }
    
    /**
     * Try to examine an item. If the item is not in the room,
     * print an error message, otherwise print item details.
     * @param itemName The item to examine
     */
    public void examineItem(String itemName)
    {
        Item item = currentRoom.getItem(itemName);
        System.out.println();
        if(item != null && item.isFound()) {
            //Item is in the room and has been found
            System.out.println(item.getDetails());
            
            if(!item.isLocked()) {
                // Item is not locked
                item.findItems();       //Find any items inside the item.
            } else if(itemName.equals("candelabrum")) {
                System.out.println("It's unlit");
            } else if(itemName.equals("painting") || itemName.equals("bookcase")) {
                System.out.print("");
            } else if(!itemName.equals("cigar box")) {
                System.out.println("It's locked.");
            } 
            
        } else if(inventory.containsKey(itemName)){
            // Item is in inventory
            item = inventory.get(itemName);
            System.out.println(inventory.get(itemName).getDetails());
            item.findItems();
        } else {
            System.out.println("This item is not in the room.");
        }
    }
    
    /**
     * Give an item to another person. If the receiver's inventory is 
     * full or the item is not in the giver's inventory print out error 
     * message.
     * @param itemName The name of the item to give
     * @param personName The name of the person to give the item to
     * @return true if give was successful, else false
     */
    public void giveItem(String itemName, Person person)
    {
        System.out.println();
        if(inventory.containsKey(itemName)) {
            // Giver has the item
            Item item = inventory.get(itemName);
            if((person.getInventorySize() + item.getWeight()) >= person.getInventoryMax()) {
                // Receiver's inventory is full.
                System.out.println("Inventory is full! Item not given.");
            } else if(!itemName.equals(person.getItemKey())){
                System.out.println(person.getName() + ": I don't want that.");
            }else {
                // give item
                removeItem(itemName, item);
                person.addItem(itemName, item);
                System.out.println("Item successfully given.");
            }
        } else {
            System.out.println("Item not in inventory.");
        }
    }
    
    /**
     * Print out inventory details in the form:
     *      "Items:
     *       book: 3 lbs
     *       pen: 1 lbs"
     */
    public void showInventory()
    {
        Set <String> keys = inventory.keySet();
        System.out.println("_____________________________________");
        
        // Print out current inventory weight and capacity.
        System.out.println("Inventory: " + inventoryWeight + " lbs/ " + inventoryMax +" lbs\n");
        System.out.println("Items:");
        for(String item : keys) {
            System.out.println(item + ": " + inventory.get(item).getWeight() + " lbs");
        }
        System.out.println("_____________________________________");
    }
    
    /**
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param direction The exit direction for the new room.
     */
    public void goRoom(String direction)
    {
        nextRoom = currentRoom.getExit(direction);
        System.out.println();
        if(direction.equals("gate")) {
            
        }
        if (nextRoom == null) {
            // Exit doesn't exist
            System.out.println("You cannot go that way!");
        } else if (nextRoom.isLocked()) {
            // Door is locked
            System.out.println("The door is locked.");
            nextRoom = null;
        } else if(nextRoom.isBlocked()) {
            // Door is blocked
            System.out.println("The way is too dark to navigate.");
            nextRoom = null;
        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            nextRoom = null;
            
            // Print out new room details.
            if(currentRoom.visited()) {
                System.out.println(currentRoom.getLongDescription());
            } else {
                currentRoom.visit();
                System.out.println(currentRoom.getLookDescription());
            }
        }
    }
    
    /**
     * Go back to the previous room.
     */
    public void goBack()
    {
        System.out.println();
        if(currentRoom.isLocked()) {
            System.out.println("The door is locked!");
        } else {
            nextRoom = previousRoom;
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            nextRoom = null;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Eat some food to heal up.
     */
    public void eat()
    {
        decked -= 5;
        System.out.println("It's delicious and you feel better. \nHealth +5");
        if(decked < 0) {
            decked = 0;
        }
    }
    
    /**
     * Take a nap to heal up.
     */
    public void sleep()
    {
        System.out.println();
        decked -= 10;
        System.out.println("You wake up. You're not sure how much time has passed, but you feel much better. \nHealth +10");
        if(decked < 0) {
            decked = 0;
        }
    }
    
    /**
     * Touch the gross piece of hanging meat.
     */
    public void useCarcass()
    {
        System.out.println();
        System.out.println("You examine the carcass more closely; searching inside of it to make sure there's nothing " +
                           "\nthere. You find nothing. Your arms and clothes are covered in blood and gross meat-stuff.");
        dirty = true;
    }
    
    /**
     * Take a bath to clean up and heal up.
     */
    public void takeBath()
    {
        System.out.println();
        decked -= 2;
        dirty = false;
        System.out.println("You are now squeaky clean and you feel marginally better after your hour long bath. \nHealth +2");
        if(decked < 0) {
            decked = 0;
        }
    }
    
    /**
     * Take a shower to clean up and heal up.
     */
    public void shower()
    {
        System.out.println();
        decked -= 5;
        dirty = false;
        System.out.println("You've quickly and effeciently showered. You feel a bit better, too. \nHealth +5");
        if(decked < 0) {
            decked = 0;
        }
    }
    
    /**
     * Try to attack an NPC. Fail miserably.
     */
    public void attack()
    {
        System.out.println("Health -10");
        decked += 10;
        dirty = true;
        if(decked >= 30) {
            conscious = false;
        }
    }
    
    /**
     * Return if an NPC has been met
     * @return true if met, else false
     */
    public boolean isMet()
    {
        return met;
    }
    
    /**
     * Meet an NPC.
     */
    public void meet()
    {
        met = true;
    }
    
    /**
     * Get consciousness status.
     * @return true if awake, false if passed out.
     */
    public boolean isConscious()
    {
        return conscious;
    }
    
    /**
     * Return item if it is in inventory. 
     * If the item is not in inventory return null, 
     *      otherwise return the item.
     * @param itemName The name of the item we want
     * @return the item object we want to use
     */
    public Item getItem(String itemName)
    {
            return inventory.get(itemName);
    }
    
    /**
     * Check if item is in inventory.
     * @param itemName The name of the item to check for
     * @return true if item is in inventory, else false.
     */
    public boolean hasItem(String itemName)
    {
        return inventory.containsKey(itemName);
    }

    /**
     *  Move NPC to new room.
     *  @param room The room to move the NPC to.
     */
    public void setRoom(Room room)
    {
        previousRoom = currentRoom;
        currentRoom = room;
    }
    
    /**
     * Return the person's dirty status.
     * @return true if dirty, else false.
     */
    public boolean isDirty()
    {
        return dirty;
    }
    
    /**
     * Return the person's current room.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Return the person's previous room.
     * Returns null if the NPC hasn't moved.
     */
    public Room getPreviousRoom()
    {
        return previousRoom;
    }
    
    /**
     * Return the person's name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Set the item name that unlocks person hints.
     * @param itemName The name of the item key.
     */
    public void setItemKey(String itemName)
    {
        itemKey = itemName;
    }
    
    /**
     * Return the name of the item that unlocks person hints.
     * @return the name of the item
     */
    public String getItemKey()
    {
        return itemKey;
    }
    
    /**
     * Return inventory capacity.
     */
    public int getInventoryMax()
    {
        return inventoryMax;
    }
    
    /**
     * Return current inventory weight.
     */
    public int getInventorySize()
    {
        return inventoryWeight;
    }

    /**
     * Try to add an item to inventory.
     * @param itemName The name of the item to add
     * @return true if item was successfully added,
     *      else return false
     */
    private boolean addItem(String itemName, Item item)
    {
        if(item.getWeight() >= 50) {
            // Item is too heavy to be carried.
            System.out.println("You are too weak to carry this item.");
            return false;
        } else {
            if((item.getWeight() + inventoryWeight) <= inventoryMax) {
                // Inventory has room for item
                inventory.put(itemName, item);          // add to inventory
                inventoryWeight += item.getWeight();    //adjust inventory weight
                return true;
            } else {
                // Inventory is too full
                System.out.println("You cannot carry anymore.");
                System.out.println("This item weighs " + item.getWeight() + "lbs");
                return false;
            }   
        }
    }
    
    /**
     * Remove an item from inventory and adjust inventory weight
     * @param itemName The name of the item
     * @param item The item to remove
     */
    private void removeItem(String itemName, Item item)
    {
        inventory.remove(itemName, item);
        inventoryWeight -= item.getWeight();
    }
}
