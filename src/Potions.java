import java.util.*;

public class Potions
{
    private String selectedPotion;
    private static final int SMALL_SIZE = 50;
    private static final int MEDIUM_SIZE = 100;
    private static final int LARGE_SIZE = 200;
    private final ArrayList<String> potions = new ArrayList<>();;
    private final Random rand = new Random();;
    private static final Player CURRENT_PLAYER = Main.player;
    private static final Enemy CURRENT_ENEMY = Main.gameMode.getNewEnemy();

    public Potions()
    {
        populatePotions();
        generatePotion();
    }

    //getter methods
    //__________________________________________________________________________________________________________________

    public String getSelectedPotion()
    {
        return selectedPotion;
    }



    //Utility methods
    //__________________________________________________________________________________________________________________

    private void populatePotions()
    {
        String item = String.join(",","Health Potions","Attack Potions","Defence Potions","Poison Potions",
                "Sleep Potions","Shrink Potions");
        potions.addAll(Arrays.asList(item.split(",")));
    }

    private void generatePotion()
    {
        selectedPotion = String.join(",", potions.get(rand.nextInt(potions.size())), generateSize());
    }

    private String generateSize()
    {
        String size;
        int generatedSize = rand.nextInt(3)+1;
        if (generatedSize == 1) size = "S";
        else if (generatedSize == 2) size = "M";
        else size = "L";
        return size;
    }

    //use potions methods
    //__________________________________________________________________________________________________________________

    public void usePotion(String potion)
    {
        String itemName = potion.split(",")[0];
        String itemSize = potion.split(",")[1];
        switch(itemName)
        {
            case "Health Potions" -> healthPotion(itemSize);
            case "Attack Potions" -> attackPotion(itemSize);
            case "Defence Potions" -> defensePotion(itemSize);
            case "Poison Potions" -> poisonPotion(itemSize);
            case "Sleep Potions" -> sleepPotion(itemSize);
            case "Shrink Potions" -> shrinkPotion(itemSize);
        }
    }

    private void healthPotion(String size)
    {
        switch (size)
        {
            case "S" -> CURRENT_PLAYER.restoreHitPoints(SMALL_SIZE);
            case "M" -> CURRENT_PLAYER.restoreHitPoints(MEDIUM_SIZE);
            case "L" -> CURRENT_PLAYER.restoreHitPoints(LARGE_SIZE);
        }
    }

    private void attackPotion(String size)
    {
        switch (size)
        {
            case "S"-> CURRENT_PLAYER.addAttackPoints(SMALL_SIZE);
            case "M"-> CURRENT_PLAYER.addAttackPoints(MEDIUM_SIZE);
            case "L"-> CURRENT_PLAYER.addAttackPoints(LARGE_SIZE);
        }
    }

    private void defensePotion(String size)
    {
        switch (size) {
            case "S"-> CURRENT_PLAYER.addDefensePoints(SMALL_SIZE);
            case "M"-> CURRENT_PLAYER.addDefensePoints(MEDIUM_SIZE);
            case "L"-> CURRENT_PLAYER.addDefensePoints(LARGE_SIZE);
        }
    }

    private void poisonPotion(String size)
    {
        switch (size)
        {
            case "S" -> CURRENT_ENEMY.startPoisoned(2);
            case "M" -> CURRENT_ENEMY.startPoisoned(3);
            case "L" -> CURRENT_ENEMY.startPoisoned(4);
        }
    }

    private void sleepPotion(String size) {
        switch (size) {
            case "S" -> CURRENT_ENEMY.startSleep(2);
            case "M" -> CURRENT_ENEMY.startSleep(3);
            case "L" -> CURRENT_ENEMY.startSleep(4);
        }
    }

    private void shrinkPotion(String size)
    {
        switch (size)
        {
            case "S":
            {
                applyShrinkPotion(SMALL_SIZE);
                break;
            }
            case "M":
            {
                applyShrinkPotion(MEDIUM_SIZE);
                break;
            }
            case "L":
            {
                applyShrinkPotion(LARGE_SIZE);
                break;
            }
        }
    }

    private void applyShrinkPotion(int size)
    {
        CURRENT_ENEMY.setAttackPoints(CURRENT_ENEMY.getAttackPoints()-size);
        CURRENT_ENEMY.setDefensePoints(CURRENT_ENEMY.getDefensePoints()-size);
        CURRENT_ENEMY.reduceHitPoints(size);
    }
}
