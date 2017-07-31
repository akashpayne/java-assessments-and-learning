import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents players in the game. Each player has 
 * a current room.
 * 
 * * @editor Akash Payne
 * * @edit 10/3/2014
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Room goalRoom;
    private int strength = 28;
    private boolean charactersMoved;
    // count The number of current number of moves
    public int count;
    // The limit to the number of moves
    public int limit;
    public HashMap<String, ItemRegister> inventory; // stores items of this Item
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        currentRoom = null;
        inventory = new HashMap<String, ItemRegister>();
    }

    /** 
     * Return the current room for this player. 
     */ 
    public Room getCurrentRoom() 
    { 
        return currentRoom; 
    } 
    
    /** 
     * Set the Goal room for this player. 
     */ 
    public void setGoalRoom(Room room) 
    { 
        goalRoom = room; 
    } 
    
    /** 
     * Set the current room for this player. 
     */ 
    public void setCurrentRoom(Room room) 
    { 
        currentRoom = room; 
    }
        
    /**
     * sets the count and limit
     */    
    public void setCount()
    {
        // set count to 0
        count=0;
        // set limit
        limit = 20; 
    }
       
    /**
     * returns the limit
     */    
    public int getLimit()
    {
        return limit;
    }
       
    /** 
     * Try to walk in a given direction. If there is a door 
     * this will change the player's location. 
    */ 
    public boolean goRoom(Command command) 
        { 
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            boolean moved = false;//incrementCount(nextRoom);
            return moved;
        }

        String direction = command.getSecondWord();
        
        // Try to leave current room. 
        Room nextRoom = currentRoom.getExit(direction); 
        
        if (nextRoom == null){ 
            System.out.println("There is no door!");
            boolean moved = incrementCount(nextRoom);
            return moved;
        } 
        else if (nextRoom.equals(goalRoom)) {
            System.out.println("Congratulations, you have reached the Throne room.");
            System.out.println("Game Over.");
            boolean moved = true;
            return moved;
        } else { 
            setCurrentRoom(nextRoom);   
            System.out.println(nextRoom.getLongDescription()); 
            boolean moved = incrementCount(nextRoom);
            return moved;
        } 
    } 
    
    /**
     * increases the count for every time the user moves room
     * * moves all the characters when you get into the room that they are in
     */    
    public boolean incrementCount(Room nextRoom) 
    {
        // Increase the count
        count++;
        charactersMoved = false;
        while(!charactersMoved)
        {
            charactersMoved = moveAllCharacters(currentRoom);
        }
        // Number of moves 
        if (count < limit) 
        {
            System.out.println("Current number of moves : " + count);
            System.out.println("Moves avaliable : " + (limit - count));
            return false;
            // Game ends when number of moves has been reached 
        }
        else 
        {
            System.out.println("You have reached the maximum number of moves, "+limit+".");
            System.out.println("Game Over.");
            return true;
        }
    }
    
    /**
    * @returns items description.
    */
    public ItemRegister getItem(String name)
    {
        return (ItemRegister)inventory.get(name);
    }
    
    /**
     * the action to drop an item
     */    
    public void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("drop what?");
            return;
        }
        String item = command.getSecondWord();
        Room currentRoom = getCurrentRoom();
        ItemRegister dropItem = getItem(item);
        if (dropItem == null){ 
            System.out.println("There is no item with that name in your iventory!");
        } else { 
            String itemDescription = dropItem.getItemDescription();
            int itemStrength = dropItem.getItemStrength();
            strength = strength - itemStrength;
            currentRoom.addItem(item, itemDescription);
            //System.out.println(item); //
            //System.out.println(dropItem);//
            //System.out.println(itemDescription);
            System.out.println(item + " has been dropped.");
            removeInventory(item);
            System.out.println("Strength is now : "+strength);
        }
    }
        
    /**
     * the action to take an item
     */    
    public void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String item = command.getSecondWord();
        Room currentRoom = getCurrentRoom();
        Item takenItem = currentRoom.getItem(item);        
        if (takenItem == null){ 
            System.out.println("There is no item with that name in this room!");
        } else { 
            String itemDescription = takenItem.getItemDescription();
            int itemStrength = takenItem.getItemStrength();
            strength = strength - itemStrength;
            addInventory(item,itemDescription,itemStrength);
            //System.out.println(item); 
            //System.out.println(takenItem);
            //System.out.println(itemDescription);
            // doesn't work - doesn't put spoon in itemRegister
            System.out.println(item + " has been taken.");
            System.out.println("Strength is now : "+strength);
            currentRoom.removeItem(item);
        }
    }

    /**
     * adds an item into the player's inventory
     */    
    public void addInventory(String name,String decsription,int itemStrength)
    {
        inventory.put(name, new ItemRegister(decsription, itemStrength));
    }
    
    /**
     * removes the item from the player's inventory
     */    
    public void removeInventory(String name)
    {
        inventory.remove(name);
    }
    
    /**
     * shows all items the player has in their inventory
     */    
    public void showInventory(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("What command (show)?");
            return;
        }
        String item = command.getSecondWord();
        Room currentRoom = getCurrentRoom();
        ItemRegister listItem = getItem(item);
        if (listItem == null){ 
            System.out.println("There is nothing in your inventory.");
        } else { 
            String itemDescription = listItem.getItemDescription();
            System.out.println(fromString(itemDescription));
        }        
    }
    
    /**
     * lists all items in the games inventory.
     */
    public String fromString(String description)
    {
        String returnString = "Inventory:";
        Set<String> keys = inventory.keySet();
        for(String inventory : keys) {
            returnString += " " + inventory;
        }
        return returnString;
    } 
    
    /**
     * moves all characters. 
     * All characters run away from the player.
     */
    public boolean moveAllCharacters(Room currentRoom)
    {
        Set<String> characterNames = currentRoom.getCharacterKeys();
        for(String chars : characterNames) 
        {
            charactersMoved = moveCharacter(chars, currentRoom);
        }
        return true;
    } 
    
    /**
     * move character
     */
    public boolean moveCharacter(String chars, Room currentRoom)
    {
        Character charactersObj = currentRoom.getCharacter(chars);
        String characterDescription = charactersObj.getCharacterDescription();
        Room nextRoom = currentRoom.getRanExit();
        if (nextRoom == null)
        {
            System.out.println(chars + " has not moved.");
            boolean deleted = false;
            return deleted;
        }else
        {
            nextRoom.addCharacter(chars, characterDescription, charactersObj.characterStrength);
            if (chars != null) {
                 // delete character from old room
                 // currentRoom.removeCharacter(chars);
                 
            }
            System.out.println(chars + " has moved to " + nextRoom.getShortDescription());
            boolean deleted = true;
            return deleted;
        }
    }
    
    /**
     * the action to fight a character
     */    
    public boolean fight(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("fight what?");
            return false;
        }
        String character = command.getSecondWord();
        Room currentRoom = getCurrentRoom();
        CharacterRegister characterList = currentRoom.getCharacterReg(character);
        if (characterList == null) {
            System.out.println("There is no fight");
            return false;
        }
        else 
        {
            Character charactersObj = currentRoom.getCharacter(character);
            int characterStrength = charactersObj.characterStrength;
            int r = randInt(1,10);
            int q = randInt(1,10);
            if ((characterStrength*r) >= (strength*r))
            {
                System.out.println("You are dead");
                return true;
            } else if ((characterStrength*r) >= (strength*r)) 
            {
                System.out.println("You beat "+character);
                return false;
                // could delete character
            }
        }
        return false;
    }
    
    /**
     * Returns a random number between min and max.
     * The difference between min and max can be at most
     * 
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     */
    public static int randInt(int min, int max) 
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
