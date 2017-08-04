  /*
       This program reads standard expressions typed in by the user.
       The expressions can contain the variable "x", in addition to
       positive numbers and the operators +, -, *, and /.
       The program constructs an expression tree to represent the
       expression.  It then prints the value of the tree at several
       values of the variable x.  It also uses the tree to print out
       a list of commands that could be used on a stack machine to 
       evaluate the expression.
   
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
       
       In addition to the main program class, SimpleParser4, this program
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
          // The printStackCommands() method prints a list
          // of commands that could be used to evaluate the expression on
          // a stack machine (assuming that the value of the expression is
          // to be left on the stack).
      abstract double value(double xValue); 
      abstract void printStackCommands();
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
   }

