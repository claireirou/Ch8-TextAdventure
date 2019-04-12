import java.util.HashMap;

/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private boolean found;
    private final int weight;
    private String name;
    private String details;
    private Item item;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int weight)
    {
        found = true;
        this.name = name;
        item = null;
        this.weight = weight;
    }
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, Item item, int weight)
    {
        found = false;
        this.name = name;
        this.item = item;
        this.weight = weight;
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
    public String examine()
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
}
