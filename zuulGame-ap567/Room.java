import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 * * @editor Akash Payne
 * * @edit 10/3/2014
 */

public class Room 
{
    private String description;
    private String itemDescription;
    private int itemStrength;
    private int characterStrength;
    private Room goalRoom;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> items;     //stores items 
    private HashMap<String, Character> characters; // stores characters
    private HashMap<String, CharacterRegister> characterRegister; // stores characters
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        //createItems();
        this.description = description;
        exits = new HashMap<String, Room>();
        this.itemDescription = itemDescription;
        items = new HashMap<String, Item>();
        characters = new HashMap<String, Character>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Creates an item in the Item room list.
     */
    public void addItem(String name, String description)   
    {
        items.put(name, new Item(description,itemStrength));
    }
   
    /**
     * Creates a character in the character room list.
     */
    public void addCharacter(String name, String description, int characterStrength)
    {
        characters.put(name, new Character(description,characterStrength));
    }
    
    /** 
     * Searches for items in a room, returns true if any found, false if it doesn't find any.
     */
    public boolean searchRoom(String name) // not used
    {
        Set<String> keys = items.keySet();
        for(Iterator<String> i=keys.iterator();i.hasNext();) 
        {
            if(i.next().equals(name))
            {
                return true;
            }
        }
        return false;
    }
       
    /**
    * @returns items description.
    */
    public Item getItem(String name)
    {
        return items.get(name);
    }
    
    /**
     * @returns character object.
     */
    public Character getCharacters(String name)
    {
        return characters.get(name);
    }
    
    /**
     * @returns character object.
     */
    public CharacterRegister getCharacterReg(String name)
    {
        return characterRegister.get(name);
    }
    
    /**
     * removes an item from inventory
     */
    public void removeItem(String name)
    {
        items.remove(name);   
    }
    
    /**
     * remove a character from the room character list
     */
    public void removeCharacter(String name)
    {
        characters.remove(name);
    }
    
    /**
     * lists all items in the Rooms inventory.
     */
    private String getItemString()   
    {   
        String returnString = "Items:";
        Set<String> keys = items.keySet();
        for (String items : keys) { 
            returnString += " " + items;
        }
        return returnString;
    }
    
    /**
     * lists all characaters in the Room.
     */
    private String getCharacterString()   
    {   
        String returnString = "Characters:";
        Set<String> keys = characters.keySet();
        for (String characters : keys) { 
            returnString += " " + characters;
        }
        return returnString;
    }
    
    /**
     * get the set of character names
     */    
    public Set<String> getCharacterKeys()
    {
        return characters.keySet();
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " 
            + description + ".\n" 
            + getExitString() + ".\n" 
            + getItemString() + " \n"
            + getCharacterString() + " \n";
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
   
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getRanExit() 
    {
        return exits.get(randomMove());      
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * gets a random direction
     */    
    public String randomMove()
    {
        Random r = new Random();        
        String[] direction = new String[] {"east", "west", "south", "north"};
        return direction[r.nextInt(4)];
    }
    
    /**
     * gets the character object from a string, name.
     */    
    public Character getCharacter(String name) 
    {
        return characters.get(name);
    }
}

