package hotel;

/**
 *
 * @author stavr
 */
public class Zevgari {
    
    private int x;
    private int y;
    
    /**
     * A simple constructor with 2 values for a Zevgari Object
     */
    public Zevgari (int myX, int myY){
        x = myX;
        y = myY;
    }
    
    /**
     * A simple getter for the x field.
     * @return the value of x
     */
    public int getX(){
        return x;
    }
    
    /**
     * A simple setter for the x field.
     * @param myX is assigned as the value of x
     */
    public void setX(int myX){
        x = myX;
    }
   
    /**
     * A simple getter for the y field.
     * @return the value of y
     */
    public int getY(){
        return y;
    }
    
    /**
     * A simple setter for the y field.
     * @param myY is assigned as the value of x
     */
    public void setY(int myY){
        y = myY;
    }
}
