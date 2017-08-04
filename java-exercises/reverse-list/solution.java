// This program includes a subroutine that makes a reversed copy of a
   // list of ints.  The main program simply tests that routine on a few lists.
   
   public class ReverseListDemo {
   
   
      static class ListNode {
            // Objects of type ListNode are linked together into linked lists.
         int item;        // An item in the list.
         ListNode next;   // Pointer to the next node in the list.
      }
      
   
      static ListNode reverse( ListNode list ) {
              // Return a new list containing the same items as the list,
              // but in the reverse order.
         ListNode rev = null;     // rev will be the reversed list.
         ListNode runner = list;  // For running through the nodes of list.
         while (runner != null) {
                // "Push" the next node of list onto the front of rev.
            ListNode newNode = new ListNode();
            newNode.item = runner.item;
            newNode.next = rev;
            rev = newNode;
               // Move on to the next node in the list.
            runner = runner.next;
         }
         return rev;
      } // end reverse()
      
      
      static void printList(ListNode start) {
            // Prints the items in the list to which start points.
            // They are printed on one line, separated by spaces.
          ListNode runner;  // For running along the list.
          runner = start;
          while (runner != null) {
             System.out.print(" " + runner.item);
             runner = runner.next;
          }
      } // end printList()
      
   
      static void main(String[] args) {
      
         System.out.println("I will print out a list and its reverse, for");
         System.out.println("several different lists.  The first list is empty.\n");
         
         ListNode list = null;   // A list, initially empty.
         ListNode reversedList;  // The reversed list.
         
         int ct = 0;  // How many lists have we processed?
         
         while (true) {
                // Print the current list and its reverse.  Then
                // add a new node onto the head of the list before
                // repeating.
             System.out.print("The list:          ");
             printList(list);
             System.out.println();
             reversedList = reverse(list);
             System.out.print("The reversed list: ");
             printList(reversedList);
             System.out.println();
             System.out.println();
             ct++;
             if (ct == 6)
                break;
             ListNode head = new ListNode();  // A new node to add to the list.
             head.item = (int)(Math.random()*100);  // A random item.
             head.next = list;
             list = head;
         }
         
      } // end main()
      
   
   } // end class ReverseListDemo

