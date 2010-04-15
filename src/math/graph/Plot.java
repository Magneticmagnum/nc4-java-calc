package math.graph;

import java.util.Collection;
import java.util.LinkedList;

public class Plot {

   private LinkedList<Plotable> data;

   public Plot() {
      data = new LinkedList<Plotable>();
   }

   public <E extends Plotable> Plot(Collection<E> c) {
      data = new LinkedList<Plotable>(c);
   }

   public void add(Plotable p) {
      data.add(p);
   }

   public void remove(Plotable p) {
      data.remove(p);
   }

   public LinkedList<Plotable> getData() {
      return data;
   }

}
