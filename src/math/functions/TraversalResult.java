package math.functions;

/*
 * Joe Houlton
 * 
 * This allows us to act on each node we traverse by defining
 * the function traverseAction when you pass it to the traverse
 * function in FunctionNode.
 */
public abstract class TraversalResult {
	public abstract void traverseAction(FunctionNode n);
}
