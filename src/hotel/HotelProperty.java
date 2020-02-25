package hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author stavr
 */
public class HotelProperty{
    private String name, owner;
    private int hotelID, buyCost, minBuyCost, entranceCost, maxUpgrade, curUpgrade;
    private ArrayList<Zevgari> upgrades;
    private boolean bought;
    
    /**
     * A simple constructor for a HotelProperty Object
     * @param hotelID is the ID of the hotel. Everything else is
     * parsed from the appropriate .txt file.
     * owner is the name of the player currently owning the hotel.
     * curUpgrade is the current level of upgrade for the hotel, whilst maxUpgrade is the maximum possible level of upgrade.
     * bought indicates if the hotel has been bought by someone.
     * upgrades is the list of each cost and benefits for each upgrade.
     * name is the name of the hotel.
     * buyCost/minBuyCost indicate the amount needed for purchasing the hotel
     * entranceCost is the cost for placing an entrance for this hotel
     */
    public HotelProperty(int hotelID){
        owner = "";
        curUpgrade = 0;
        bought = false;
        this.hotelID = hotelID;
        upgrades = new ArrayList<>();
        String fileName = "file::../../resources/" + Integer.toString(hotelID) + ".txt";
        try{
            File arxeio = new File(fileName);
            Scanner myScan;
            myScan = new Scanner(arxeio).useDelimiter(",|\\r\n");
            name = myScan.nextLine();
            buyCost = myScan.nextInt();
            minBuyCost = myScan.nextInt();
            entranceCost = myScan.nextInt();
            while (myScan.hasNext()){
                upgrades.add(new Zevgari(myScan.nextInt(), myScan.nextInt()));
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        maxUpgrade = upgrades.size();
    }
    
    public HotelProperty(String myOwner, int myMaxUpgrade, int myCurUpgrade, boolean myBought, int myHotelID, String myName, int myBuyCost, int myMinBuyCost, int myEntranceCost, ArrayList<Zevgari> myUpgrades){
        owner = myOwner;
        maxUpgrade = myMaxUpgrade;
        curUpgrade = myCurUpgrade;
        bought = myBought;
        hotelID = myHotelID;
        upgrades = myUpgrades;
        name = myName;
        buyCost = myBuyCost;
        minBuyCost = myMinBuyCost;
        entranceCost = myEntranceCost;
    }
    
    /**
     * A simple getter for the owner field.
     * @return the name of the Owner of the hotel
     */
    public String getOwner(){
        return owner;
    }
    
    /**
     * A simple setter for the owner field.
     * @param myOwner is the value to be assigned in the owner field
     */
    public void setOwner(String myOwner){
        owner = myOwner;
    }
    
    /**
     * A simple getter for the maxUpgrade field.
     * @return the maximum upgrade level possible for this hotel
     */
    public int getMaxUpgrade(){
        return maxUpgrade;
    }
    
    /**
     * A simple setter for the maxUpgrade field.
     * @param myMaxUpgrade is the value to be assigned to the maxUpgrade field
     */
    public void setMaxUpgrade(int myMaxUpgrade){
        maxUpgrade = myMaxUpgrade;
    }
    
    /**
     * A simple getter for the curUpgrade field.
     * @return the current level of upgrade the hotel has
     */
    public int getCurUpgrade(){
        return curUpgrade;
    }
    
    /**
     * Used instead of a setter to increase the level of upgrade by 1
     */
    public void incUpgrade(){
        curUpgrade++;
    }
    
    /**
     * A simple getter for the upgrades field.
     * @return the list of the possible upgrades for the hotel
     */
    public ArrayList<Zevgari> getUpgrades(){
        return upgrades;
    }
    
    /**
     * A simple setter for the upgrades field.
     * @param myUpgrades is the list to be assigned
     */
    public void setUpgrades(ArrayList<Zevgari> myUpgrades){
        upgrades = myUpgrades;
    }
    
    /**
     * A simple getter for the name field.
     * @return the name of the hotel
     */
    public String getName(){
        return name;
    }
    
    /**
     * A simple setter for the name field.
     * @param myName is the new name of the hotel
     */
    public void setName(String myName){
        name = myName;
    }
    
    /**
     * A simple getter for the buyCost field.
     * @return the price for buying the hotel if no player owns it
     */
    public int getBuyCost(){
        return buyCost;
    }
    
    /**
     * A simple setter for the buyCost field.
     * @param myBuyCost the value to be assigned to the buyCost field
     */
    public void setBuyCost(int myBuyCost){
        buyCost = myBuyCost;
    }
    
    /**
     * A simple getter for the minBuyCost field.
     * @return  the price for buying the hotel from another player
     */
    public int getMinBuyCost(){
        return minBuyCost;
    }
    
    /**
     * A simple setter for the minBuyCost field.
     * @param myMinBuyCost the value to be assigned to minBuyCost field
     */
    public void setMinBuyCost(int myMinBuyCost){
        minBuyCost = myMinBuyCost;
    }
    
    /**
     * A simple getter for the entranceCost field.
     * @return the price for buying an entrance for the hotel 
     */
    public int getEntranceCost(){
        return entranceCost;
    }
    
    /**
     * A simple setter for the entranceCost field.
     * @param myEntranceCost is assigned as the entranceCost value
     */
    public void setEntranceCost(int myEntranceCost){
        entranceCost = myEntranceCost;
    }
    
    /**
     * A simple getter for the HotelID field.
     * @return the ID of the hotel
     */
    public int getHotelID(){
        return hotelID;
    }
    
    /**
     * A simple setter for the hotelID field.
     * @param myID is assigned to the hotelID field
     */
    public void setHotelID(int myID){
        hotelID = myID;
    }
    
    /**
     * A simple getter for the bought field.
     * @return true if the hotel is already bought, false otherwise
     */
    public boolean getBought(){
        return bought;
    }
    
    /**
     * A simple setter for the bought field.
     * @param isBought is assigned as the value of the bought field
     */
    public void setBought(boolean isBought){
        bought = isBought;
    }
}
