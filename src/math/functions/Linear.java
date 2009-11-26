package math.functions;


// BORED document: Linear
public abstract class Linear extends Function {

   // FORM: y = b
   public static final Function createConstant(final double b) {
      return new Linear() {
         @Override
         public double f(double x) {
            return b;
         }
      };
   }

   // FORM: y = m * x + b
   public static final Function createCommon(final double m, final double b) {
      return new Linear() {
         @Override
         public double f(double x) {
            return m * x + b;
         }
      };
   }

   // FORM: a * x + b * y + c = 0
   public static final Function createGeneral(final double a, final double b, final double c) {
      return new Linear() {
         @Override
         public double f(double x) {
            return -(a * x + c) / b;
         }
      };
   }

   // FORM: a * x + b * y = c
   public static final Function createStandard(final double a, final double b, final double c) {
      return new Linear() {
         @Override
         public double f(double x) {
            return (c - a * x) / b;
         }
      };
   }

   // FORM: y - y1 = m * (x - x1)
   public static final Function createPointSlope(final double m, final double x1, final double y1) {
      return new Linear() {
         @Override
         public double f(double x) {
            return m * (x - x1) + y1;
         }
      };
   }

   // FORM: x / c + y / b = 1
   public static final Function createIntercept(final double c, final double b) {
      return new Linear() {
         @Override
         public double f(double x) {
            return b * (1 - x / c);
         }
      };
   }

   // FORM: r * sin a = m * r * cos a + b
   public static final Function createPolar(final double m, final double b) {
      return new Linear() {
         @Override
         public double f(double theta) {
            return b / (Math.sin(theta) - m * Math.cos(theta));
         }
      };
   }

   // FORM: y * sin a + x * cos a - p = 0
   public static final Function createNormal(final double rho, final double phi) {
      return new Linear() {
         @Override
         public double f(double x) {
            return (rho - x * Math.cos(phi)) / Math.sin(phi);
         }
      };
   }

}
