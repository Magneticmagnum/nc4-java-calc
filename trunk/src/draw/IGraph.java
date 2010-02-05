package draw;

import java.util.List;

import math.functions.Function;
import math.plot.Plot;
import math.plot.Plotable;

public interface IGraph<E extends Plotable> {

   public void add(Function f);

   public void remove(Function f);

   public List<Function> getFunctions();

   public void add(Plot<E> p);

   public void remove(Plot<E> p);

   public List<Plot<E>> getPlots();

}
