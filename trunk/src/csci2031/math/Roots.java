package csci2031.math;

import math.calculus.Differentiation;
import math.functions.Function;

public class Roots {

   public static final int DEFAULT_NMAX = 10000;

   public static final double DEFAULT_DELTA = 0.0;
   public static final double DEFAULT_EPSILON = 0.000000001;

   public static final double bisection(Function f, double a, double b) {
      return bisection(f, a, b, DEFAULT_NMAX, DEFAULT_EPSILON);
   }

   public static final double newton(Function f, double x) {
      return newton(f, Differentiation.derivative(f), x, DEFAULT_NMAX, DEFAULT_EPSILON, DEFAULT_DELTA);
   }

   public static final double secant(Function f, double a, double b) {
      return secant(f, a, b, DEFAULT_NMAX, DEFAULT_EPSILON);
   }

   public static final double bisection(Function f, double a, double b, int nmax, double epsilon) {
      double temp = Math.min(a, b);
      b = Math.max(a, b);
      a = temp;

      double f_a = f.f(a);
      double f_b = f.f(b);

      if (f_a * f_b < 0) {
         double c, f_c;
         double error = b - a;

         for (int n = 0; n < nmax && Math.abs(error) > epsilon; n++) {
            error = error / 2.0;
            c = a + error;
            f_c = f.f(c);
            if (f_a * f_c < 0) {
               b = c;
               f_b = f_c;
            } else {
               a = c;
               f_a = c;
            }
         }
         return a + error / 2.0;
      } else {
         System.out.println("Error - Function as same sign at a and b.");
         return a;
      }
   }

   public static final double newton(Function f, Function fp, double x, int nmax, double epsilon, double delta) {
      double f_x = f.f(x);
      double fp_x = fp.f(x);
      double d = f_x / fp_x;

      for (int n = 0; n < nmax && Math.abs(d) > epsilon && Math.abs(fp_x) >= delta; n++) {
         x -= d;
         f_x = f.f(x);
         fp_x = fp.f(x);
         d = f_x / fp_x;
      }
      return x;
   }

   public static final double secant(Function f, double a, double b, int nmax, double epsilon) {
      double fa = f.f(a);
      double fb = f.f(b);
      double d = (b - a) / (fb - fa) * fa;

      for (int n = 0; n < nmax && Math.abs(fa) > epsilon; n++) {
         b = a;
         fb = fa;

         a -= d;
         fa = f.f(a);

         d = (b - a) / (fb - fa) * fa;
      }
      return a;
   }

}
