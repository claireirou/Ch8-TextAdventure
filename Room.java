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
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        name = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
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
     *     Options: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + name + ".\n" + getExitString();
    }
    
    /**
     * 
     */
    public String getLookDescription()
    {
        return null;
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
     * "Options: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Options:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
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
     * Add item in the room to ArrayList
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Remove an item from the room.
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }
    
    /**
     * Change a room's status to visited
     */
    public void visit()
    {
        visited = true;
    }
}

