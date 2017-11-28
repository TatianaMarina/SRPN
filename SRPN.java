import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 *  Program class for an SRPN calculator.
 * 
 *  @version 1.7
 *  @release 28/11/2017
 *  @copyright Copyright (C) 2017 Ed Jeffery - All Rights Reserved
 */

public class SRPN {
   
	Stack<String> myStack = new Stack<String>();
	RandomNumber r = new RandomNumber(myStack);
	
	/**
	 * Method. Checks if string is an operator.
	 * 
	 * @param s
	 * 		String from split up line of input (using regex)
	 * @return Boolean
	 * 		True if operator, false if not
	 */
	public static boolean isOperator(String s) {
		
		if ( s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*") || s.equals("+") ||
				s.equals("^") || s.equals("%") || s.equals("=") ) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Method. Checks if string is an integer.
	 * 
	 * @param s
	 * 		String from split up line of input (using regex)
	 * @return Boolean
	 * 		True if integer, false if not
	 */
	public static boolean isInteger(String s) {
		
		if (s.matches("^(-?)[0-9]+$")) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Method. Checks if string is a special value.
	 * 
	 * @param s
	 * 		String from split up line of input (using regex)
	 * @return Boolean
	 * 		True if special, false if not
	 */
	public static boolean isSpecial(String s) {
		
		if (s.equals("d") | s.equals("r")) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Method. Checks if string is an octal number.
	 * @param s
	 * 		String from split up line of input (using regex)
	 * @return Boolean
	 * 		True if octal, false if not
	 */
	public static boolean isOctal(String s) {
		
		if (s.length() > 1 && !s.contains("8") && !s.contains("9")) {
			if (s.charAt(0) == '0') {
				return true;
			}
			else if (s.charAt(0) == '-' && s.charAt(1) == '0') {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Method. Checks whether calculation saturates the integer's range and returns saturated number if true.
	 * @param bigInteger
	 * 		Result from calculation, stored as a long
	 * @return Either calculation value or maximum/minimum integer value if saturated
	 */
	public int fixSaturation(BigInteger bigInteger) {
		
		if (bigInteger.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
			return Integer.MAX_VALUE;
		}
		else if (bigInteger.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) == -1) {
        		return Integer.MIN_VALUE;
        }
		else {
			return bigInteger.intValue();
		}
		
	}
	
	/**
	 * Method. Converts octal number to decimal.
	 * 
	 * @param s
	 * 		String from split up line of input (using regex)
	 * @return Decimal value converted to a String (with saturation check)
	 */
	public String octalConversion(String s) {
		
		BigInteger dec = new BigInteger(s, 8);
		
		return String.valueOf(fixSaturation(dec));
		
	}
	
	/**
	 * Method. Prints the whole stack, starting from the first value.
	 */
	public void printStack() {
		
		for (int i = 0; i < myStack.size(); i++) {
			System.out.println(myStack.get(i));
		}
		
	}
	
	/**
	 * Method. Removes text from # character to the end of the string.
	 * 
	 * @param s
	 * 		String from split up line of input (using regex)
	 * @return String without comment in
	 */
	public String removeComment(String s) {
		
		int comment = s.indexOf('#');
		
		return s.substring(0, comment);
		
	}
	
	/**
	 * Method. Performs operation on two numbers.
	 * 
	 * @param num1
	 * 		2nd to last number that was popped from stack
	 * @param num2
	 * 		Last number that was popped from stack
	 * @param operator
	 * 		Operator symbol i.e. + - / * ^ % 
	 */
	public void performOperation(long num1, long num2, String operator) {
		
		switch(operator) {
			case "+":
				myStack.push(String.valueOf(fixSaturation(BigInteger.valueOf(num1 + num2))));
				break;
			case "-":
				myStack.push(String.valueOf(fixSaturation(BigInteger.valueOf(num1 - num2))));
				break;
			case "/":
				//If last number on stack is 0, display error msg and place numbers back on stack.
				if (num2 == 0) {
					System.out.println("Divide by 0.");
					myStack.push(String.valueOf(num1)); //2nd to last number pushed back on.
					myStack.push(String.valueOf(num2)); //Last number pushed back on.
				}
				else {
					myStack.push(String.valueOf(fixSaturation(BigInteger.valueOf(num1 / num2))));
				}
				break;
			case "*":
				myStack.push(String.valueOf(fixSaturation(BigInteger.valueOf(num1 * num2))));
				break;
			case "^":
				if (num2 < 0) {
					System.out.println("Negative power.");
					myStack.push(String.valueOf(num1)); //2nd to last number pushed back on.
					myStack.push(String.valueOf(num2)); //Last number pushed back on.
				}
				else {
					myStack.push(String.valueOf(fixSaturation(BigInteger.valueOf((long) Math.pow(num1, num2)))));
				}
				break;
			case "%":
				myStack.push(String.valueOf(fixSaturation(BigInteger.valueOf(num1 % num2))));
				break;	
		}
		
	}

	/**
	 * Method. Perform relevant operations on a string containing input from the command-line.
	 * @param s
	 * 		String containing line of input from the command-line
	 * @exception EmptyStackException
	 * 		Stack is empty so no number can be popped or peeked upon
	 * @exception StackOverflowError
	 * 		Stack is full (23 values stored)
	 * @exception NumberFormatException
	 * 		The character or string is not a recognised operator or operand.
	 */
	public void processCommand(String s) {
		//Removal of comments first
		if (s.contains("#")) {
			processCommand(removeComment(s));
		}
		//Checks if string is a tab
		else if (s.equals("\t")) {
			System.out.println("Unrecognised operator or operand " + "\"" + s + "\".");
		}
		//Check for special Rachid statement
		else if (s.contains("rachid")) {
			System.out.println("Rachid is the best unit lecturer.");
		}
		else {
			//Split string using a regular expression, then process.
			//Regular expression splits up string by operators and numbers and any unrecognised set of characters
			String[] stringArray = s.trim().split("(?<=[d+/=^%])|(?=[-d+/=^%])|(\\s)|(?<=[^0-9-d+/=^%])|(?=[^0-9-d+/=^%])");
			for (int i = 0; i < stringArray.length; i++) { 
				stringArray[i] = stringArray[i].trim();
				try {
					if (isOctal(stringArray[i])) {
						stringArray[i] = octalConversion(stringArray[i]);
					}
					if (isInteger(stringArray[i])) {
						//Check if stack is saturated before pushing
						if (myStack.size() >= 23) {
							throw new StackOverflowError();
						}
						//Otherwise push to stack
						else {
							BigInteger b = new BigInteger(stringArray[i]);
							myStack.push(String.valueOf(fixSaturation(b)));
						}
					}
					else if (isSpecial(stringArray[i])) {
						//If d inputted, print out stack
						if (stringArray[i].equals("d")) {
							printStack();
						}
						//If r inputted, print out number from random number list
						else if (stringArray[i].equals("r")) {
							//Check if stack is saturated before pushing
							if (myStack.size() >= 23) {
								throw new StackOverflowError();
							}
							//Otherwise push to stack
							else {
								r.pushRandomNumToStack();
								myStack = r.getMyStack();
							}
						}
					}
					else if (isOperator(stringArray[i])) {
						//If "=", print top of stack
						if (stringArray[i].equals("=")) {
							if (myStack.isEmpty()) {
								throw new EmptyStackException();
							}
							else {
								System.out.println("" + myStack.peek());
							}
						}
						//If stack does not have two elements, cannot perform operation
						else if (myStack.size() < 2) {
							throw new EmptyStackException();
						}
						//Otherwise perform operation
						else {
							long num2 = Long.valueOf((String) myStack.pop());
							long num1 = Long.valueOf((String) myStack.pop());
							
							performOperation(num1, num2, stringArray[i]);
						}
					}
					//If blank string or just contains whitespace
					else if (stringArray[i].equals("") || stringArray[i].trim().isEmpty()) {
						//Do nothing
					}
					//If not applicable to any previous conditional statement, throw exception
					else {
						throw new NumberFormatException();
					}

				} catch (EmptyStackException ese) {
					if (stringArray[i].equals("=")) {
						System.out.println("Stack empty.");
					}
					else {
						System.out.println("Stack underflow.");	
					}
				} catch (StackOverflowError soe) {
					System.out.println("Stack overflow.");
				} catch (NumberFormatException nfe) {
					//Print out unrecognised operator or operand by character
					for (char ch: stringArray[i].toCharArray()) {
						System.out.println("Unrecognised operator or operand " + "\"" + ch + "\".");
					}
				}
			}
		}
    }
	
	/**
	 * Main method. Reads input from the command-line and tries to send it to be processed.
	 * 
	 * @exception IOException
	 * 		Error in trying to read input stream.
	 */
    public static void main(String[] args) {
        SRPN srpn = new SRPN();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            //Keep on accepting input from the command-line
            while(true) {
                //Puts input into string
            		String command = reader.readLine();
                
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null) {
                  //Exit code 0 for a graceful exit
                  System.exit(0);
                }
                
                //Otherwise, (attempt to) process the character
                srpn.processCommand(command);          
            }
        } catch(IOException e) {
	          System.err.println(e.getMessage());
	          System.exit(1);
        }
    }
}


