
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class ItemRegister / inventory - items in a game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Item" represents one tool in the inventory of the game. 
 * 
 * @author  Akash Payne
 * @version 10/02/2014
 */
public class ItemRegister
{
    private String itemDescription;
    private int itemStrength = 28;
    // private HashMap items;
    // a constant array that holds all items
    private HashMap<String, Item> inventory; // stores items of this Item

    /**
     * Constructor for objects of class Item
     * Create an item described "description"
     */
    public ItemRegister(String description,int itemStrength)
    {
        this.itemDescription = itemDescription;
        this.itemStrength = itemStrength;
        this.inventory = new HashMap<String, Item>();
    }

    public void register(String name, String description, int itemStrength) 
    {
        inventory.put(name, new Item(description,itemStrength));
    }
   
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setItem(String direction, Item itemList) 
    {
        inventory.put(direction, itemList);
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
