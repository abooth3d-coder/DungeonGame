public class DungeonRoom
{
    private final String dungeonName;
    private static final String NARRATOR_NAME = Main.getNarratorName();

    public DungeonRoom(String dungeonName)
    {
        this.dungeonName = dungeonName;
    }

    public void printDungeonDescription()
    {
        System.out.println(NARRATOR_NAME+": We are now in the " + dungeonName + " dungeon!");
    }

    public String dungeonElementAppend()
    {
        String dungeonElement = "";
        switch (dungeonName)
        {
            case "Forest" -> dungeonElement = "Earth";
            case "Lava" -> dungeonElement = "Fire";
            case "Meadow" -> dungeonElement = "Normal";
            case "Swamp" -> dungeonElement = "Mouldy";
            case "Mountain" -> dungeonElement = "Winged";
            case "Glacier" -> dungeonElement = "Ice";
            case "Cave", "Graveyard" -> dungeonElement = "Dark";
            case "Ocean" -> dungeonElement = "Water";
        }
        return dungeonElement;
    }
}
