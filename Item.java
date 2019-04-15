import java.util.ArrayList;

/**
 * Class Item - an item in a text-based game.
 * 
 * This class is part of the "Mansion Detective" application. 
 * "Mansion Detective" is a text based murder mystery adventure game. 
 * 
 * An "Item" represents an object a person can interact with. It has 
 * a weight and can be locked. An item can also have items inside of it.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private boolean found;  
    private boolean locked;
    private final int weight;
    private String name;
    private String details;
    private ArrayList<Item> items;          // Stores all the items inside this item
    private Item container;                 // Stores the item this item is contained in
    
    /**
     * Create an item named "name". Initially it has a weight
     * and is either out in the open or hidden.
     */
    public Item(String name, int weight, boolean found)
    {
        items = new ArrayList<Item>();
        container = null;
        this.found = found;             // Determines if item has been found
        this.name = name;
        this.weight = weight;
        locked = false;
    }
    
    /**
     *  Return the weight of an item
     */
    public int getWeight()
    {
       return weight;
    }
    
    /**
     * Set the item description/ details
     */
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    /**
     * Change item status to found.
     */
    public void found()
    {
        this.found = true;
    }
    
    /**
     *  Return a string description of item.
     */
    public String getDetails()
    {
        return details;
    }
    
    /**
     * Return what the item is
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the item this item is contained in.
     * @return the container item or null if there is 
     *      no container.
     */
    public Item getContainer()
    {
        return container;
    }
    
    /**
     * Set the container item this item is in
     */
    public void setContainer(Item container)
    {
        this.container = container;
    }
    
    /**
     * Lock an item.
     */
    public void lock()
    {
        locked = true;
    }
    
    /**
     * Unlock an item. If item has items in it,
     * print those items' details.
     */
    public void unlock()
    {
        locked = false;
        findItems();
    }
    
    /**
     * Return lock status
     * @return true if an item is locked, otherwise
     *      return false
     */
    public boolean isLocked()
    {
        return locked;
    }
    
    /**
     * Return item found status
     * @return true if found, else false
     */
    public boolean isFound()
    {
        return found;
    }
    
    /**
     * Add an item inside another item.
     *      e.g. Add key inside of box
     * @param item The item to be added inside
     */
    public void add(Item item) 
    {
        items.add(item);
    }
    
    /**
     * Remove an item from it's container.
     * @param item The item to remove
     */
    public void remove(Item item)
    {
        items.remove(item);
        item.setContainer(null);
    }
    
    /**
     * See if an item has items in it.
     * @return true if yes, else false.
     */
    public boolean hasItems()
    {
        return !items.isEmpty();
    }
    
    /**
     * Find the items inside this item and 
     * print out their details.
     */
    public void findItems()
    {
        if(!items.isEmpty()) {
            //Item has items in it.
            System.out.println("Inside there is: ");
            for(Item item : items) {
                item.found();                                   // Find the items inside
                System.out.println("\t" + item.getDetails());   // Print their details  
            }
        }
    }
}
