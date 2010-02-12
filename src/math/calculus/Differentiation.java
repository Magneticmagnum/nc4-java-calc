package math.calculus;

import math.functions.Function;

// BORED document: math.calculus.Differentiation
public final class Differentiation {

   private Differentiation() {
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Pages 172-173
   public static double derivative(Function f, double x) {
      double h = Math.abs(x / 1000.0);
      h += (h == 0.0 ? 0.0 : 0.0001);
      double xph = f.f(x + h);
      double xmh = f.f(x - h);
      return 1.0 / (2.0 * h) * (xph - xmh) - 1.0 / (12.0 * h)
            * (f.f(x + 2.0 * h) - 2.0 * xph + 2.0 * xmh - f.f(x - 2.0 * h));
   }

   public static Function derivative(final Function f) {
      return new Function() {
         @Override
         public double f(double x) {
            return derivative(f, x);
         }
      };
   }

   public static Function prime(Function f) {
      return derivative(f);
   }


   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 170
   public static double rechardsonExtrapolation(Function f, double x, int n) {
      double[] d = new double[n + 1];
      double h = Math.abs(x / 1000.0);
      h += (h == 0.0 ? 0.0 : 0.001);

      for (int i = 0; i <= n; i++) {
         d[i] = (f.f(x + h) - f.f(x - h)) / (2.0 * h);
         h /= 2.0;
      }
      for (int i = 1; i <= n; i++)
         for (int j = n; j >= i; j--)
            d[j] = d[j] + (d[j] - d[j - 1]) / (Math.pow(4.0, i) - 1.0);
      return d[n];
   }

   public static Function rechardsonExtrapolation(final Function f, final int n) {
      return new Function() {
         @Override
         public double f(double x) {
            return rechardsonExtrapolation(f, x, n);
         }
      };
   }


   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 173
   public static double secondDerivative(Function f, double x) {
      double h = Math.abs(x / 1000.0);
      h += (h == 0.0 ? 0.0 : 0.0001);
      return 1.0 / (h * h) * (f.f(x + h) - 2.0 * f.f(x) + f.f(x - h));
   }

   public static Function secondDerivative(final Function f) {
      return new Function() {
         @Override
         public double f(double x) {
            return secondDerivative(f, x);
         }
      };
   }

   public static Function doublePrime(Function f) {
      return secondDerivative(f);
   }

}
