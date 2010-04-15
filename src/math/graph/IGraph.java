package math.graph;

import java.util.List;

import math.functions.Function;

public interface IGraph {

   public void add(Function f);

   public boolean remove(Function f);

   public List<Function> getFunctions();

   public void add(Plot p);

   public boolean remove(Plot p);

   public List<Plot> getPlots();

}
