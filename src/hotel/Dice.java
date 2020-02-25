package hotel;
import java.util.Random;

/**
 *
 * @author stavr
 */
public class Dice {
    /**
     * A method for simulating a dice roll.
     * @return a random number between 1 and 6 (included)
     */
    public int diceRoll(){
        Random ran = new Random();
        return (ran.nextInt(6) + 1);
    }
}
