import java.util.Stack;
import cs1.Keyboard;

public class Decode
{
   //-----------------------------------------------------------------
   //   Decodes a message by reversing each word in a string.
   //-----------------------------------------------------------------
   public static void main (String[] args)
   {  
      Stack word = new Stack();
      String message;
      int index = 0;

      System.out.println ("Enter the coded message:");
      message = Keyboard.readString();
      System.out.println ("The decoded message is:");

      while (index < message.length())
      {
         // Push word onto stack
         while (index < message.length() && message.charAt(index) != ' ')
         {
            word.push (new Character(message.charAt(index)));
            index++;
         }

         // Print word in reverse
         while (!word.empty())
            System.out.print (((Character)word.pop()).charValue());
         System.out.print (" ");
         index++;
      }

      System.out.println();
   }
}