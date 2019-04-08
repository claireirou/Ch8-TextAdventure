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
    private Room room;
    private String person;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, Room room, boolean portable)
    {
        found = false;
        this.name = name;
        this.portable = portable;
        this.room = room;
    }
    
    /**
     * Set the room the item is in.
     * 
     * @param room The room the item is located in.
     */
    public void addToRoom(Room room)
    {
        this.room = room;
        person = null;
    }
    
    /**
     *  Add item to player inventory.
     *  @param person The person inventory to add the item to.
     *  @return true if item is added and false if item cannont
     *      be added.
     */
    public boolean addToInventory(String person)
    {
       if(portable) {
            this.person = person;
            room = null;
            return true;
       }
       return false;
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
    
    /**
     *  Return what room the item is in.
     *  Returns null if the item is in an inventory.
     */
    public Room getRoom()
    {
        return room;
    }
}