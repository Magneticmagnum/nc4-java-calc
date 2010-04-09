package math.graph;

import java.util.List;

import math.functions.Function;

public interface IGraph<E extends Plotable> {

   public void add(Function f);

   public boolean remove(Function f);

   public List<Function> getFunctions();

   public void add(Plot<E> p);

   public boolean remove(Plot<E> p);

   public List<Plot<E>> getPlots();

}
