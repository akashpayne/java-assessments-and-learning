   /*
       This program computes derivatives!
   
       The program reads standard expressions typed in by the user.
       The expressions can contain the variable "x", in addition to
       positive numbers and the operators +, -, *, and /.
       The program constructs an expression tree to represent the
       expression.   It uses the tree to compute the derivative of 
       the expression.  It prints the value of the derivative for
       several values of x, and it prints the derivative itself.
   
       The expressions are defined by the BNF rules:
       
               <expression>  ::=  [ "-" ] <term> [ [ "+" | "-" ] <term> ]...
               
               <term>  ::=  <factor> [ [ "*" | "/" ] <factor> ]...
               
               <factor>  ::=  <number>  |  <variable-x> |  "(" <expression> ")"
               
       <variable-x> represents the variable, which can be written as either 
       an upper case or lower case "x".
       
       A number must begin with a digit (i.e., not a decimal point).
       A line of input must contain exactly one such expression.  If extra
       data is found on a line after an expression has been read, it is
       considered an error.  
       
       In addition to the main program class, SimpleParser5, this program
       defines a set of four classes for implementing expression trees.
       (I know that it's not very good style to have them here...)
   */
   
   
   
   //-------------------- Classes for Expression Trees ------------------------------
   
   
   abstract class ExpNode {
          // An abstract class representing any node in an expression tree.
          // The following three concrete node classes are subclasses.
          // Two instance methods are specified, so that they can be used with
          // any ExpNode.  The value() method returns the value of the
          // expression at a specified value of the variable, x. 
          // The derivative() method returns an expression tree for the derivative 
          // of the expression (with no attempt at simplification).  Actually,
          // this might not be a tree, but it is a "directed acyclic graph",
          // with no loops, so it's OK for our purposes.  The printInfix()
          // method prints the expression in fully parenthesized form.
      abstract double value(double xValue); 
      abstract void printStackCommands();
      abstract void printInfix();
      abstract ExpNode derivative();
   }
   
   class VariableNode extends ExpNode {
          // An expression node that represents a reference to the variable, x.
      VariableNode() {
             // Construct a VariableNode. (There is nothing to do!)
      }
      double value(double xValue) {
            // The value of the node is the value of x.
         return xValue;
      }
      void printStackCommands() {
            // On a stack machine, just push the value of X onto the stack.
         TextIO.putln("  Push X"); 
      }
      void printInfix() {
         TextIO.put("x");
      }
      ExpNode derivative() {
            // The derivative of x is the constant 1.
         return new ConstNode(1);
      }
   }
   
   class ConstNode extends ExpNode {
          // An expression node that holds a number.
      double number;  // The number.
      ConstNode(double val) {
             // Construct a ConstNode containing the specified number.
         number = val;
      }
      double value(double xValue) {
            // The value of the node is the number that it contains,
            // no matter what the value of the variable x.
         return number;
      }
      void printStackCommands() {
            // On a stack machine, just push the number onto the stack.
         TextIO.putln("  Push " + number); 
      }
      void printInfix() {
         TextIO.put(number);
      }
      ExpNode derivative() {
            // The derivative of a constant is zero.
          return new ConstNode(0);
      }
   }
   
   class BinOpNode extends ExpNode {
          // An expression node representing a binary operator,
      char op;        // The operator.
      ExpNode left;   // The expression for its left operand.
      ExpNode right;  // The expression for its right operand.
      BinOpNode(char op, ExpNode left, ExpNode right) {
             // Construct a BinOpNode containing the specified data.
         this.op = op;
         this.left = left;
         this.right = right;
      }
      double value(double xValue) {
              // The value is obtained by evaluating the left and right
              // operands and combining the values with the operator.
          double x = left.value(xValue);
          double y = right.value(xValue);
          switch (op) {
             case '+':  return x + y;
             case '-':  return x - y;
             case '*':  return x * y;
             case '/':  return x / y;
             default:   return Double.NaN;  // Bad operator!
          }
      }
      void  printStackCommands() {
             // To evaluate the expression on a stack machine, first do
             // whatever is necessary to evaluate the left operand, leaving
             // the answer on the stack.  Then do the same thing for the
             // second operand.  Then apply the operator (which means popping
             // the operands, applying the operator, and pushing the result).
          left.printStackCommands();
          right.printStackCommands();
          TextIO.putln("  Operator " + op);
      }
      void printInfix() {
             // Print the expression, in parentheses.
         TextIO.put('(');
         left.printInfix();
         TextIO.put(" " + op + " ");
         right.printInfix();
         TextIO.put(')');
      }
      ExpNode derivative() {
            // Apply the derivative formulas.
          switch (op) {
             case '+':
                return new BinOpNode('+', left.derivative(), right.derivative());
             case '-':
                return new BinOpNode('-', left.derivative(), right.derivative());
             case '*':
                return new BinOpNode( '+',
                            new BinOpNode('*', left, right.derivative()),
                            new BinOpNode('*', right, left.derivative()) );
             case '/':
                return new BinOpNode( '/',
                            new BinOpNode('-', 
                                    new BinOpNode('*', right, left.derivative()),
                                    new BinOpNode('*', left, right.derivative())),
                            new BinOpNode('*', right, right) );
             default:
                return null;
          }
      }
   }
   
   class UnaryMinusNode extends ExpNode {
          // An expression node to represent a unary minus operator.
      ExpNode operand;  // The operand to which the unary minus applies.
      UnaryMinusNode(ExpNode operand) {
             // Construct a UnaryMinusNode with the specified operand.
         this.operand = operand;
      }
      double value(double xValue) {
            // The value is the negative of the value of the operand.
         double neg = operand.value(xValue);
         return -neg;
      }
      void printStackCommands() {
            // To evaluate this expression on a stack machine, first do
            // whatever is necessary to evaluate the operand, leaving the
            // operand on the stack.  Then apply the unary minus (which means
            // popping the operand, negating it, and pushing the result).
         operand.printStackCommands();
         TextIO.putln("  Unary minus");
      }
      void printInfix() {
            // Print the expression, in parentheses.
         TextIO.put("(-");
         operand.printInfix();
         TextIO.put(')');
      }
      ExpNode derivative() {
             // The derivative of -A is -(derivative of A).
         return new UnaryMinusNode(operand.derivative());
      }
   }
   
   
   //------------------------------------------------------------------------
   
   public class SimpleParser5 {
   
   
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
                 ExpNode deriv = exp.derivative();
                 TextIO.getln();
                 TextIO.putln("\nValue of derivative at x = 0 is " + deriv.value(0));
                 TextIO.putln("Value of derivative at x = 1 is " + deriv.value(1));
                 TextIO.putln("Value of derivative at x = 2 is " + deriv.value(2));
                 TextIO.putln("Value of derivative at x = 3 is " + deriv.value(3));
                 TextIO.putln("\nOrder of postfix evaluation for derivative is:\n");
                 deriv.printStackCommands();
                 TextIO.putln("\nA fully parenthesized expression for the derivative is:");
                 TextIO.put("   ");
                 deriv.printInfix();
                 TextIO.putln();
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
   
   
   } // end class SimpleParser5