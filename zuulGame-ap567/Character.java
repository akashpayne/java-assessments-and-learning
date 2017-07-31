import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Character that implements characters - a character that can 
 * move around rooms.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * 
 * @author  Akash Payne
 * @version 10/02/2014
 */
public class Character
{
    private String characterDescription;
    public int characterStrength = 28;
    private Room currentRoom;
    private HashMap<String, Character> character; // stores characters

    /**
     * Constructor for objects of class Character
     * Create an Character described by "description"
     */
    public Character(String description, int characterStrength)
    {
        this.characterDescription = characterDescription;
        this.characterStrength = characterStrength; 
        //this.currentRoom = currentRoom;
        this.character = new HashMap<String, Character>();
    }

    /**
     * Return a long description of the item that includes the description 
     */
    public String toString() {
        return "Characters: "+character;
    } 
    
        /**
     * lists all items in the games inventory.
     */
    public String fromString(String description)
    {
        String returnString = "Characters:";
        Set<String> keys = character.keySet();
        for(String character : keys) {
            returnString += " " + character;
        }
        return returnString;
    } 
    
    /**
     * Return a long description of the item that includes the description 
     */
    public String getCharacterDescription() 
    {
        return characterDescription;
    }
    
    /**
     * Return strength of character
     */
    public int getCharacterStrength() 
    {
        return characterStrength;
    }
    
    /**
     * gets the current room the monster is in
     */
    public Room getRoom()
    {
        return this.currentRoom;
    }
}
