package math.graph;

import java.util.LinkedList;
import java.util.List;

import math.functions.Function;

public class Graph implements IGraph {

   private LinkedList<Function> functions;
   private LinkedList<Plot>     plots;

   public Graph() {
      functions = new LinkedList<Function>();
      plots = new LinkedList<Plot>();
   }

   @Override
   public void add(Function f) {
      functions.add(f);
   }

   @Override
   public boolean remove(Function f) {
      return functions.remove(f);
   }

   @Override
   public List<Function> getFunctions() {
      return functions;
   }

   @Override
   public void add(Plot p) {
      plots.add(p);
   }

   @Override
   public boolean remove(Plot p) {
      return plots.remove(p);
   }

   @Override
   public List<Plot> getPlots() {
      return plots;
   }

}
