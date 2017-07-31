
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class CharacterRegister / characters - characters in a game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Character" represents one tool in the characters of the game. 
 * 
 * @author  Akash Payne
 * @version 10/02/2014
 */
public class CharacterRegister
{
    private String characterDescription;
    private int characterStrength;
    private Room currentRoom;
    // private HashMap characters;
    // a constant array that holds all characters
    private HashMap<String, CharacterRegister> characterRegister; // stores characters of this Character
    
    
    /**
     * Constructor for objects of class Character
     * Create an character described "description"
     */
    public CharacterRegister(String description, int characterStrength)
    {
        this.characterDescription = characterDescription;
        this.characterStrength = characterStrength;
        this.characterRegister = new HashMap<String, CharacterRegister>();
    }

    /**
     * adds a new character to character register
     */    
    public void register(String name, String description,int characterStrength) 
    {
        characterRegister.put(name, new CharacterRegister(description,characterStrength));
    }
   
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setCharacter(String direction, CharacterRegister characterList) 
    {
        characterRegister.put(direction, characterList);
    }
    
    /**
     * Return a long description of the character that includes the description 
     */
    public String getCharacterDescription() 
    {
        return characterDescription;
    }
    

    
}
