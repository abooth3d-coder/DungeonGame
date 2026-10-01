//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main
{
    static GameMode gameMode;
    static Player player;
    static PlayerInventory playerInventory;
    static DungeonRoom dungeonRoom;
    static Characters characters;
    private static String playerName;
    private static String difficulty;
    private static final String NARRATOR_NAME = "Merlin";
    private static final Scanner PLAYER_INPUT = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.printf("Hello there!%nMy name is %1s!%n%1s: I the greatest wizard of all time!%n%1s: Welcome you to Dungeon Survival!%n",NARRATOR_NAME,NARRATOR_NAME,NARRATOR_NAME);
        initialisePlayerFields();
        initialiseClasses();
        switch (difficulty)
        {
            case "easy" -> System.out.printf("%1s: Ah! I see taking it easy then, the casual route it is! %n",NARRATOR_NAME);
            case "medium" ->System.out.printf("%1s: Ah! I see you want nothing special then, the normal route it is! %n",NARRATOR_NAME);
            case "hard" -> System.out.printf("%1s: Ah! I see you want to see how skilled you are, the challenging route it is! %n",NARRATOR_NAME);
        }
        boolean isValidResponse = false;
        while (!isValidResponse)
        {
            System.out.printf("%1s: Thank You %1s!%n%1s: Please type 'W' to enter the first dungeon! or 'Q' to quit!%n",NARRATOR_NAME,playerName,NARRATOR_NAME);
            String action = PLAYER_INPUT.nextLine();
            switch (action)
            {
                case "W", "w" ->
                {
                    dungeonRoom = gameMode.createDungeon();
                    dungeonRoom.printDungeonDescription();
                    isValidResponse = true;
                }
                case "Q", "q" -> System.exit(0);
                default -> System.out.printf("%1s: I don't Understand that, Please try again%n",NARRATOR_NAME);
            }
        }
        gameMode.spawnEnemy();
        System.out.printf("%1s: Oh no a Level %1d %1s %1s have appeared!%n",NARRATOR_NAME,gameMode.getNewEnemy().getLevel(),dungeonRoom.dungeonElementAppend(),gameMode.getNewEnemy().getEnemyName());
        Battle newBattle= new Battle(gameMode.getNewEnemy());
    }

    //Getter Methods
    //__________________________________________________________________________________________________________________

    /**
     * Returns the Scanner functionality for player's input
     * @return PLAYER_INPUT
     */
    public static Scanner getPlayerInput()
    {
        return PLAYER_INPUT;
    }

    public static String getNarratorName()
    {
        return NARRATOR_NAME;
    }

    public static String getPlayerName()
    {
        return playerName;
    }

    public static String getDifficulty()
    {
        return difficulty;
    }


    //Utility Methods
    //__________________________________________________________________________________________________________________

    private static void initialiseClasses()
    {
        gameMode = new GameMode();
        player = new Player();
        playerInventory = new PlayerInventory();
        characters = new Characters(gameMode);
    }

    /**
     * Initialises player's name from players input and Difficulty including narrator dialog.
     */
    private static void initialisePlayerFields()
    {
        System.out.printf("%1s: Please enter your name?%n", NARRATOR_NAME);
        playerName = PLAYER_INPUT.nextLine().trim();
        System.out.printf("%n%1s: My name is %1s, very nice to meet you!%n", playerName, playerName);
        System.out.printf("%1s: That's great %1s!%n",NARRATOR_NAME,playerName);
        settingUpDifficulty();
    }

    private static void settingUpDifficulty()
    {
        boolean correctFormat = false;
        Scanner scanner = new Scanner(System.in);
        while (!correctFormat)
        {
            System.out.printf("%s: Now enter difficulty: Easy, Medium, Hard: %n",NARRATOR_NAME);
            difficulty = scanner.nextLine().trim().toLowerCase();
            switch (difficulty)
            {
                case "easy", "medium", "hard" -> correctFormat = true;
                default -> System.out.printf("%1s: Invalid difficulty",NARRATOR_NAME);
            }
        }
    }
}

