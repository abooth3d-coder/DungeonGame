import java.util.*;

public class Weapons
{
    private String selectedWeapon;
    private int level;
    private double ratio;
    private int weaponPower;
    private final Random rand = new Random();
    private final ArrayList<String> weaponNames = new ArrayList<>();
    private final ArrayList<String> weaponTypes = new ArrayList<>();

    public Weapons()
    {
        populateNames();
    }

    //Getter Methods
    // ------------------------------------------


    public String getSelectedName()
    {
        return selectedWeapon;
    }


    public int getLevel()
    {
        return level;
    }

    /**
     * @return ration       - This is a double which represents the base ratio of weapon
     */
    public double getRatio()
    {
        return ratio;
    }


    //Utility Methods
    //------------------------------------------------------------------------------------------------------------------

    private void populateNames()
    {
        String weaponNameCreated = String.join(",", "Quicksilver,Moon,Crucifix,Reaper,Serenity,Thunder,Malice,Starfall,Deathsong,Solarflare,Spellkeeper,Starlight");
        weaponNames.addAll(Arrays.asList(weaponNameCreated.split(",")));
        String weaponTypesCreated = String.join(",", "Long Sword,Short Sword,Dagger,Magic Staff,Bow,Katana,Knuckle Buster,Dual Sword,Axe");
        weaponTypes.addAll(Arrays.asList(weaponTypesCreated.split(",")));
    }

    private void generateRatio(String weaponType)
    {
        switch (weaponType)
        {
            case "Long Sword"-> ratio = 1.5;
            case "Short Sword"-> ratio = 1.3;
            case "Dagger", "Knuckle Buster" -> ratio = 1.1;
            case "Magic Staff"-> ratio = 2.0;
            case "Bow"-> ratio = 1.2;
            case "Dual Sword"-> ratio = 1.8;
            case "Axe"-> ratio = 1.7;
            case "Katana"-> ratio = 1.6;
        }
    }

    public void generateWeapon()
    {
        String selectedType = weaponTypes.get(rand.nextInt(weaponTypes.size() - 1));
        String selectedName = weaponNames.get(rand.nextInt(weaponNames.size() - 1)) + " " + selectedType;
        generateRatio(selectedType);
        weaponPowerGenerator();
        selectedWeapon = String.join(",", selectedName.trim(),String.valueOf(weaponPower));
    }

    /**
     * Set weapons power by getting the level & ratio of the weapon, multiplying them together and then using
     * Math.round() to round them  to nearest integer and finally casting as an int type.
     */
    public  void weaponPowerGenerator()
    {
        weaponPower = (int)Math.round(level*(getRatio()*10));
    }


    public void levelGenerator(Object Character)
    {
        if (Character instanceof Enemy enemy)
        {
            int enemyLevel = enemy.getLevel();
            int minWeaponLevel = Math.max(1, enemyLevel - 2); // Lower bound
            int maxWeaponLevel = enemyLevel + 2; // Upper bound
            level = rand.nextInt(maxWeaponLevel - minWeaponLevel + 1) + minWeaponLevel;
        }
        else if (Character instanceof Player player)
        {
            int playerLevel = player.getLevel();
            int minWeaponLevel = Math.max(1, playerLevel - 2); // Lower bound
            int maxWeaponLevel = playerLevel + 2; // Upper bound
            level = rand.nextInt(maxWeaponLevel - minWeaponLevel + 1) + minWeaponLevel;
        }
    }
}
