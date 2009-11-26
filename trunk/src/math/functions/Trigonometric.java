package math.functions;


//BORED document: Trigonometric
public abstract class Trigonometric extends Function {

   // FORM: A * sin(omega * t + theta)
   public static Function createSinusoid(final double A, final double omega, final double theta) {
      return new Trigonometric() {
         @Override
         public double f(double t) {
            return A * Math.sin(omega * t + theta);
         }
      };
   }


   public static Function sine() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.sin(x);
         }
      };
   }

   public static Function cosine() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.cos(x);
         }
      };
   }

   public static Function tangent() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.tan(x);
         }
      };
   }

   public static Function cosecant() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return 1.0 / Math.sin(x);
         }
      };
   }

   public static Function secant() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return 1.0 / Math.cos(x);
         }
      };
   }

   public static Function cotangent() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return 1.0 / Math.tan(x);
         }
      };
   }

   public static Function arcSine() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.asin(x);
         }
      };
   }

   public static Function arcCosine() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.acos(x);
         }
      };
   }

   public static Function arcTangent() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.atan(x);
         }
      };
   }

   public static Function arcCosecant() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.asin(1.0 / x);
         }
      };
   }

   public static Function arcSecant() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.acos(1.0 / x);
         }
      };
   }

   public static Function arcCotangent() {
      return new Trigonometric() {
         @Override
         public double f(double x) {
            return Math.atan(1.0 / x);
         }
      };
   }

}
