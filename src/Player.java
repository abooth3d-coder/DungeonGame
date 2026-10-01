/**
 * A Class that handle all Player's logic which includes the following:-
 *<br>-Setting up and handling Player's Hit Points
 *<br>-Setting up and handling Player's Exp logic.
 *<br>-Setting up and handling Player's Base Ratio
 *<br>-Setting up and handling Player's Weapon and Shield
 * @Author Andrew Booth
 * @version 07/08/2025
 */
public class Player
{
    private static final int RATIO_MULTIPLIER = 10;
    private static final int HIT_POINT_MULTIPLIER = 100;
    private int level  = 1;
    private int hitPoints =10;
    private double ratio =0.0;
    private double expRatio;
    private int exp = 0;
    private int expReward;
    private int expMax;
    private String currentWeapon = "No weapon";
    private int weaponLevel;
    private String currentShield = "No shield";
    private int shieldLevel;
    private int attackPoints;
    private int defensePoints;

    /**
     * This initializer method is used to set up the Players fields when the Player class is created. The following
     * operation executions:-
     * <p>
     * <br>-settingPlayerRatio - This method sets up the Player's Base Ratio and expRatio Based on Difficulty mode
     * <br>-hitPoints          - Sets up the Player's hit points Depending on Player's Ratio and level.
     * <br>-setUpAttackDefence - This method sets up the Player's Attack and Defense power Depending on Player's Ratio and level.
     * <br>-expMax             - Sets up the Player's expMax used for leveling up the Player using expRatio.
     * <br>-nextExpReward      - This method sets up the exp Reward system to the player  using both Player's and Enemy's ratio.
     */
    public Player()
    {
        settingPlayerRatio();
        hitPoints = ((int)Math.round(ratio*HIT_POINT_MULTIPLIER)*level);
        setUpAttackDefence();
        expMax = (int)Math.round(expRatio* RATIO_MULTIPLIER);
        nextExpReward();
    }

    // Getter Methods
    // __________________________________________________________________________________________________________________

    /**
     * Get the Exp rewarded to the player
     * @return expReward - An int that represent the amount of Exp Rewarded to the Player.
     */
    public int getExpReward()
    {
        return expReward;
    }

    /**
     * Get the Exp Max requirements for the next level.
     * @return expMax - An int value that represent the of Exp the Player requires to advance to the next level
     */
    public int getExpMax()
    {
        return expMax;
    }

    /**
     * Gets the Players base Ratio.
     * @return ratio - a double value that represents the Player base ratio determined by Difficulty mode.
     */
    public double getRatio()
    {
        return ratio;
    }

    /**
     * Get the name of the Player's Weapon
     * @return currentWeapon - A String value that represent the name of the current weapon equipped to Player.
     */
    public String getCurrentWeapon()
    {
        return currentWeapon;
    }

    /**
     * Get the level of the Player.
     * @return level - An int value that represent the level of the player
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Get the Players Hit Points.
     * @return hitPoints - An int value that represent the Player's Hit Points.
     */
    public int getHitPoints()
    {
        return hitPoints;
    }

    /**
     * Gets the Players total attack power to be used in offensive Battle phase.
     * @return - attackPoints - An int value that presents the total attack power of the player
     */
    public int getAttackPoints()
    {
        return attackPoints;
    }

    /**
     * Gets the Players total defense power in defensive Battle phase.
     * @return defensePoints - An int value that represents the total defense power of the player
     */
    public int getDefensePoints()
    {
        return defensePoints;
    }

    /**
     * Gets the current weapon's level.
     * @return weaponLevel - An int value the represents the level of current weapon equipped to Player
     */
    public int getWeaponLevel()
    {
        return weaponLevel;
    }


    //Setter methods
    //------------------------------------------------------------------------------------------------------------------


    /**
     * Set the Players Hit Points by a set amount in the parameter
     * @param hitPoints - An int value that represents the Player's Hit Points
     */
    public void setHitPoints(int hitPoints)
    {
        this.hitPoints = hitPoints;
    }

    /**
     * Sets up both the Player's attack power and defense power. This is completed  by multiplying the Player's Base Ratio
     * with the RATIO_MULTIPLIER of 10 then multiply the result by the Player's level and final rounded using Math.round
     * which is cast to an int type.
     */
    private void setUpAttackDefence()
    {
        attackPoints=(int)Math.round(ratio* RATIO_MULTIPLIER)*level;
        defensePoints=(int)Math.round(ratio*RATIO_MULTIPLIER)*level;
    }

    private void settingPlayerRatio()
    {
        switch (Main.getDifficulty())
        {
            case"easy" ->
            {
                ratio = 2.0;
                expRatio = 1.0;
            }
            case "medium" ->
            {
                ratio = 1.5;
                expRatio = 1.5;
            }
            case "hard" ->
            {
                ratio = 1.0;
                expRatio = 2.0;
            }
        }
    }

    public void setCurrentWeapon(String newWeapon,int power ,int level)
    {
        currentWeapon = newWeapon;
        weaponLevel = level;
        attackPoints = power+((int)Math.round(ratio*RATIO_MULTIPLIER)*level);
    }

    public void setCurrentShield(String newShield,int level)
    {
        currentShield = newShield;
        shieldLevel = level;
        defensePoints = shieldLevel+((int)Math.round(ratio*RATIO_MULTIPLIER)*level);
    }


    //Utilities Methods
    //------------------------------------------------------------------------------------------------------------------

    public void reduceHitPoints(int damage)
    {
        hitPoints -= damage;
    }

    public void restoreHitPoints(int restore)
    {
        hitPoints += restore;
    }

    private void nextExpReward()
    {
        expReward=((int)Math.round((expRatio*RATIO_MULTIPLIER)*(int)Math.round(Main.gameMode.getNewEnemy().getEnemyRatio()*RATIO_MULTIPLIER))/RATIO_MULTIPLIER);
    }

    public void rewardExp()
    {
        increaseExp(expReward);
        nextExpReward();
    }

    public void increaseExp(int increase)
    {
        exp += increase;
        if (exp >= expMax)
        {
            level++;
            exp -= expMax;
            expMax = (int)Math.round((expMax +((expMax*expRatio)/2)));
            hitPoints = hitPoints + HIT_POINT_MULTIPLIER;
        }
    }

    public void addAttackPoints(int add)
    {
        attackPoints += add;
    }

    public void addDefensePoints(int add)
    {
        defensePoints += add;
    }
}