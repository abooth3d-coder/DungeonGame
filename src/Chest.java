import java.util.*;
public class Chest
{
    private final Random rand = new Random();
    private final ArrayList<String> items;
    private String selectedItem;
    private static final Player PLAYER =Main.player;
    private static final String NARRATOR_NAME = Main.getNarratorName();
    private Enemy enemy =Main.gameMode.getNewEnemy();
    Weapons newWeapon;

    public Chest()
    {
        items = new ArrayList<>();
        populate();
        selectItem();
        itemHub();

    }

    //getter methods
    //__________________________________________________________________________________________________________________

    private int getItemIndex(String item)
    {
        return items.indexOf(item);
    }

    public String getSelectedItem()
    {
        return selectedItem;
    }
    public Weapons getNewWeapons()
    {
        return newWeapon;
    }

    //Utility Methods
    //__________________________________________________________________________________________________________________

    private void populate()
    {
        items.add("Potions");
        items.add("Weapon");
        items.add("Shield");
        items.add("Explode");
        items.add("Restore");
        items.add("Enemy");
        items.add("Level Up");
    }

    private void selectItem()
    {
        //int index = rand.nextInt(items.size());
        selectedItem = items.get(1);
    }

    private void itemHub()
    {
        switch (selectedItem)
        {
            case "Potions" ->
            {
                new Potions();
                System.out.println("You just gained a Potions!");
            }
            case "Weapon"->
            {
               newWeapon = new Weapons();
               newWeapon.levelGenerator(PLAYER);
               newWeapon.generateWeapon();
               String[] weaponInfo = newWeapon.getSelectedName().split(",");
               System.out.printf("%n%1s:You just gained a Level %1d %1s with attack power of %1s!",NARRATOR_NAME,newWeapon.getLevel(), weaponInfo[0], weaponInfo[1]);
            }
            case "Shield"->
            {
                new Shield();
                System.out.println("You just gained a Shield!");
            }
            case "Explode"-> PLAYER.setHitPoints(Math.round((float)(PLAYER.getHitPoints()-(PLAYER.getHitPoints() * 20) /100)));
            case "Restore"-> PLAYER.setHitPoints(PLAYER.getLevel()* (int) PLAYER.getRatio());
            //case "Enemy": new Enemy(); break;
            case "Level Up"->
            {
                PLAYER.increaseExp(PLAYER.getExpMax());
                System.out.println("Player Leveled Up!! %n Player now Level: "+ PLAYER.getLevel());
            }
        }
    }
}
