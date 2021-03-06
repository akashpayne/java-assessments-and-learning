 /*
      This program includes a non-recursive subroutine that prints the
      nodes of a binary tree, using a queue.  The main program simply
      tests that routine.  (The nodes are printed in what is called
      "level order".)
      
      This file defines the queue and tree classes, as well as the main
      program.  Not particularly good style...
   */
   
   class StrTreeNode {
         // An object in this class is a node in a binary tree
         // in which the nodes contain items of type String.
      String item;  // The item
      StrTreeNode left;  // Pointer to left subtree.
      StrTreeNode right; // Pointer to right subtree.
      StrTreeNode( String str ) {
            // Constructor.  Make a node to contain str.
         item = str;
      }
   } // end class StrTreeNode
   
   
   class TreeQueue {
         // An object of this type represents a queue of StrTreeNodes,
         // with the usual operations: dequeue, enqueue, isEmpty.
   
      private static class Node {
             // An object of type Node holds one of the items
             // in the linked list that represents the queue.
         StrTreeNode item;
         Node next;
      }
   
      private Node head = null;  // Points to first Node in the queue.
                                 // The queue is empty when head is null.
      
      private Node tail = null;  // Points to last Node in the queue.
   
      void enqueue( StrTreeNode tree ) {
            // Add N to the back of the queue.
         Node newTail = new Node();  // A Node to hold the new item.
         newTail.item = tree;
         if (head == null) {
               // The queue was empty.  The new Node becomes
               // the only node in the list.  Since it is both
               // the first and last node, both head and tail
               // point to it.
            head = newTail;
            tail = newTail;
         }
         else {
               // The new node becomes the new tail of the list.
               // (The head of the list is unaffected.)
            tail.next = newTail;
            tail = newTail;
         }
      }
      
      StrTreeNode dequeue() {
             // Remove and return the front item in the queue.
             // Note that this can throw a NullPointerException.
         StrTreeNode firstItem = head.item;
         head = head.next;  // The previous second item is now first.
         if (head == null) {
               // The queue has become empty.  The Node that was
               // deleted was the tail as well as the head of the
               // list, so now there is no tail.  (Actually, the
               // class would work fine without this step.)
            tail = null;
         } 
         return firstItem;
      }
      
      boolean isEmpty() {
             // Return true if the queue is empty.
         return (head == null);
      }
         
   } // end class TreeQueue
    
   
   public class TreePrintNR {   // MAIN PROGRAM CLASS
   
      static StrTreeNode root;  // A pointer to the root of the binary tree.
      
      
      static void levelOrderPrint(StrTreeNode root) {
              // Use a queue to print all the strings in the tree to which
              // root points.  (The nodes will be listed in "level order",
              // that is:  first the root, then children of the root, then
              // grandchildren of the root, and so on.)
          if (root == null)
             return;  // There is nothing to print in an empty tree.
          TreeQueue queue;   // The queue, which will only hold non-null nodes.
          queue = new TreeQueue();
          queue.enqueue(root);
          while ( queue.isEmpty() == false ) {
             StrTreeNode node = queue.dequeue();
             System.out.println( node.item );
             if ( node.left != null )
                queue.enqueue( node.left );
             if ( node.right != null )
                queue.enqueue( node.right );
          }
      } // end levelOrderPrint()
      
      
      static void treeInsert(String newItem) {
               // Add the word to the binary sort tree to which the
               // global variable "root" refers.  I will use this 
               // routine only to create the sample tree on which
               // I will test levelOrderPrint().
         if ( root == null ) {
                 // The tree is empty.  Set root to point to a new node 
                 // containing the new item.
             root = new StrTreeNode( newItem );
             return;
          }
          StrTreeNode runner; // Runs down the tree to find a place for newItem.
          runner = root;   // Start at the root.
          while (true) {
             if ( newItem.compareTo(runner.item) < 0 ) {
                      // Since the new item is less than the item in runner,
                      // it belongs in the left subtree of runner.  If there
                      // is an open space at runner.left, add a node there.
                      // Otherwise, advance runner down one level to the left.
                if ( runner.left == null ) {
                   runner.left = new StrTreeNode( newItem );
                   return;  // New item has been added to the tree.
                }
                else
                   runner = runner.left;
             }
             else {
                      // Since the new item is greater than or equal to the 
                      // item in runner, it belongs in the right subtree of
                      // runner.  If there is an open space at runner.right, 
                      // add a new node there.  Otherwise, advance runner
                      // down one level to the right.
                if ( runner.right == null ) {
                   runner.right = new StrTreeNode( newItem );
                   return;  // New item has been added to the tree.
                }
                else
                   runner = runner.right;
              }
          } // end while
      }  // end treeInsert()
      
      
      public static void main(String[] args) {
             // Make a tree with a known form, then call levelOrderPrint()
             // for that tree.  (I want to check that all the items from
             // the tree are printed, and I want to see the order in which
             // they are printed.  The expected order of output is
             // judy bill mary alice fred tom dave jane joe.  The
             // tree that is built here is from an illustration in
             // Section 11.4.)
         treeInsert("judy");
         treeInsert("bill");
         treeInsert("fred");
         treeInsert("mary");
         treeInsert("dave");
         treeInsert("jane");
         treeInsert("alice");
         treeInsert("joe");
         treeInsert("tom");
         levelOrderPrint(root);
      } // end main()
   
   
   } // end class TreePrintNR
   
