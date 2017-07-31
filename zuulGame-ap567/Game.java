import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling, David J. Barnes and Olaf Chitil
 * @version 28/2/2014
 * * @editor Akash Payne
 * * @edit 10/3/2014
 * 
 * @edit notes:
 * *added goal
 * *
 * * *
 * * * 
 * * * * Game should be broken down into sub classes, however, have not 
 * * * * been asked to.
 * 
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room characterRoom;
    // The field that holds the boolean variable of 
    // the winning objective
    private boolean goalReached;
    // object taken
    private boolean taken;
    // object dropped 
    private boolean dropped;
    // inventory checked
    private boolean checked;
    // goal room variable
    private Room goalRoom;
    // field for the player object
    private Player player;
    private HashMap<String, ItemRegister> itemRegister;
    private HashMap<String, CharacterRegister> characters;

    /**
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        characters = new HashMap<String, CharacterRegister>();
        itemRegister = new HashMap<String, ItemRegister>();
        parser = new Parser();
        player = new Player();
        createRooms();  
        createCharacters();
        createItems();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room gate, graveyard, church, crypt, entrance, hall, kitchen, buttery, greathall, staircase,
            dungeon, topstaircase, throne, solar, wardrobe, privy;
            
        // create the rooms
        gate = new Room("outside the old gate of the castle");
        graveyard = new Room("on a wind-swept gaveyard");
        church = new Room("in a small ancient church with medieval windows");
        crypt = new Room("in the crypt of the church");
        entrance = new Room("at the big wooden entrance of the castle");
        hall = new Room("in the dark entrance hall of the castle");
        kitchen = new Room("in the kitchen with a huge table and a big stove");
        buttery = new Room("in the buttery of the castle");
        greathall = new Room("in the great hall of the castle with its magnificient huge windows");
        staircase = new Room("at the staircase");
        dungeon = new Room("in the dark dungeon of the castle");
        topstaircase = new Room("at the top of the staircase");
        throne = new Room("in the throne room with golden walls");
        solar = new Room("in the solar of the castle");
        wardrobe = new Room("in the wardroble of the Lord of the castle");
        privy = new Room("in the privy");
        
        // initialise room exits
        
        gate.setExit("north", graveyard);
        
        graveyard.setExit("south", gate);
        graveyard.setExit("east", church);
        graveyard.setExit("north", entrance);
        
        church.setExit("west", graveyard);
        church.setExit("south", crypt);
        
        crypt.setExit("north", church);
        
        entrance.setExit("south", graveyard);
        entrance.setExit("north", hall);
        
        hall.setExit("south", graveyard);
        hall.setExit("west", kitchen);
        hall.setExit("north", greathall);
        hall.setExit("east", staircase);
        
        kitchen.setExit("east", hall);
        kitchen.setExit("south", buttery);
        
        buttery.setExit("north", kitchen);
        
        greathall.setExit("south", hall);
        
        staircase.setExit("west", hall);
        staircase.setExit("down", dungeon);
        staircase.setExit("up", topstaircase);
        
        topstaircase.setExit("down", staircase);
        topstaircase.setExit("north", throne);
        topstaircase.setExit("south", solar);
        
        throne.setExit("south", topstaircase);
        
        solar.setExit("north", topstaircase);
        solar.setExit("west", wardrobe);
        solar.setExit("east", privy);
        
        wardrobe.setExit("east", solar);
        
        privy.setExit("west", solar);
        
        // currentRoom = gate;  // start game at gate
        player.setCurrentRoom(gate);
        player.setGoalRoom(throne);

        //goalRoom = throne;// goal finished: game ends at throne
        
        // initialise item locations 
        gate.addItem("spoon","a digging tool");
        graveyard.addItem("fork","a grabbing tool");
        entrance.addItem("knife","a cutting tool");
       
        graveyard.addCharacter("bob","eager", 2);
        graveyard.addCharacter("john","white", 2);
        
    }
    
    /** 
     * Set the start room for this Character. 
     */ 
    public void setCharacterRoom(Room room) 
    { 
        characterRoom = room; 
    } 
    
    
    private void createItems()
    {
        ItemRegister knife, spoon, fork; 
        //
        knife = new ItemRegister("a cutting tool", 5);
        fork = new ItemRegister("a grabbing tool", 3);
        spoon = new ItemRegister("a digging tool", 1);
        
    }
    
    private void createCharacters()
    {
       CharacterRegister bob, john;
         
       bob = new CharacterRegister("eager", 28);
       john = new CharacterRegister("tall", 28);
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        player.setCount();       
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.                
        boolean finished = false;
        while (! finished & ! goalReached) {
            Command command = parser.getCommand();
            if (command == null) {
                System.out.println("I do not understand.");
            } else {
            finished = processCommand(command);
        }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, mysterious adventure game.");
        
        System.out.println("Amount of moves avaliable : "+ player.getLimit());
        
        
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
        
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                {
                    goalReached = player.goRoom(command);
                }
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            //case FIGHT
            //    break;
            
            case TAKE:
                player.takeItem(command);
                break;
            
            case DROP:
                player.dropItem(command);
                break;
                
            case INVENTORY:
                player.showInventory(command);
                break;
                
            case FIGHT:
                goalReached = player.fight(command);
                break;
        }
        return wantToQuit;
    }       
   
    // implementations of user commands
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander around an ancient castle.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }   
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
