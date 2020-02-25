package hotel;

/**
 *
 * @author stavr
 */
public class FacultySquare extends Square{
    private int nextX;
    private int nextY;
    /**
     * A simple constructor for a FacultySquare Object.
     * @param myX is initialized in super constructor.
     * @param myY is initialized in super constructor.
     * @param myEidos is initialized in super constructor
     */
    public FacultySquare(int myX, int myY, String myEidos){
        super(myX,myY, myEidos);
    }
    
    /**
     * A simple getter for the nextX field.
     * @return the X-coordinate of the next in order square of the grid
     */
    public int getNextX(){
        return nextX;
    }
    
    /**
     * A simple setter for the nextX field.
     * @param myNextX assigned as the value of the nextX field
     */
    public void setNextX(int myNextX){
        nextX = myNextX;
    }
    
    /**
     * A simple getter for the nextY field.
     * @return the Y-coordinate of the next in order square of the grid
     */
    public int getNextY(){
        return nextY;
    }
    
    /**
     * A simple setter for the nextY field.
     * @param myNextY assigned as the value of the nextY field
     */
    public void setNextY(int myNextY){
        nextY = myNextY;
    }
}
