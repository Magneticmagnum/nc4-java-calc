package draw;

import math.functions.Function;

public class GFuction extends Function implements Graphable {

	private Function function;

	public GFuction(Function function) {
		this.function = function;
	}

	@Override
	public double f(double n) {
		return function.f(n);
	}

	@Override
	public void graph(IGraph graph) {
		// TODO broken... not even sure this class is necessary... 

		// Rectangle2D rec = graph.getRectangle();
		// double dx = (rec.getMaxX() - rec.getMinX()) / 800.0;
		//
		// Vector prev = graph.getVector(rec.getMinX(),
		// function.f(rec.getMinX()));
		// for (double x = rec.getMinX() + dx; x <= rec.getMaxX() + dx; x += dx)
		// {
		// Vector next = graph.getVector(x, function.f(x));
		// if (!(Double.isInfinite(prev.get(1)) || Double.isNaN(prev.get(1))
		// || Double.isInfinite(next.get(1)) || Double.isNaN(next
		// .get(1))))
		// graph.drawLine(prev, next);
		// prev = next;
		// }
	}

}
