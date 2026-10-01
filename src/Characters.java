import java.util.Random;
public class Characters
{
    private final Random ranEnemy = new Random();
    private final int playerLevel;
    protected Enemy enemy;
    protected Player player;
    private final Double enemyRatio;
    private final GameMode gameMode;
    private final String enemyName;

    public Characters(GameMode currentGameMode)
    {
        gameMode = currentGameMode;
        enemyName=gameMode.getSelectedEnemy();
        enemyRatio=gameMode.getSelectedRatio();
        player = Main.player;
        playerLevel = player.getLevel();
    }

    public String getEnemyName()
    {
        return enemyName;
    }

    public Double getEnemyRatio()
    {
        return enemyRatio;
    }

    public int getPlayerLevel()
    {
        return playerLevel;
    }

    public Enemy getEnemy()
    {
        return enemy;
    }

    public Player getPlayer()
    {
        return player;
    }

    private GameMode getGameMode()
    {
        return gameMode;
    }
}
