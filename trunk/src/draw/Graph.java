package draw;

import java.util.ArrayList;
import java.util.List;

import math.functions.Function;

// And why is this named like this? :-)
public class Graph implements IGraph {

	private ArrayList<Function> functions;

	public Graph() {
		functions = new ArrayList<Function>();
	}

	@Override
	public void add(Function f) {
		functions.add(f);
	}

	@Override
	public void remove(Function f) {
		functions.remove(f);
	}

	@Override
	public List<Function> getFunctions() {
		return functions;
	}

}
