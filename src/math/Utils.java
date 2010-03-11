package math;

// BORED document class
public class Utils {

   private Utils() {
   }


   /**
    * The <code>double</code> value of one-third.
    */
   public static final double ONE_THIRD  = (1.0D / 3.0D);

   /**
    * The <code>double</code> value of two-thirds.
    */
   public static final double TWO_THIRDS = (2.0D / 3.0D);

   /**
    * Returns the square of the <code>double</code>. That is, the double multiplied be it's self once.
    * 
    * @param n
    *           the number whose square is to be calculated.
    * @return the square of the argument.
    */
   public static final double sqr(final double n) {
      return n * n;
   }

   /**
    * Returns the cube of a <code>double</code>. That is, the double multiplied be itself three times.
    * 
    * @param n
    *           the number whose cube is to be calculated.
    * @return the cube of the argument.
    */
   public static final double cube(final double n) {
      return n * n * n;
   }

   public static final int sign(final double n) {
      return (n < 0 ? -1 : 1);
   }

   public static final double pow(double a, int n) {
      double t = 1;
      for (int i = Math.abs(n); i > 0; i--)
         t *= a;
      if (n < 0)
         t = 1.0 / t;
      return t;
   }

   public static final double pow(int a, int n) {
      if (n < 0)
         throw new ArithmeticException("Cannot raise an integer to a negative number.");
      int t = 1;
      for (int i = Math.abs(n); i > 0; i--)
         t *= a;
      return t;
   }

   public static final int factorial(int n) {
      if (n < 0)
         throw new ArithmeticException("Cannot take the factorial of a negative number.");
      int t = 1;
      for (int i = n; i > 0; i--)
         t *= i;
      return t;
   }

   public static final int choose(int i, int j) {
      return compination(i, j);
   }

   public static final int permutation(int n, int r) {
      if (n < 0 || r < 0)
         throw new ArithmeticException("Can not take the factorial of a negative number.");
      int t = 1;
      for (int i = n; i > n - r; i--)
         t *= i;
      return t;
   }

   public static final int compination(int n, int r) {
      return permutation(n, r) / factorial(r);
   }

}
