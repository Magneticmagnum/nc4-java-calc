package draw;

import java.util.ArrayList;

import math.functions.Function;

// And why is this named like this? :-)
public class Graph {

	private ArrayList<Function> function;
	private ArrayList<Function> funcPara;
	private ArrayList<Function> funcPolar;

	public Graph() {
		function = new ArrayList<Function>();
		funcPara = new ArrayList<Function>();
		funcPolar = new ArrayList<Function>();
	}

	public void addFunc(Function f) {
		function.add(f);
	}

	public void removeFunc(Function f) {
		function.remove(f);
	}

	public Function getFunc(int x) {
		return function.get(x);
	}

	public void addPara(Function f) {
		funcPara.add(f);
	}

	public void removePara(Function f) {
		funcPara.remove(f);
	}

	public Function getPara(int x) {
		return funcPara.get(x);
	}

	public void addPolar(Function f) {
		funcPolar.add(f);
	}

	public Function removePolar(int x) {
		return funcPolar.remove(x);
	}

	public Function getPolar(int f) {
		return funcPolar.get(f);
	}

}
