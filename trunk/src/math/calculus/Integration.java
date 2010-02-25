package math.calculus;

import math.functions.Function;

// BORED document: math.calculus.Integration
public final class Integration {

   private Integration() {
   }


   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 185
   public static final double sumLower(Function f, double a, double b, int n) {
      double h = (b - a) / n;
      double sum = 0.0;
      for (int i = n; i >= 1; i--)
         sum += f.f(a + i * h);
      return sum * h;
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 185
   public static final double sumUpper(Function f, double a, double b, int n) {
      return sumLower(f, a, b, n) + (b - a) * (f.f(a) - f.f(b)) / n;
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 191
   public static final double trapizoid(Function f, double a, double b, int n) {
      double h = (b - a) / n;
      double sum = 1.0 / 2.0 * (f.f(a) + f.f(b));
      for (int i = 1; i < n; i++)
         sum += f.f(a + i * h);
      return sum * h;
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 206
   public static final double romberg(Function f, double a, double b, int n) {
      double[] r = new double[n + 1];

      double h = b - a;
      r[0] = (h / 2.0) * (f.f(a) + f.f(b));
      for (int i = 1; i <= n; i++) {
         h = h / 2.0;
         double sum = 0.0;
         for (int k = 1; k <= Math.pow(2, i) - 1; k += 2)
            sum += f.f(a + k * h);
         r[i] = 1.0 / 2.0 * r[i - 1] + sum * h;
      }
      for (int i = 1; i <= n; i++)
         for (int j = n; j >= i; j--)
            r[j] = r[j] + (r[j] - r[j - 1]) / (Math.pow(4.0, i) - 1.0);

      return r[n];
   }

   public static final double simpson(Function f, double a, double b, double epsilon, int level_max) {
      return simpson(f, a, b, epsilon, 0, level_max);
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 224
   private static final double simpson(Function f, double a, double b, double epsilon, int level, int level_max) {
      level++;

      double h = b - a;
      double c = (a + b) / 2.0;
      double one_simp = h * (f.f(a) + 4.0 * f.f(c) + f.f(b)) / 6.0;

      double d = (a + c) / 2.0;
      double e = (c + b) / 2.0;
      double two_simp = h * (f.f(a) + 4.0 * f.f(d) + 2.0 * f.f(c) + 4.0 * f.f(e) + f.f(b)) / 12.0;

      if (level >= level_max) {
         // System.out.println("Maximum level reached");
         return two_simp;
      } else if (Math.abs(two_simp - one_simp) < 15.0 * epsilon) {
         return two_simp + (two_simp - one_simp) / 15.0;
      } else {
         return simpson(f, a, c, epsilon / 2.0, level, level_max) + simpson(f, c, b, epsilon / 2.0, level, level_max);
      }
   }
}
