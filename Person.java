import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

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
        currentRoom.visit();
    }
    
    /**
     * Take an item from the room.
     * @param itemName The name of the item to take
     */
    public void takeItem(String itemName)
    {
        if(currentRoom.hasItem(itemName)) {
            Item item = currentRoom.getItem(itemName);
            
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
            System.out.println("This item is not in the room.");
        }
    }
    
    /**
     * Drop item from inventory.
     * @param itemName The name of the item to drop
     */
    public void dropItem(String itemName)
    {
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
     * Try to examine an item. If the item is not in the room,
     * print an error message, otherwise print item details.
     * @param itemName The item to examine
     */
    public void examineItem(String itemName)
    {
        if(currentRoom.hasItem(itemName) && currentRoom.getItem(name).isFound()) {
            //Item is in the room and has been found
            Item item = currentRoom.getItem(itemName);
            System.out.println(item.getDetails());
            item.findItems();       //Find the items inside the item if any
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
     * @param person The person to give the item to
     */
    public void giveItem(String itemName, Person person)
    {
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
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param direction The exit direction for the new room.
     */
    public void goRoom(String direction)
    {
        nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("You cannot go that way!");
        } else if (nextRoom.isLocked()) {
            System.out.println("The door is locked.");
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
     *  Move NPC to new room.
     *  @param room The room to move the NPC to.
     */
    public void setRoom(Room room)
    {
        previousRoom = currentRoom;
        currentRoom = room;
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
