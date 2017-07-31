

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RoomTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class RoomTest
{
    private Room room;
    private static String description = "room1";
    private static String description2 = "room2";
    private static String direction = "north";
    private static Room roomTwo = new Room(description2);
    
    /**
     * Default constructor for test class RoomTest
     */
    public RoomTest()
    {
                
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws Exception
    {
        room = new Room(description);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() throws Exception
    {
        room = null;
    }

    @Test
    public void checkRoomForExitAndTestObtain()
    {
        room.setExit(direction,roomTwo);
        assertEquals(roomTwo,room.getExit(direction));
    }
    
    @Test
    public void checkGetOfARoomWithNoExits()
    {
        assertNull(room.getExit(direction));
    }
    
    @Test 
    public void checkNewRoomHasNoItems()
    {
        assertNull(room.getItem(description));
    }
}

