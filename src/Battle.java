import java.util.*;

/**
 * A Battle class that Handle all the logic to simulate the battle conditions, the players input during the battle,
 * the damage handling of the battle and what happens after the battle have been lost/won.
 *
 * @author Andrew_Booth
 * @version 31/07/2025
 */
public class Battle
{
    private static final Player PLAYER = Main.player;
    private static final String NARRATOR_NAME = Main.getNarratorName();
    private static final String PLAYER_NAME = Main.getPlayerName();
    private static final Scanner PLAYER_INPUT = Main.getPlayerInput();
    private static final PlayerInventory PLAYER_INVENTORY = Main.playerInventory;
    private final Random rand = new Random();
    private final Enemy enemy;
    private final String enemyName;
    private boolean hasRan = false;
    private boolean isEnemyDefeated = false;
    private int playerDiceResult;
    private int enemyDiceResult;

     Battle(Enemy enemy)
     {
         this.enemy = enemy;
         this.enemyName = Main.dungeonRoom.dungeonElementAppend() + " " + enemy.getEnemyName();
         while (!getHasRan() || !isEnemyDefeated())
         {
             System.out.printf("%n%1s: You have:%1d hit points and %1s have:%1d hit points!",NARRATOR_NAME, PLAYER.getHitPoints(),enemyName,enemy.getHitPoints());
             System.out.printf("%n%1s: What will you do %1s?",NARRATOR_NAME, PLAYER_NAME);
             System.out.printf("%n%1s: Fight,Use Potions or Run%n",NARRATOR_NAME);
             switch (PLAYER_INPUT.nextLine())
             {
                 case "Fight", "fight" -> fight();
                 case "Run","run" -> run();
                 //case "Use Potions" -> useItem(item);
                 default ->  System.out.printf("%n%1s: I did not understand that command!",NARRATOR_NAME);
             }
         }
    }

    //getter methods
    //__________________________________________________________________________________________________________________

    /**
     * Gets hasRan boolean
     * @return hasRan
     */
    public boolean getHasRan()
    {
        return hasRan;
    }

    /**
     * Gets isEnemyDefeated
     * @return isEnemyDefeated
     */
    public boolean isEnemyDefeated()
    {
         return isEnemyDefeated;
    }


    //setter methods
    //__________________________________________________________________________________________________________________

    /**
     * This method sets the hasRan boolean value
     */
    private void setHasRan()
    {
        hasRan = true;
    }

    /**
     * This method sets the boolean isEnemyDefeated to true when the enemy is defeated
     */
    private void setEnemyDefeated()
    {
        isEnemyDefeated = true;
    }


    //Utility methods
    //__________________________________________________________________________________________________________________

    /**
     * A private method helper which simulate 2 dice rolls to represent PLAYER roll and enemy roll then get and assign the
     * dice result to the respected PLAYER/enemy.
     */
    private void diceRoll()
    {
        for(int index = 0; index < 2; index++)
        {
            DiceRoll roll = new DiceRoll();
            if (index == 0) playerDiceResult = roll.getDiceResult();
            else enemyDiceResult = roll.getDiceResult();
        }

       /* DiceRoll diceRoll = new DiceRoll();
        DiceRoll diceRoll2 = new DiceRoll();
         playerDiceResult = diceRoll.getDiceResult();
         enemyDiceResult = diceRoll2.getDiceResult();*/
    }

    /**
     * A private helper method that define the fight sequence between player and enemy; the player turn first then enemy.
     * This method get the player/enemy hit points and if the Hit Points are NOT equal or Below 0 then their respected method
     * is executed. This method have no parameter or Return.
     */
    private void fight()
    {
        if(!(PLAYER.getHitPoints()<=0)) playerEnemyTurn(true);
        if(!(enemy.getHitPoints() <=0)) playerEnemyTurn(false);
    }

    /**
     * A private method that determine weather or not if Players or Enemy's turn. if  Players turn and instructs the player to roll the dice.
     * and execute Battle phase method as player otherwise print invalid message. If enemy, execute Enemy's logic and enters battle phase as Enemy.
     * This method take no parameter or any return,
     */
    private void playerEnemyTurn(Boolean playerTurn)
    {
        //This executes if it is players turn
        if(playerTurn)
        {
            System.out.printf("%n%1s: I will fight!!%n%1s: Your turn!!, Please Roll the dice%n%1s: R = Roll the dice", PLAYER_NAME, NARRATOR_NAME, NARRATOR_NAME);
            String newPlayerInput = PLAYER_INPUT.nextLine();
            boolean isValid = false;
            while (!isValid)
            {
                //Check if the player input is "R" or "r"
                if (newPlayerInput.equalsIgnoreCase("R") || newPlayerInput.equalsIgnoreCase("r"))
                {
                    diceRoll();
                    battlePhase(true);
                    isValid = true;
                }
                //If input is invalid the prints invalid message and instructs player to re-enter input
                else
                {
                    System.out.printf("%n%1s: Invalid Input%n%1s: Please Roll the dice again!%n%1s: R = Roll the dice",NARRATOR_NAME, NARRATOR_NAME, NARRATOR_NAME);
                    newPlayerInput = PLAYER_INPUT.nextLine();
                }
            }
        }
        //This executes if it is enemy's turn.
        else
        {
            System.out.printf("%n%1s: %1s turn!!, Please Roll the dice",NARRATOR_NAME,enemyName);
            diceRoll();
            System.out.printf("%n%1s: %1s, You rolled a %d%n%1s: %1s rolled a %1d", NARRATOR_NAME, PLAYER_NAME, playerDiceResult, NARRATOR_NAME, enemyName, enemyDiceResult);
            battlePhase(false);
        }
    }


    /**
     * This private method handle the actual battle phase logic, this includes battle tree, calculating battle damage, method
     * execution and message based on battle win, lose or tie. It determines which end of the battle base on the players turn boolean.
     * @param playerTurn A Boolean value that represents whether or no it's Players turn
     */
    private void battlePhase(Boolean playerTurn)
    {
        //This executes if it is players turn
        if (playerTurn)
        {
            System.out.printf("%n%1s: %1s, You rolled a %d%n%1s: %1s rolled a %1d", NARRATOR_NAME, PLAYER_NAME, playerDiceResult, NARRATOR_NAME, enemyName, enemyDiceResult);
            int damage = compareAttDef(playerDiceResult * PLAYER.getAttackPoints(), enemyDiceResult * enemy.getDefensePoints());
            if (damage > 0)
            {
                enemy.reduceHitPoints(damage);
                System.out.printf("%n%1s: %1s, You did %1d damage to %1s!", NARRATOR_NAME, PLAYER_NAME, damage, enemyName);
            }
            else if (damage < 0)
            {
                PLAYER.reduceHitPoints(-damage);
                System.out.printf("%n%1s: %1s, You was not strong enough for %1s's defences, you take %1d damage!", NARRATOR_NAME, PLAYER_NAME, enemyName, -damage);
            }
            else tieMessage();
        }
        //This executes if it is enemy's turn.
        else
        {
            int damage = (compareAttDef(enemyDiceResult * enemy.getAttackPoints(), playerDiceResult * PLAYER.getDefensePoints()));
            if (damage > 0)
            {
                PLAYER.reduceHitPoints(damage);
                System.out.printf("%n%1s: %1s, %1s beaten your defences and inflicted %1d to you!", NARRATOR_NAME, PLAYER_NAME, enemyName, damage);
            }
            else if (damage < 0)
            {
                enemy.reduceHitPoints(-damage);
                System.out.printf("%n%1s: %1s, %1s was not strong enough for your defences, and inflicted %1d damage to itself!", NARRATOR_NAME, PLAYER_NAME, enemyName, -damage);
            }
            else tieMessage();
        }
        checkHitPoints();
    }

    /**
     * A method that print a message to player when the battle is tied.
     */
    private void tieMessage()
    {
        System.out.printf("%n%1s: This battle is a tie no Damage have been inflicted to everyone", NARRATOR_NAME);
    }

    /**
     * A method that calculate the difference between the player/enamy attack points to the enemy/player defense points.
     * @param attack an int value that represent the attack points of player/enemy
     * @param defense an int value that represent the defense points of the player/enemy
     * @return damage - an int value that represent the difference between attack and defense.
     */
    private int compareAttDef(int attack, int defense)
    {
        System.out.println("\nattack is "+attack+" and defense is "+defense );
        int damage = attack-defense;
        System.out.println("damage is " +damage);
        return damage;
    }

    /**
     * This method check the hit points of both the player and the enemy.
     *   - If the player's hit points become 0, then a Game over message to printed then quit the game.
     *   - If enemy's hit point become 0, the winning reward method is executed and message is printed informing the player to move on to the next dungeon.
     */
    private void checkHitPoints()
    {
        if(PLAYER.getHitPoints()<=0)
        {
            System.out.printf("%n%1s: %1s Your Hit Points have become 0!!%n%1s: Game Over, You have been defeated!%n%1s: Thank you for playing!",NARRATOR_NAME, PLAYER_NAME,NARRATOR_NAME,NARRATOR_NAME);
            System.exit(0);
        }
        else if(enemy.getHitPoints()<=0)
        {
            winningReward();
        }
        System.out.printf("%n%1s: Lets move to the next Dungeon!",NARRATOR_NAME);
        System.exit(0);
    }

    private void winningReward()
    {
        System.out.printf("%n%1s: %1s You have defeated your opponent!%n%1s:You gained %1d and a chest!",NARRATOR_NAME,PLAYER_NAME,NARRATOR_NAME,PLAYER.getExpReward());
        setEnemyDefeated();
        PLAYER.rewardExp();
        System.out.printf("%n%1s: Lets Open your chest!",NARRATOR_NAME);
        Chest newChest = new Chest();
        System.out.printf("%n%1s: %1s you have obtained an %1s",NARRATOR_NAME, PLAYER_NAME, newChest.getSelectedItem());
        String action;
        boolean isValidResponse =false;
        if (newChest.getSelectedItem().equals("Weapon") && PLAYER.getCurrentWeapon().equals("No weapon"))
        {
            String[] newWeapon = newChest.getNewWeapons().getSelectedName().split(",");
            String weaponLevel = String.valueOf(newChest.getNewWeapons().getLevel());
            System.out.printf("%n%1s: %1s, You have no weapon equipped!%n%1s:Would you like to equip level %1s %1s? Y/N? %n",NARRATOR_NAME, PLAYER_NAME,NARRATOR_NAME,weaponLevel,newWeapon[0]);
            action = PLAYER_INPUT.nextLine();
            while (!isValidResponse)
            {
                switch (action)
                {
                    case "Y", "y" ->
                    {
                        PLAYER.setCurrentWeapon(newWeapon[0], Integer.parseInt(newWeapon[1]), Integer.parseInt(weaponLevel));
                        System.out.printf("%n%1s: %1s, level %1s %1s have been equipped!",NARRATOR_NAME,PLAYER_NAME,PLAYER.getWeaponLevel(),PLAYER.getCurrentWeapon());
                        isValidResponse = true;
                    }
                    case "N", "n " ->
                    {
                        addToInventory(newWeapon[0]);
                        isValidResponse = true;
                    }
                    default -> System.out.printf("%n%1s: Invalid Input", NARRATOR_NAME);
                }
            }
        }
        else addToInventory(newChest.getSelectedItem());
    }

    private void addToInventory(String itemName)
    {
        boolean isValidResponse=false;
        while(!isValidResponse)
        {
            System.out.printf("%n%1s: Would you like to add it too your Inventory? Y/N?", NARRATOR_NAME);
            String action = PLAYER_INPUT.nextLine();
            switch (action) {
                case "Y", "y" -> {
                    Main.playerInventory.addToInventory(itemName);
                    isValidResponse = true;
                }
                case "N", "n" -> {
                    System.out.printf("%n%1s: %1s, You have discarded %1s", NARRATOR_NAME, PLAYER_NAME, itemName);
                    isValidResponse = true;
                }
                default -> System.out.printf("%n%1s: Invalid Input", NARRATOR_NAME);
            }
        }
    }

    private void run()
    {
        int randomBool = rand.nextInt(2);
        if (randomBool == 0)
        {
            System.out.println("You escaped undamaged!");
            setHasRan();
        }
        else
        {
            if (PLAYER.getHitPoints()<=2)
            {
                System.out.printf("%n%1s: %1s, %1s took all your hit points as you was escaping", NARRATOR_NAME, PLAYER_NAME,enemyName);
                PLAYER.setHitPoints(0);
                checkHitPoints();
            }
            int randDamagePercent = rand.nextInt(40) +1;
            int playsDamage = Math.round((float)(PLAYER.getHitPoints() * randDamagePercent) /100);
            PLAYER.setHitPoints(PLAYER.getHitPoints()-playsDamage);
            System.out.println(enemy.getEnemyName() + " attacked you before you escaped and inflicted -" + playsDamage + " damage");
            System.out.println("Player HP is: " + PLAYER.getHitPoints());
            setHasRan();
        }
    }

    private void useItem(Potions itemInstance)
    {
        int itemIndex =1;
        System.out.println("Items in inventory: %n");
        for ( Map.Entry<String, Integer> entry :PLAYER_INVENTORY.getInventory().entrySet())
        {
            System.out.println(itemIndex+": "+ "Potions: " + entry.getKey() + ", Quantity: " + entry.getValue());
            itemIndex++;
        }
        System.out.println("Please enter the item number to use that item!");
        String itemNumber = PLAYER_INPUT.next();
        for(String item : PLAYER_INVENTORY.getItemNames())
        {
            if(item.indexOf(item) == Integer.parseInt(itemNumber))
            {
                itemInstance.usePotion(item);
                PLAYER_INVENTORY.getInventory().remove(item);
                break;
            }
        }
    }
}
