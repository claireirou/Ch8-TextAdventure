import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Room 
{
    private String name;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private String lookDescription;
    private boolean visited;
    private boolean locked;
    private HashMap<String, Item> items;
    private ArrayList<Person> characters;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, boolean locked) 
    {
        name = description;
        this.locked = locked;
        exits = new HashMap<String, Room>();
        items = new HashMap<String, Item>();
        characters = new ArrayList<Person>();
        visited = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return name;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exit: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + name + ".\n" + getExitString();
    }
    
    /**
     *  Return a detailed description of the room.
     *  @return A look description of this room
     */
    public String getLookDescription()
    {
        return lookDescription + "\n" + getExitString();
    }
    
    /**
     *  Set a detailed description of the room and
     *  the items in it.
     */
    public void setLookDescription(String description)
    {
        lookDescription = description;
    }
    
    /**
     * Return whether a room has been previously visited by player.
     * @return true if yes, false if player has never been there.
     */
    public boolean visited()
    {
        return visited;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit + ",";
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     *  Remove an exit from this room.
     *  @param direction The direction of the exit
     */
    public void removeExit(String direction)
    {
        exits.remove(direction);
    }
    
    /**
     * Add item in the room to ArrayList
     */
    public void addItem(String name, Item item)
    {
        items.put(name, item);
    }
    
    /**
     * Remove an item from the room.
     */
    public void removeItem(String name, Item item)
    {
        items.remove(name, item);
    }
    
    /**
     * Return a given item if it is in the room.
     * @param name The name of the item
     */
    public Item getItem(String name)
    {
        return items.get(name);
    }
    
    /**
     * See if a given item is in the room
     * @param name The name of the item
     * @return true if item is in room, else false.
     */
    public boolean hasItem(String name)
    {
        return items.containsKey(name);
    }
    
    /**
     * Change a room's status to visited
     */
    public void visit()
    {
        visited = true;
    }
    
    /**
     * Get the room's locked status
     * @return true, if the room is locked, false if it isn't
     */
    public boolean isLocked()
    {
        return locked;
    }
    
    /**
     * Unlock the room. If the room is already unlocked
     * do nothing.
     */
    public void unlock()
    {
        if(locked) {
            locked = false;
        }
    }
}

