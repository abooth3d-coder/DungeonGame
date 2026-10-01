import java.util.*;

public class GameMode
{
    private Enemy newEnemy;
    private final Double[] enemyRatios;
    private final Random ran = new Random();
    private String selectedEnemy;
    private Double selectedRatio;
    private final String[] enemyNames;
    private String[] dungeonNames;

    public GameMode()
    {
        enemyNames = populateEnemyNames();
        enemyRatios = populateEnemyRatios();
        populateDungeonNames();
    }

    //Getter Methods
    //__________________________________________________________________________________________________________________

    public Enemy getNewEnemy()
    {
        return newEnemy;
    }

    public Double getSelectedRatio()
    {
        return selectedRatio;
    }

    public String[] getEnemyNames()
    {
        return enemyNames;
    }

    public String getSelectedEnemy()
    {
        return selectedEnemy;
    }

    public Double[] getEnemyRatios()
    {
        return enemyRatios;
    }

    //Utility Methods
    //__________________________________________________________________________________________________________________

    private void populateDungeonNames()
    {
        String createDungeonNames = String.join(",","Forest","Lava","Meadow","Swamp","Mountain","Glacier","Cave","Graveyard","Ocean");
        dungeonNames = createDungeonNames.split(",");
    }

    public DungeonRoom createDungeon()
    {
        String selectedDungeonName = dungeonNames[ran.nextInt(dungeonNames.length)];
        return new DungeonRoom(selectedDungeonName);
    }

    public void spawnEnemy()
    {
        int randomEnemy = ran.nextInt(getEnemyNames().length);
        selectedEnemy = enemyNames[randomEnemy];
        selectedRatio = enemyRatios[randomEnemy];
        newEnemy = new Enemy(this);
    }

    public String[]  populateEnemyNames()
    {
        String createEnemyNames = String.join(",","Mimic","Skeleton","Dragon","Wizard","Inferius","Bat","Vampire","Wolf","Werewolf");
        return createEnemyNames.split(",");
    }

    public Double[] populateEnemyRatios()
    {
        String createEnemyRatio = String.join(",","1.0","1.3","1.9","1.5","1.4","1.1","1.5","1.2","1.4");
        String[]tempEnemyRatio = createEnemyRatio.split(",");
        Double[]convertedRatio= new Double[getEnemyNames().length];
        int index =0;
        for (String ratio:tempEnemyRatio)
        {
            convertedRatio[index]= (Double.parseDouble(tempEnemyRatio[ratio.length()]));
            index++;
        }
        return convertedRatio;
    }
}
