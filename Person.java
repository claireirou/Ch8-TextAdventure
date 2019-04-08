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
    private String name;
    
    /**
     * Constructor for objects of class Person
     */
    public Person()
    {
        inventory = new ArrayList<Item>();
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
