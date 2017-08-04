/*
      This program lets the user specify a text file for input and a file
      for output.  All the words are read from the input file.  Words are
      converted to lower case.  An alphabetical list of all the words that
      were found, without repetition, is written to the output file, with
      one word per line.  A word in this program is defined to be any
      sequence of letters.
      
      In this version of the program, words are stored in a binary
      sort tree.
      
      This class depends on the non-standard classes TextIO and TextReader,
   */
   
   import java.io.*;
   
   public class WordListWithTree {
   
   
      //------------------ The main program ------------------------------------
         
   
      static TreeNode root;  // Points to the root of the binary sort
                             //   tree that holds the words.  At the
                             //   beginning of the program, when the tree
                             //   is empty, root is null.
      
   
      public static void main(String[] args) {
      
         TextReader in;    // A stream for reading from the input file.
         PrintWriter out;  // A stream for writing to the output file.
         
         String inputFileName;   // Input file name, specified by the user.
         String outputFileName;  // Output file name, specified by the user.
         
         root = null;  // Start with an empty tree.  (Not really necessary,
                       //   since null is the default initial value anyway.)
         
         /* Get the input file name from the user and try to create the
            input stream.  If there is a FileNotFoundException, print
            a message and terminate the program. */
         
         TextIO.put("Input file name?  ");
         inputFileName = TextIO.getln().trim();
         try {
            in = new TextReader(new FileReader(inputFileName));
         }
         catch (FileNotFoundException e) {
             TextIO.putln("Can't find file \"" + inputFileName + "\".");
             return;
         }
         
         /* Get the output file name from the user and try to create the
            output stream.  If there is an IOException, print a message
            and terminate the program. */
   
         TextIO.put("Output file name? ");
         outputFileName = TextIO.getln().trim();
         try {
            out = new PrintWriter(new FileWriter(outputFileName));
         }
         catch (IOException e) {
             TextIO.putln("Can't open file \"" + outputFileName + "\" for output.");
             TextIO.putln(e.toString());
             return;
         }
         
         /* Read all the words from the input stream and insert them into
            the array of words.  Reading from a TextReader can result in
            an error of type TextReader.Error.  If one occurs, print an
            error message and terminate the program. */
         
         try {
            while (true) {
                  // Skip past and non-letters in the input stream.  If an
                  //   end-of-stream has been reached, end the loop.  Otherwise,
                  //   read a word and insert it into the array of words.
               while ( ! in.eof() && ! Character.isLetter(in.peek()) )
                  in.getAnyChar();
               if (in.eof())
                  break;
               insertWord(in.getAlpha());
            }
         }
         catch (TextReader.Error e) {
            TextIO.putln("An error occurred while reading from the input file.");
            TextIO.putln(e.toString());
            return;
         }
         
         /* Write all the words from the tree to the output stream. */
   
         inorderPrint(root, out);
         
         /* Finish up by checking for an error on the output stream and
            printing either a warning message or a message that the words
            have been output to the output file. */
         
         if (out.checkError() == true) {
            TextIO.putln("\nSome error occurred while writing output.");
            TextIO.putln("Output might be incomplete or invalid.");
         }
         else {
            TextIO.putln("\n" + countNodes(root) + " words from \"" + inputFileName + 
                          "\" output to \"" + outputFileName + "\".");
         }
      
      } // end main()
      
   
     //------------- A class and subroutines for the binary sort tree ----------
   
      static class TreeNode {
                 // An object of type TreeNode represents one node
                 // in a binary tree of strings.
          String item;      // The data in this node.
          TreeNode left;    // Pointer to left subtree.
          TreeNode right;   // Pointer to right subtree.
          TreeNode(String str) {
                 // Constructor.  Make a node containing str.
             item = str;
          }
      }  // end class TreeNode
      
      
      static void inorderPrint(TreeNode node, PrintWriter output) {
               // Output the items in the tree to which node points.
               // The items are listed, one to a line, on the specified
               // output streams.  An inorder listing is used, which
               // will print the words in the binary sort tree in
               // alphabetical order.
           if (node != null) {
              inorderPrint( node.left, output );
              output.println(node.item);
              inorderPrint( node.right, output );
           }
      }  // end inorderPrint()
      
      
      static int countNodes(TreeNode node) {
             // Return the number of nodes in the tree to which node points.
          if (node == null)
             return 0;
          else 
             return 1 + countNodes(node.left) + countNodes(node.right);
      }
      
      
      static void insertWord(String newItem) {
               // Add the word to the binary sort tree to which the
               // global variable "root" refers.  Duplicate entries
               // will be ignored!  All words are converted to lower case.
         newItem = newItem.toLowerCase();
         if ( root == null ) {
                 // The tree is empty.  Set root to point to a new node 
                 // containing the new item.
             root = new TreeNode( newItem );
             return;
          }
          TreeNode runner; // Runs down the tree to find a place for newItem.
          runner = root;   // Start at the root.
          while (true) {
             if ( newItem.equals(runner.item) ) {
                     // The word is already in the tree.  Don't insert
                     // another copy.  Just return.
                 return;
             }
             if ( newItem.compareTo(runner.item) < 0 ) {
                      // Since the new item is less than the item in runner,
                      // it belongs in the left subtree of runner.  If there
                      // is an open space at runner.left, add a node there.
                      // Otherwise, advance runner down one level to the left.
                if ( runner.left == null ) {
                   runner.left = new TreeNode( newItem );
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
                   runner.right = new TreeNode( newItem );
                   return;  // New item has been added to the tree.
                }
                else
                   runner = runner.right;
              }
          } // end while
      }  // end insertWord()
      
      
   }  // end class WordListWithTree