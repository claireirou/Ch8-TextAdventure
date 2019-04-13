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
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int weight, boolean found, boolean usable)
    {
        items = new ArrayList<Item>();
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
     * Return item useable status
     */
    public boolean getUse()
    {
        return usable;
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
}
