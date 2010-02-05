package draw;

import java.util.LinkedList;
import java.util.List;

import math.functions.Function;
import math.plot.Plot;
import math.plot.Plotable;

public class Graph<E extends Plotable> implements IGraph<E> {

   private LinkedList<Function> functions;
   private LinkedList<Plot<E>>  plots;

   public Graph() {
      functions = new LinkedList<Function>();
      plots = new LinkedList<Plot<E>>();
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

   @Override
   public void add(Plot<E> p) {
      plots.add(p);
   }

   @Override
   public void remove(Plot<E> p) {
      plots.remove(p);
   }

   @Override
   public List<Plot<E>> getPlots() {
      return plots;
   }

}
