package hotel;

/**
 *
 * @author stavr
 */
public class Square extends Zevgari {
    private String eidos;

    /**
     * A simple constructor for a Square Object.
     * @param myX is initialized in super constructor.
     * @param myY is initialized in super constructor.
     * @param myEidos representing the type of each square in the grid
     */
    public Square (int myX, int myY, String myEidos){
        super(myX, myY);
        eidos = myEidos;
    }
   
    /**
     * A simple getter for the eidos field.
     * @return the value of field eidos
     */
    public String getEidos(){
        return eidos;
    }
    
    /**
     * A simple setter for the eidos field.
     * @param myEidos is assigned as value of the field eidos
     */
    public void setEidos(String myEidos){
        eidos = myEidos;
    }
}
