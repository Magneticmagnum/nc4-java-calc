import math.Roots;
import math.functions.Function;
import math.functions.Quadratic;


public class TestRoots {

   /**
    * @param args
    */
   public static void main(String[] args) {
      Function f = Quadratic.createFactored(-1.0, 2.0, 3.0);

      double r1 = Roots.bisection(1.5, 2.5, f);
      double r2 = Roots.bisection(2.5, 3.5, f);

      System.out.println(r1 + " " + r2);
   }

}
