package draw;

import java.util.List;

import math.functions.Function;

public interface IGraph {

	public void addFunc(Function f);

	public void removeFunc(Function f);

	public Function getFunc(int x);

	public List<Function> getFunctions();

}
