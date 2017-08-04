  
   public class SimpleParser4 {
   
   
       static class ParseError extends Exception {
              // Represents a syntax error found in the user's input.
          ParseError(String message) {
              super(message);
          }
       } // end nested class ParseError
       
       
       public static void main(String[] args) {
       
           while (true) {
              TextIO.putln("\n\nEnter an expression, or press return to end.");
              TextIO.put("\n?  ");
              skipBlanks();
              if ( TextIO.peek() == '\n' )
                 break;
              try {
                 ExpNode exp = expressionTree();
                 skipBlanks();
                 if ( TextIO.peek() != '\n' )
                    throw new ParseError("Extra data after end of expression.");
                 TextIO.getln();
                 TextIO.putln("\nValue at x = 0 is " + exp.value(0));
                 TextIO.putln("Value at x = 1 is " + exp.value(1));
                 TextIO.putln("Value at x = 2 is " + exp.value(2));
                 TextIO.putln("Value at x = 3 is " + exp.value(3));
                 TextIO.putln("\nOrder of postfix evaluation is:\n");
                 exp.printStackCommands();
              }
              catch (ParseError e) {
                 TextIO.putln("\n*** Error in input:    " + e.getMessage());
                 TextIO.putln("*** Discarding input:  " + TextIO.getln());
              }
           }
           
           TextIO.putln("\n\nDone.");
       
       } // end main()
       
       
       static void skipBlanks() {
             // Skip past any spaces and tabs on the current line of input.
             // Stop at a non-blank character or end-of-line.
          while ( TextIO.peek() == ' ' || TextIO.peek() == '\t' )
             TextIO.getAnyChar();
       }
       
       
       static ExpNode expressionTree() throws ParseError {
              // Read an expression from the current line of input and
              // return an expression tree representing the expression.
          skipBlanks();
          boolean negative;  // True if there is a leading minus sign.
          negative = false;
          if (TextIO.peek() == '-') {
             TextIO.getAnyChar();
             negative = true;
          }
          ExpNode exp;   // The expression tree for the expression.
          exp = termTree();  // Start with the first term.
          if (negative)
             exp = new UnaryMinusNode(exp);
          skipBlanks();
          while ( TextIO.peek() == '+' || TextIO.peek() == '-' ) {
                   // Read the next term and combine it with the
                   // previous terms into a bigger expression tree.
              char op = TextIO.getAnyChar();
              ExpNode nextTerm = termTree();
              exp = new BinOpNode(op, exp, nextTerm);
              skipBlanks();
          }
          return exp;
       } // end expressionTree()
   
   
       static ExpNode termTree() throws ParseError {
              // Read a term from the current line of input and
              // return an expression tree representing the term.
          skipBlanks();
          ExpNode term;  // The expression tree representing the term.
          term = factorTree();
          skipBlanks();
          while ( TextIO.peek() == '*' || TextIO.peek() == '/' ) {
                   // Read the next factor, and combine it with the
                   // previous factors into a bigger expression tree.
              char op = TextIO.getAnyChar();
              ExpNode nextFactor = factorTree();
              term = new BinOpNode(op,term,nextFactor);
              skipBlanks();
          }
          return term;
       } // end termTree()
       
       
       static ExpNode factorTree() throws ParseError {
              // Read a factor from the current line of input and
              // return an expression tree representing the factor.
          skipBlanks();
          char ch = TextIO.peek();
          if ( Character.isDigit(ch) ) {
                 // The factor is a number.  Return a ConstNode.
             double num = TextIO.getDouble();
             return new ConstNode(num);
          }
          else if ( ch == 'x' || ch == 'X' ) {
                // The factor is the variable x.
             TextIO.getAnyChar();   // Read the X.
             return new VariableNode();
          }
          else if ( ch == '(' ) {
                // The factor is an expression in parentheses.
             TextIO.getAnyChar();  // Read the "("
             ExpNode exp = expressionTree();
             skipBlanks();
             if ( TextIO.peek() != ')' )
                throw new ParseError("Missing right parenthesis.");
             TextIO.getAnyChar();  // Read the ")"
             return exp;
          }
          else if ( ch == '\n' )
             throw new ParseError("End-of-line encountered in the middle of an expression.");
          else if ( ch == ')' )
             throw new ParseError("Extra right parenthesis.");
          else if ( ch == '+' || ch == '-' || ch == '*' || ch == '/' )
             throw new ParseError("Misplaced operator.");
          else
             throw new ParseError("Unexpected character \"" + ch + "\" encountered.");
       }  // end factorTree()
   
   
   } // end class SimpleParser4