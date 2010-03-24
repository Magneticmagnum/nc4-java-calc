/**
 * 
 */
package math.functions;
import java.lang.IndexOutOfBoundsException;
/**
 * @author Joe Houlton
 *
 */
public class FunctionNode {
	private FunctionNode parent;
	private FunctionNode [] children;
	private final int MAX_CHILDREN = 32;
	private int numChildren;
	private FunctionSymbol f;
	
	public FunctionNode(FunctionSymbol f)
	{
		this(f,null);
	}
	public FunctionNode(FunctionSymbol f, FunctionNode parent)
	{
		this.f = f;
		this.parent = parent;
		children = new FunctionNode[MAX_CHILDREN];
		numChildren = 0;
	}
	public FunctionNode() {
		// TODO Auto-generated constructor stubs
		children = new FunctionNode[MAX_CHILDREN];
		numChildren = 0;
		parent = this;
	}
	public FunctionNode getParent()
	{
		return this.parent;
	}
	public FunctionSymbol getFunc()
	{
		return this.f;
	}
	public int addChild(FunctionNode child)
	{
		if (numChildren >= MAX_CHILDREN)
			throw new IndexOutOfBoundsException("Child stack exceeded!");
		children[numChildren] = child;
		child.setParent(this);
		return ++numChildren;
	}
	public FunctionNode getChild(int num)
	{
		if (num > numChildren)
			return null;
		return children[num];
	}
	public FunctionNode getSibling(int num)
	{
		if (parent == null)
			return null;
		return parent.getChild(num);
	}
	public boolean isRoot()
	{
		return numChildren == 0;
	}
	public void setParent(FunctionNode n)
	{
		parent = n;
	}
	public static void traverse(FunctionNode node, TraversalResult res)
	{
		int c = 0;
		for(node = node.getChild(c); node != null; node = node.getSibling(++c))
		{
			res.traverseAction(node);
			traverse(node, res);
		}
	}
	public static FunctionNode toParent(FunctionNode node)
	{
		while(node.getParent() != null)
			node = node.getParent();
		return node;
	}
}
