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
    private boolean portable;
    private String name;
    private String details;
    private HashMap<String, Room> location;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, Room room, boolean portable)
    {
        location = new HashMap<String, Room>();
        found = false;
        this.name = name;
        
        
    }
    
    /**
     * Set the room the item is in
     */
    public void addToRoom()
    {
        
    }
    
    /**
     * Set the item description/ details
     */
    public void setDetails(String details)
    {
        this.details = details;
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
