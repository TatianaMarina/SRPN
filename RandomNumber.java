import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  Random number class for generating random numbers within the range of an integer.
 * 
 *  @version 1.2
 *  @release 26/11/2017
 *  @copyright Copyright (C) 2017 Ed Jeffery - All Rights Reserved
 */
public class RandomNumber {
	
	Stack<String> myStack = new Stack<String>();
	int rCount = 0; //Stores how many times a number has been accessed from randomNumbers array
	String[] randomNumbers = {"1804289383", "846930886", "1681692777", "1714636915", "1957747793", "424238335",
			"719885386", "1649760492", "596516649", "1189641421", "1025202362", "1350490027", "783368690",
			"1102520059", "2044897763", "1967513926", "1365180540", "1540383426", "304089172", "1303455736",
			"35005211", "521595368", "294702567", "1726956429", "336465782", "861021530", "278722862", "233665123",
			"2145174067", "468703135", "1101513929", "1801979802", "1315634022", "635723058", "1369133069", "1125898167",
			"1059961393", "2089018456", "628175011", "1656478042", "1131176229", "1653377373", "859484421", "1914544919",
			"608413784", "756898537", "1734575198", "1973594324", "149798315", "2038664370", "1129566413", "184803526",
			"412776091", "1424268980", "1911759956", "749241873", "137806862", "42999170", "982906996", "135497281"};
	
	/**
	 * Constructor. Sets stack equal to stack provided as argument.
	 * @param myStack
	 * 		Stack from the SRPN class.
	 */
	public RandomNumber(Stack<String> myStack) {
		
		this.myStack = myStack;
		
	}
	
	/**
	 * Method. Pushes random number to the stack.
	 */
	public void pushRandomNumToStack() {
		
		if (rCount < randomNumbers.length) {
			myStack.push(randomNumbers[rCount]);
			rCount++;
		}
		else {
			myStack.push(String.valueOf(randInt()));
		}
		
	}
	
	/**
	 * Method. Generates a random integer between the min. and max. of an integer.
	 * @return Random integer
	 */
	public static int randInt() {
		//Source for random number generator: https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
		//Credit given to: Greg Case
		
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int randomInt = (int) ThreadLocalRandom.current().nextLong( (long) Integer.MIN_VALUE, (long) Integer.MAX_VALUE + 1);
		
		return randomInt;
	
	}
	
	/**
	 * Accessor.
	 * @return Stack from RandomNumber class
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
	
	/**
	 * Accessor. 
	 * @return Count of how many times a number has been accessed from randomNumbers array
	 */
	public int getrCount() {
		return rCount;
	}
	
	/**
	 * Mutator.
	 * @param rCount
	 * 		Count of how many times a number has been accessed from randomNumbers array
	 */
	public void setrCount(int rCount) {
		this.rCount = rCount;
	}
	
}
