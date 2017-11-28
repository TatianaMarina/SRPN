import java.math.BigInteger;
import java.util.Stack;

/**
 *  Operation class for handling calculations.
 * 
 *  @version 1.2
 *  @release 28/11/2017
 *  @copyright Copyright (C) 2017 Ed Jeffery - All Rights Reserved
 */
public class Operation {
	
	Stack<String> myStack = new Stack<String>();
	long num2;
	long num1;
	
	/**
	 * Constructor. Sets stack variable from input and pops and stores the numbers required for operation.
	 * 		Also calls operation method.
	 * 
	 * @param myStack
	 * 		Stack from the SRPN class.
	 */
	public Operation(Stack<String> myStack) {
		
		this.myStack = myStack;
		num2 = Long.valueOf((String) myStack.pop());
		num1 = Long.valueOf((String) myStack.pop());
		
	}
	
	/**
	 * Method. Performs operation on two numbers.
	 * 
	 * @param operator
	 * 		Operator symbol i.e. + - / * ^ % 
	 */
	public void performOperation(String operator) {
		
		switch(operator) {
			case "+":
				myStack.push(String.valueOf(SRPN.fixSaturation(BigInteger.valueOf(num1 + num2))));
				break;
			case "-":
				myStack.push(String.valueOf(SRPN.fixSaturation(BigInteger.valueOf(num1 - num2))));
				break;
			case "/":
				//If last number on stack is 0, display error msg and place numbers back on stack.
				if (num2 == 0) {
					System.out.println("Divide by 0.");
					myStack.push(String.valueOf(num1)); //2nd to last number pushed back on.
					myStack.push(String.valueOf(num2)); //Last number pushed back on.
				}
				else {
					myStack.push(String.valueOf(SRPN.fixSaturation(BigInteger.valueOf(num1 / num2))));
				}
				break;
			case "*":
				myStack.push(String.valueOf(SRPN.fixSaturation(BigInteger.valueOf(num1 * num2))));
				break;
			case "^":
				if (num2 < 0) {
					System.out.println("Negative power.");
					myStack.push(String.valueOf(num1)); //2nd to last number pushed back on.
					myStack.push(String.valueOf(num2)); //Last number pushed back on.
				}
				else {
					myStack.push(String.valueOf(SRPN.fixSaturation(BigInteger.valueOf((long) Math.pow(num1, num2)))));
				}
				break;
			case "%":
				myStack.push(String.valueOf(SRPN.fixSaturation(BigInteger.valueOf(num1 % num2))));
				break;	
		}
		
	}

	/**
	 * Accessor.
	 * @return Stack from Operation class
	 */
	public Stack<String> getMyStack() {
		return myStack;
	}

	/**
	 * Mutator.
	 * @param myStack
	 * 		Any stack you wish (likely SRPN stack)
	 */
	public void setMyStack(Stack<String> myStack) {
		this.myStack = myStack;
	}
	
}
