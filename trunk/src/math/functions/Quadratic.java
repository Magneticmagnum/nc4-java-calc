package math.functions;


//BORED document: Quadratic
public abstract class Quadratic extends Function {

   // FORM: y = a * x^2 + b * x + c
   public static final Function createPolynomial(final double a, final double b, final double c) {
      return new Quadratic() {
         @Override
         public double f(double x) {
            return a * x * x + b * x + c;
         }
      };
   }

   // FROM: y = a * (x - h)^2 + k
   public static final Function createStandard(final double a, final double h, final double k) {
      return new Quadratic() {
         @Override
         public double f(double x) {
            return a * (x - h) * (x - h) + k;
         }
      };
   }

   // FORM: y = a * (x - r1) * (x - r2)
   public static final Function createFactored(final double a, final double r1, final double r2) {
      return new Quadratic() {
         @Override
         public double f(double x) {
            return a * (x - r1) * (x - r2);
         }
      };
   }

}
