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
    
    /**
     * Constructor for objects of class Person
     * @param inventorySize The max size of the person's inventory.
     */
    public Person(int inventorySize)
    {
        inventory = new ArrayList<Item>();
        this.inventorySize = inventorySize;
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
}