import java.util.Random;
public class Enemy extends Characters
{
    private static final Double SELECTED_RATIO = Main.gameMode.getSelectedRatio();
    private static final int POISONED = 50;
    private static final Player PLAYER = Main.player;
    private static final int HIT_POINT_MULTIPLIER = 100;
    private final String enemyName;
    private int hitPoints;
    private final int level;
    private final Random ranLevel = new Random();
    private String currentWeapon = "No Weapon";
    private String currentShield = "No Shield";
    private int weaponLevel;
    private int shieldLevel;
    private int attackPoints;
    private int defensePoints;
    private boolean isPoisoned = false;
    private int turnsPoisoned = 0;
    private boolean isSleeping = false;
    private int sleepingTurns = 0;


    public Enemy(GameMode gameMode)
    {
        super(gameMode);
        enemyName = super.getEnemyName();
        System.out.println("Enemy Ratio is: " + SELECTED_RATIO);
        level = levelGen();
        setUpHitPoints();
        setDefensePoints(level);
        setAttackPoints(level);
    }

    //getter methods
    //__________________________________________________________________________________________________________________

    public int getSleepingTurns()
    {
        return sleepingTurns;
    }

    public int getWeaponLevel()
    {
        return weaponLevel;
    }

    public boolean getIsSleep()
    {
        return isSleeping;
    }

    public String getCurrentWeapon()
    {
        return currentWeapon;
    }

    @Override
    public Double getEnemyRatio()
    {
        return SELECTED_RATIO;
    }

    public String getEnemyName()
    {
        return enemyName;
    }

    public int getLevel()
    {
        return level;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public String getCurrentShield()
    {
        return currentShield;
    }

    public int getShieldLevel()
    {
        return shieldLevel;
    }

    public int getAttackPoints()
    {
        return attackPoints;
    }

    public int getDefensePoints()
    {
        return defensePoints;
    }

    public boolean getIsPoisoned()
    {
        return isPoisoned;
    }

    public int getTurnsPoisoned()
    {
        return turnsPoisoned;
    }

    public int getPoisoned()
    {
        return POISONED;
    }


    //setter methods
    //__________________________________________________________________________________________________________________


    public void setSleeping(boolean sleeping)
    {
        isSleeping = sleeping;
    }

    public void setSleepingTurns(int turns)
    {
        this.sleepingTurns = turns;
    }

    public void setIsPoisoned(boolean poisoned)
    {
        isPoisoned = poisoned;
    }

    public void setTurnsPoisoned(int turns)
    {
        turnsPoisoned = turns;
    }

    public void setWeaponLevel(int level)
    {
        weaponLevel = level;
    }

    public void setShieldLevel(int level)
    {
        shieldLevel = level;
    }

    public void setAttackPoints(int level)
    {
        if (getCurrentWeapon().equalsIgnoreCase("No Weapon")) attackPoints  = (int)Math.round(SELECTED_RATIO *10)*level;
        else attackPoints = weaponLevel*((int)Math.round(SELECTED_RATIO *10)*level);
    }

    public void setDefensePoints(int level)
    {
        if (getCurrentShield().equalsIgnoreCase("No Shield")) defensePoints  = (int)Math.round(SELECTED_RATIO *10)*level;
        else defensePoints = level*((int)Math.round(SELECTED_RATIO *10)*level);
    }

    public void setCurrentWeapon(String newWeapon,int level)
    {
        currentWeapon = newWeapon;
        setWeaponLevel(level);
        setAttackPoints(getLevel());
    }

    public void setCurrentShield(String newShield,int level)
    {
        currentShield = newShield;
        setShieldLevel(level);
    }

    private void setUpHitPoints()
    {
        switch (Main.getDifficulty())
        {
            case "easy" -> hitPoints = (int) Math.round((SELECTED_RATIO * HIT_POINT_MULTIPLIER) * level) / 2;
            case "medium"-> hitPoints = (int) Math.round(SELECTED_RATIO * HIT_POINT_MULTIPLIER) * level;
            case"hard" -> hitPoints = (int) Math.round((SELECTED_RATIO * HIT_POINT_MULTIPLIER) * level) * 2;
        }
    }



    //utility methods
    //__________________________________________________________________________________________________________________

    private int levelGen()
    {
        int playerLevel = super.getPlayerLevel();
        int minEnemyLevel = Math.max(1, playerLevel - 2); // Lower bound
        int maxEnemyLevel = playerLevel + 1; // Upper bound
        return ranLevel.nextInt(maxEnemyLevel - minEnemyLevel + 1) + minEnemyLevel;
    }

    public void reduceHitPoints(int damage)
    {
        hitPoints -= damage;
    }

    public void startPoisoned(int turns)
    {
        setIsPoisoned(true);
        turnsPoisoned = turns;
    }

    public void poisonDamage()
    {
        if(getIsPoisoned() && getTurnsPoisoned() > 0)
        {
            reduceHitPoints(getPoisoned());
            setTurnsPoisoned(getTurnsPoisoned()-1);
        }
        else setIsPoisoned(false);
    }

    public void startSleep(int turns)
    {
        setSleeping(true);
        setTurnsPoisoned(turns);
    }
}
