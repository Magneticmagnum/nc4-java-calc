/**
 * 
 */
package math.functions;
import java.util.ArrayDeque;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.HashMap;
/**
 * @author Joe Houlton
 *
 * TODO  USE HASHMAPS with a table generated when VM starts up
 */
public class ExpressionParsing {

	private static HashMap <String, Function> functionMap = new HashMap <String, Function> ();

	static {
		functionMap.put("sin", 		Trigonometric.sine());
		functionMap.put("cos", 		Trigonometric.cosine());
		functionMap.put("tan", 		Trigonometric.tangent());
		functionMap.put("asin",		Trigonometric.arcSine());
		functionMap.put("acos", 	Trigonometric.arcCosine());
		functionMap.put("atan",		Trigonometric.arcTangent());
		functionMap.put("cot", 		Trigonometric.cotangent());
		functionMap.put("csc", 		Trigonometric.cosecant());
		functionMap.put("sec", 		Trigonometric.secant());
		functionMap.put("acosec", 	Trigonometric.arcCosecant());
		functionMap.put("asec", 	Trigonometric.arcSecant());
		functionMap.put("acot", 	Trigonometric.arcCotangent());
		functionMap.put("ln",		FunctionLibrary.natrualLog());
		functionMap.put("log", 		FunctionLibrary.logBase10());
		functionMap.put("abs", 		FunctionLibrary.abs());
		functionMap.put("ceil", 	FunctionLibrary.ceiling());
		functionMap.put("cbrt", 	FunctionLibrary.cubeRoot());
		functionMap.put("exp", 		FunctionLibrary.exponent());
		functionMap.put("floor",	FunctionLibrary.floor());
		functionMap.put("sqrt",		FunctionLibrary.squareRoot());
		functionMap.put("addident", FunctionIdentities.AdditionIdentity());
		functionMap.put("multident",FunctionIdentities.MultiplicationIdentity());
	}
	/* 
	 * Test driver function to try out some things...
	 * Probably don't work yet
	 */
	public static void main(String [] args) {
		String [] vars = new String[] { "x", "y" };
		Stack <ExpressionToken> operands = new Stack <ExpressionToken>();
		operands.push(new ExpressionToken("x"));
		operands.push(new ExpressionToken("2"));
		ArrayDeque <Function> functions = new ArrayDeque <Function>();
		Function plus = createFunction(new ExpressionToken("+"), operands, vars);
		functions.push(plus);
		Function sin = createFunction(new ExpressionToken("sin"), functions);
		if (functions != null) {
			System.out.println("Created function f " + sin.toString());
			System.out.println("Evaluating f(g(x))" + sin.f(5));
		}
		string2Expression("x+2*cos(x)","x");
	}
	/*
	 * Return a function with the corresponding expression given in string form
	 * take note of variables passed along with it
	 */
	public static Function string2Expression(String st, String variables) {
		// TODO Return a function that represents a parsed string expression
		ArrayDeque <ExpressionToken> infix = new ArrayDeque <ExpressionToken>();
		String [] vararray = variables.split(",");
		StringTokenizing tokenizer = new StringTokenizing(st);
		ExpressionToken exp;
		while ((exp = tokenizer.nextToken()) != null) {
			/*if (exp.isNumber() || arrContains(exp.toString(), vararray))
				operands.push(exp);
			else
				operators.push(exp);
				*/
			infix.add(exp);	
		}
		infix = infix2Postfix(infix, vararray);
		System.out.println( infix.toString() );
				
		return null;
	}
	/* This function actually works, takes an array of expression tokens
	 * and parses them to an infix expression
	 */
	public static ArrayDeque <ExpressionToken> infix2Postfix(ArrayDeque <ExpressionToken> input, String [] vararray) {
		ArrayDeque <ExpressionToken> inf = new ArrayDeque <ExpressionToken>();
		Stack <ExpressionToken> stack = new Stack <ExpressionToken>();
		while (!input.isEmpty()) {
			ExpressionToken e = input.remove();
			if (e.isNumber() || arrContains(e.toString(), vararray)) {
				inf.add(e);
			}
			else if (e.toString().equals("(")) {
				stack.push(e);
			}
			/* Glitchy so far, see 
			 * http://en.wikipedia.org/wiki/Shunting-yard_algorithm
			 * 
			 * for interpretation.
			 */
			else if (e.toString().equals(")")) {
				while (!stack.isEmpty() && !stack.peek().toString().equals("(")) {
					inf.add( stack.pop() );
					if (!stack.isEmpty())
						stack.pop();
					if (!stack.isEmpty() && functionMap.containsKey( stack.peek().toString() ))
						inf.add( stack.pop() );
				}
			}
			else {
				if (functionMap.containsKey( e.toString()))
					stack.push(e);
				else if (stack.isEmpty()) {
					stack.push(e);
				}
				else {
					while (!stack.isEmpty() && stack.peek().operatorPrecedence() >= e.operatorPrecedence() && !stack.peek().toString().equals("(")) {
						inf.add(stack.pop());
					}
					stack.push(e);
				}
			}
		}
		while (!stack.isEmpty()) {
				inf.add( stack.pop() );
		}
		return inf;
	}
	// Creates a function from its node and leafs
	public static Function createFunction(ExpressionToken operator,  ArrayDeque <Function> fs)
	{
		Function addident = functionMap.get("addident");
		Function multident = functionMap.get("multident");
		Function other = null;
		final int MULTIPLY = 2;
		final int ADD = 1;
		int identity = 0;
		String sop = operator.toString();
		while (!fs.isEmpty())
		{
			Function n = fs.remove();
			if (sop.equals("+"))
			{
				identity = ADD;
				addident = addident.add(n);
			}
			else if (sop.equals("-"))
			{
				identity = ADD;
				addident = addident.subtract(n);
			}
			else if (sop.equals("*"))
			{
				multident = multident.multiply(n);
				identity = MULTIPLY;
			}
			else if (sop.equals("/"))
			{
				multident = multident.divide(n);
				identity = MULTIPLY;
			}
			else if ( (other = functionMap.get(sop)) != null)
			{
				other = other.composite(n);
			}
		}
		if (identity == ADD)
			return addident;
		else if (identity == MULTIPLY)
			return multident;
		else
			return other;
	}
	/* Create a single function using a given operator and
	 * a stack of expression operands.  Used to start out functions
	 * on the leaf nodes.
	 * ex.  1+2+3+4 ->  createFunction(+, [1,2,3,4], "")
	 * returns a function with f(double a) { return 1+2+3+4; } essentially
	 */
	public static Function createFunction(ExpressionToken operator, Stack <ExpressionToken> operands, String [] variables) {
		Function n = null;
		ArrayDeque <Function> arrf = new ArrayDeque <Function>();
		while(!operands.isEmpty()) {
			ExpressionToken e = operands.pop();
			// it is a constant
			if (e.isNumber()) {
				n = Linear.createConstant(e.numValue());
			}
			// It is a variable
			else if (arrContains(e.toString(), variables)) {
				n = new Function() {
					public double f(double a) {
						return a; 
						}
					};
			}
			else {
				// Uh oh
				System.out.println("Error parsing function: "+ e + " not recognized.");
				return null;
			}
			arrf.add(n);
		}
		return createFunction( operator, arrf );
		
	}
	private static boolean arrContains(String test, String [] arr) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i].equals(test))
				return true;
		return false;
	}
}
class StringTokenizing
{
	private StringTokenizer tokenizer;
	public StringTokenizing(String st) {
		tokenizer = new StringTokenizer(st, "+-*()/^", true);
	}
	public ExpressionToken nextToken() {
		if (tokenizer.hasMoreTokens())
			return new ExpressionToken(tokenizer.nextToken());
		else
			return null;
	}
}
class ExpressionToken
{
	private String myString;
	public ExpressionToken(String st) { 
		myString = st; 
	}
	public boolean isNumber() {
		try { 
			Double.parseDouble(myString); 
		}
		catch (NumberFormatException e) { 
			return false;
		}
		try { 
			Integer.parseInt(myString);
		}
		catch (NumberFormatException e) { 
			return false;
		}
		return true;
	}
	public double numValue() {
		return Double.parseDouble(myString);
	}
	public String toString() { 
		return myString;
	}
	public int operatorPrecedence() {
		if (myString.equals(";"))
			return 0;
		if (myString.equals("="))
			return 1;
		if (myString.equals("?"))
			return 2;
		if (myString.equals("=="))
			return 4;
		if (myString.equals("+") || myString.equals("-"))
			return 5;
		if (myString.equals("*") || myString.equals("/"))
			return 6;
		if (myString.equals("!"))
			return 7;
		return 0;
	}
}