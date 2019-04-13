import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private boolean found;
    private final boolean usable;
    private final int weight;
    private String name;
    private String details;
    private ArrayList<Item> items;
    private Item container;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int weight, boolean found, boolean usable)
    {
        items = new ArrayList<Item>();
        container = null;
        this.found = found;
        this.name = name;
        this.weight = weight;
        this.usable = usable;
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
     * Return item useable status.
     * @return true if item can be used, else false
     */
    public boolean getUse()
    {
        return usable;
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
     * Find the items inside this item
     */
    public void findItems()
    {
        if(!items.isEmpty()) {
            //Item has items in it.
            System.out.println("Inside the " + getName() + " there is: ");
            for(Item item : items) {
                item.found();                                   // Find the items inside
                System.out.println("\t" + item.getDetails());   // Print their details  
            }
        }
    }
}
