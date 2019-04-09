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
    private Room previousRoom;
    
    /**
     * Constructor for objects of class Person
     * @param inventorySize The max size of the person's inventory.
     * @param startRoom The room the person starts in.
     */
    public Person(int inventorySize, Room startRoom)
    {
        inventory = new ArrayList<Item>();
        this.inventorySize = inventorySize;
        currentRoom = startRoom;
        previousRoom = null;
    }
    
    /**
     * Add an item to inventory.
     * @param item The item to add
     */
    public void addItem(Item item)
    {
        if(inventory.size() <= inventorySize) {
            if(item.addToInventory(name)) {
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
     * @param currentRoom The room the item is dropped in
     */
    public void dropItem(Item item, Room currentRoom)
    {
        if(inventory.contains(item)) {
            inventory.remove(item);
            item.addToRoom(currentRoom);
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
     * Return the NPC's current room.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Return the NPC's previous room.
     * Returns null if the NPC hasn't moved.
     */
    public Room getPreviousRoom()
    {
        return previousRoom;
    }
}
