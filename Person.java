import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

/**
 * Write a description of class Person here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Person
{
    private HashMap<String, Item> inventory;
    private int inventoryMax;
    private int inventoryWeight;
    private String name;
    private Room currentRoom;
    private Room nextRoom;
    private Room previousRoom;
    private int decked;
    private boolean dirty;
    private boolean conscious;
    
    
    
    //TODO show inventory
    
    
    
    
    
    
    /**
     * Constructor for objects of class Person
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
    }
    
    /**
     * Take an item from the room.
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
                    return;
                }
                
                if(addItem(itemName, item)) {
                    // Item was successfully added
                    currentRoom.removeItem(itemName, item);     // remove from room
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
     * Drop item from inventory.
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
            System.out.println("Inventory weight: " + inventoryWeight + "lbs/ " + inventoryMax +"lbs\n");
        } else {
            System.out.println("You do not have this item in your inventory!");
        }
    }
    
    /**
     * Remove a used item from inventory.
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
            //Item item = currentRoom.getItem(itemName);
            System.out.println(item.getDetails());
            
            if(!item.isLocked()) {
                // Item is not locked
                item.findItems();       //Find any items inside the item.
            } else if(itemName.equals("candelabrum")) {
                System.out.println("It's unlit");
            } else if(!itemName.equals("cigar box")) {
                System.out.println("It's locked.");
            }
        } else if(inventory.containsKey(itemName)){
            System.out.println(inventory.get(itemName).getDetails());
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
            Item item = inventory.get(itemName);
            if((person.getInventorySize() + item.getWeight()) >= person.getInventoryMax()) {
                System.out.println("Inventory is full! Item not given.");
            } else {
                removeItem(itemName, item);
                person.addItem(itemName, item);
                System.out.println("Item successfully given.");
            }
        } else {
            System.out.println("Item not in inventory.");
        }
    }
    
    /**
     * Print out inventory details.
     */
    public void showInventory()
    {
        Set <String> keys = inventory.keySet();
        System.out.println("_____________________________________");
        System.out.println("Inventory: " + inventoryWeight + "lbs/ " + inventoryMax +"lbs\n");
        for(String item : keys) {
            System.out.println(item + ": " + inventory.get(item).getWeight() + "lbs");
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
        if (nextRoom == null) {
            System.out.println("You cannot go that way!");
        } else if (nextRoom.isLocked()) {
            System.out.println("The door is locked.");
            nextRoom = null;
        } else if(nextRoom.isBlocked()) {
            System.out.println("The way is too dark to navigate.");
            nextRoom = null;
        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            nextRoom = null;
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
        System.out.println();
        decked -= 5;
        System.out.println("It's delicious and you feel better.");
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
        System.out.println("You wake up. You're not sure how much time has passed, but you feel much better.");
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
        System.out.println("You are now squeaky clean and you feel marginally better after your hour long bath.");
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
        System.out.println("You've quickly and effeciently showered. You feel a bit better, too.");
        if(decked < 0) {
            decked = 0;
        }
    }
    
    /**
     * Try to attack an NPC. Fail miserably.
     */
    public void attack()
    {
        System.out.println();
        decked += 10;
        dirty = true;
        if(decked >= 30) {
            conscious = false;
        }
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
     * Return inventory capacity.
     */
    public int getInventoryMax()
    {
        return inventoryMax;
    }
    
    /**
     * Return current inventory size.
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
            System.out.println("You are too weak to carry this item.");
            return false;
        } else {
            if((item.getWeight() + inventoryWeight) <= inventoryMax) {
                inventory.put(itemName, item);          // add to inventory
                inventoryWeight += item.getWeight();    //adjust inventory weight
                return true;
            } else {
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
