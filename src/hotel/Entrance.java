package hotel;

/**
 *
 * @author stavr
 */
public class Entrance extends Zevgari{
    private Player owner;
    private int hotel;
    /**
     * A simple constructor for an Entrance Object.
     * @param myX initialized in super constructor.
     * @param myY initialized in super constructor.
     * @param myOwner reference to the Player who owns the entrance.
     * @param myHotel ID of the hotel the entrance belongs to
     */
    public Entrance(int myX, int myY, Player myOwner, int myHotel){
        super(myX,myY);
        owner = myOwner;
        hotel = myHotel;
    }
    
    /**
     * A simple setter for the hotel field.
     * @param myHotel assigned as value of the hotel field
     */
    public void setHotel(int myHotel){
        hotel = myHotel;
    }
    
    /**
     * A simple getter for the hotel field.
     * @return ID of the hotel the entrance belongs to
     */
    public int getHotel(){
        return hotel;
    }
    
    /**
     * A simple setter fro the owner field.
     * @param myOwner reference to a Player Object assigned to owner field 
     */
    public void setOwner(Player myOwner){
        owner = myOwner;
    }
    
    /**
     * A simple getter for the owner field.
     * @return reference to the Player Object who is owner of the entrance
     */
    public Player getOwner(){
        return owner;
    }
}
