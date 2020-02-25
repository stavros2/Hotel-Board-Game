package hotel;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author stavr
 */
public class Player {
    private String name;
    private Color xroma;
    private int maxMoney, moneyHeld,  x, y;
    private ArrayList<Integer> hotelsOwned;
    private ArrayList<Entrance> eisodoi;
    private boolean passedMayor, passedBank;
    
    /**
     * A simple constructor for a Player Object.
     * @param myName the name of the player.
     * @param myXroma the color representing the player
     */
    public Player(String myName, Color myXroma){
        xroma = myXroma;
        name = myName;
        maxMoney = 12000;
        passedBank = false;
        passedMayor = false;
        moneyHeld = 12000;
        hotelsOwned = new ArrayList<>();
        eisodoi = new ArrayList<>();
    }
    
    /**
     * A simple getter for the xroma field.
     * @return color representing the player
     */
    public Color getXroma(){
        return xroma;
    }
    
    /**
     * A simple setter for the xroma field.
     * @param myXroma assigned as the value of xroma field
     */
    public void setXroma(Color myXroma){
        xroma = myXroma;
    }
    
    /**
     * A simple getter for the y field.
     * @return Y-coordinate of the square the player is currently on
     */
    public int getY(){
        return y;
    }
    
    /**
     * A simple setter for the y field.
     * @param myY assigned as the value of the y field
     */
    public void setY(int myY){
        y = myY;
    }
    
     /**
     * A simple getter for the x field.
     * @return X-coordinate of the square the player is currently on
     */
    public int getX(){
        return x;
    }
   
     /**
     * A simple setter for the x field.
     * @param myX assigned as the value of the x field
     */
    public void setX(int myX){
        x = myX;
    }
    
    /**
     * A simple getter for the name field.
     * @return name of the player
     */
    public String getName(){
        return name;
    }
    
    /**
     * A simple setter for the name field.
     * @param myName assigned as the name of the player
     */
    public void setName(String myName){
        name = myName;
    }
    
    /**
     * A simple getter for the maxMoney field.
     * @return maximum amount of money a player has achieved during the game
     */
    public int getMaxMoney(){
        return maxMoney;
    }
    
    /**
     * A simple setter for the maxMoney field.
     * @param money assigned as the value of the maxMoney field
     */
    public void setMaxMoney(int money){
       maxMoney = money;
    }
    
    /**
     * A simple getter for the money field.
     * @return amount of money the player has
     */
    public int getMoneyHeld (){
        return moneyHeld;
    }
    
    /**
     * A simple setter for the money field.
     * @param amount is added to the player's money.
     * After the addition the maxMoney field is updated if needed
     */
    public void setMoneyHeld (int amount){
        moneyHeld += amount;
        if (moneyHeld > maxMoney)
            maxMoney = moneyHeld;
    }
    
    /**
     * A simple getter for the hotelsOwned field.
     * @return list of ID's of the hotels the player owns
     */
    public ArrayList<Integer> getHotelsOwned(){
        return hotelsOwned;
    }
    
    /**
     * A simple setter for the hotelsOwned field.
     * @param hotel ID of the hotel to be added in the list 
     */
    public void setHotelsOwned(int hotel){
        hotelsOwned.add(hotel);
    }
    
    /**
     * A simple setter for the hotelsOwned field.
     * @param hotel ID removed from the list of hotels the player owns 
     */
    public void removeHotel(int hotel){
        hotelsOwned.remove(new Integer(hotel));
    }
    
    /**
     * A simple getter for the passedMayor field.
     * @return true if the player has passed the city hall, false otherwise
     */
    public boolean getPassedMayor(){
        return passedMayor;
    }
    
    /**
     * A simple setter for the passedMayor field.
     * @param bool assigned as value of the passedMayor field
     */
    public void setPassedMayor(boolean bool){
        passedMayor = bool;
    }
    
    /**
     * A simple getter for the passedBank field.
     * @return true if the player has passed the bank, false otherwise
     */
    public boolean getPassedBank(){
        return passedBank;
    }
    
    /**
     * A simple setter for the passedBank field.
     * @param bool assigned as value of the passedBank field
     */
    public void setPassedBank(boolean bool){
        passedBank = bool;
    }
    
    /**
     * A simple getter for the eisodoi field.
     * @return list of entrances the player has
     */
    public ArrayList<Entrance> getEisodoi(){
        return eisodoi;
    }
    
    /**
     * A simple setter for the eisodoi field.
     * @param myEisodos added to the eisodoi list
     */
    public void setEisodoi(Entrance myEisodos){
        eisodoi.add(myEisodos);
    }
}
