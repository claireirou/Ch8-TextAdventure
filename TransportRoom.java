
/**
 * Write a description of class TransportRoom here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TransportRoom extends Room
{

    /**
     * Constructor for objects of class TransportRoom
     * @param description The description of the room
     * @param locked The locked status of the room
     */
    public TransportRoom(String description, boolean locked)
    {
        super(description, locked);
    }
    
    /**
     *  Return a random room, independent of the direction
     *  parameter.
     *  @param direction Ignored.
     *  @return A random room.
     */
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }
    
    /**
     * Choose a random room.
     * @return A random room.
     */
    private Room findRandomRoom()
    {
        
        return room;
    }
}
