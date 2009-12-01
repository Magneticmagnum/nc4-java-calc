package draw;

import java.util.List;

import math.functions.Function;

public interface IGraph {

	public void add(Function f);

	public void remove(Function f);

	public List<Function> getFunctions();

}
