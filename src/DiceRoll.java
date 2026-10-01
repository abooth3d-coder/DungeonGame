import java.util.Random;

public class DiceRoll
{
    Random roll = new Random();
    private int diceResult;
    private final static int MAX_DICE = 6;

    public DiceRoll()
    {
        diceResult = 0;
        setDiceResult(newDiceRoll());
    }

    private int newDiceRoll()
    {
        return roll.nextInt(getMaxDice())+1;
    }

    public int getDiceResult()
    {
        return diceResult;
    }

    private void setDiceResult(int newDiceResult)
    {
        diceResult = newDiceResult;
    }

    private int getMaxDice()
    {
        return MAX_DICE;
    }
}
