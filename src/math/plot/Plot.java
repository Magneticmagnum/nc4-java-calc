package math.plot;

import java.util.Collection;
import java.util.LinkedList;

public class Plot<E extends Plotable> {

   private LinkedList<E> data;

   public Plot() {
      data = new LinkedList<E>();
   }

   public Plot(Collection<E> c) {
      data = new LinkedList<E>(c);
   }

   public void add(E p) {
      data.add(p);
   }

   public void remove(E p) {
      data.remove(p);
   }

   public LinkedList<E> getData() {
      return data;
   }

}
