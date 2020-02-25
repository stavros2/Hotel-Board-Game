package hotel;

/**
 *
 * @author stavr
 */
public class HotelSquare extends Square{
    private int hotelID;
    
    /**
     * A simple constructor for a HotelSquare Object
     * @param myX is initialized in super constructor.
     * @param myY is initialized in super constructor.
     * @param myEidos is initialized in super constructor.
     * @param myHotelID is the id of the hotel this square belongs
     */
    public HotelSquare(int myX, int myY, String myEidos, int myHotelID){
        super(myX, myY, myEidos);
        hotelID = myHotelID;
    }
    
    /**
     * A simple getter for the hotelID field.
     * @return the ID of the hotel the square belongs to
     */
    public int getHotelID(){
        return hotelID;
    }
    
    /**
     * A simple setter for the hotelID field.
     * @param myHotelID is assigned as the id of the hotel 
     * this square belongs to
     */
    public void setHotelID(int myHotelID){
        hotelID = myHotelID;
    }
}
