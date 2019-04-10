import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class Person here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Person
{
    private ArrayList<Item> inventory;
    private final int inventorySize;
    private String name;
    private Room currentRoom;
    private Room nextRoom;
    private Room previousRoom;
    
    /**
     * Constructor for objects of class Person
     * @param inventorySize The max size of the person's inventory.
     * @param startRoom The room the person starts in.
     */
    public Person(String name, int inventorySize, Room startingRoom)
    {
        this.name = name;
        inventory = new ArrayList<Item>();
        this.inventorySize = inventorySize;
        currentRoom = startingRoom;
        previousRoom = null;
        currentRoom.visit();
    }
    
    /**
     * Add an item to inventory.
     * @param item The item to add
     */
    public void addItem(Item item)
    {
        if(inventory.size() <= inventorySize) {
            if(item.addToInventory(name, item)) {
                inventory.add(item);
            } else {
                System.out.println("This item cannot be carried.");
            }
        } else {
            System.out.println("You cannot carry anymore items.");
        }
    }
    
    /**
     * Drop item from inventory.
     * @param item The item to drop
     * @param currentRoom The room to drop the item in
     */
    public void dropItem(Item item, Room currentRoom)
    {
        if(inventory.contains(item)) {
            inventory.remove(item);
            item.addToRoom(currentRoom, item);
            System.out.println(item.getName() + " dropped.");
        } else {
            System.out.println("You do not have this item in your inventory!");
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
                System.out.println(currentRoom.getLongDescription());
            }
        }
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
}
