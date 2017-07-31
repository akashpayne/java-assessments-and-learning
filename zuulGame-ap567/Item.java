import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Item - an item in a room.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Item" represents one tool in the inventory of the game. 
 * 
 * @author  Akash Payne
 * @version 10/02/2014
 */
public class Item
{
    private String itemDescription;
    private int itemStrength;
    // private HashMap items;
    // a constant array that holds all items
    private HashMap<String, Item> items; // stores items of this Item

    /**
     * Constructor for objects of class Item
     * Create an item described "description"
     */
    public Item(String description,int itemStrength)
    {
        this.itemDescription = itemDescription;
        this.itemStrength = itemStrength;
        this.items = new HashMap<String, Item>();
    }

    /**
     * Return a long description of the item that includes the description 
     */
    public String toString() {
        return "Items: "+items;
    } 
    
    /**
     * lists all items in the games inventory.
     */
    public String fromString(String description)
    {
        String returnString = "Inventory:";
        Set<String> keys = items.keySet();
        for(String items : keys) {
            returnString += " " + items;
        }
        return returnString;
    } 
    
    /**
     * Return a long description of the item that includes the description 
     */
    public String getItemDescription() 
    {
        return itemDescription;
    }
    
    /**
     * Return Strength of the item 
     */
    public int getItemStrength() 
    {
        return itemStrength;
    }
}
