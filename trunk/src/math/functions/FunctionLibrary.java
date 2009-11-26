package math.functions;


// BORED document: FuctionLibrary
public final class FunctionLibrary {

   private FunctionLibrary() {
   }


   public static Function exponent() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.exp(n);
         }
      };
   }

   public static Function natrualLog() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.log(n);
         }
      };
   }

   public static Function logBase10() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.log10(n);
         }
      };
   }

   public static Function logrithmic(final double base) {
      return natrualLog().divide(Math.log(base));
   }

   public static Function squareRoot() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.sqrt(n);
         }
      };
   }

   public static Function cubeRoot() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.cbrt(n);
         }
      };
   }

   public static Function ceiling() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.ceil(n);
         }
      };
   }

   public static Function floor() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.floor(n);
         }
      };
   }

   public static Function roundInt() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.rint(n);
         }
      };
   }

   public static Function powerBase(final double base) {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.pow(base, n);
         }
      };
   }

   public static Function powerExponent(final double exponent) {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.pow(n, exponent);
         }
      };
   }

   public static Function abs() {
      return new Function() {
         @Override
         public double f(double n) {
            return Math.abs(n);
         }
      };
   }

}
