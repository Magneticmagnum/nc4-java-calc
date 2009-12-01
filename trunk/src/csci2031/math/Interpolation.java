package csci2031.math;

import java.util.List;

import math.functions.Function;
import csci2031.graph.UserPoints;
import draw.Vector2D;

public class Interpolation {

   // pg 155
   public static final double[] chebyshevNodes(double a, double b, int n) {
      double[] nodes = new double[n];
      for (int i = 0; i < n; i++)
         nodes[i] = (a + b) / 2.0 + (b - a) / 2.0 * Math.cos((2.0 * i + 1.0) / (2.0 * n) * Math.PI);
      return nodes;
   }

   public static final double[] coef(double[] x, double[] y) {
      double[] a = y.clone();
      for (int j = 1; j < a.length; j++)
         for (int i = a.length - 1; i >= j; i--)
            a[i] = (a[i] - a[i - 1]) / (x[i] - x[i - j]);
      return a;
   }

   public static final double[] coef(Function f, double[] x) {
      double[] y = new double[x.length];
      for (int i = 0; i < y.length; i++)
         y[i] = f.f(x[i]);
      return coef(x, y);
   }

   public static final double[] coef(Function f, double a, double b, int n) {
      return coef(f, chebyshevNodes(a, b, n));
   }

   public static final double[] coef(Vector2D[] points) {
      double[] a = new double[points.length];
      for (int i = 0; i < a.length; i++)
         a[i] = points[i].getY();
      for (int j = 1; j < a.length; j++)
         for (int i = a.length - 1; i >= j; i--)
            a[i] = (a[i] - a[i - 1]) / (points[i].getX() - points[i - j].getX());
      return a;
   }

   public static final double[] coef(List<Vector2D> points) {
      return coef(points.toArray(new Vector2D[points.size()]));
   }

   public static final double eval(double t, double[] x, double[] a) {
      double temp = Double.NaN;
      if (a.length != 0 && a.length == x.length) {
         temp = a[a.length - 1];
         for (int i = a.length - 2; i >= 0; i--)
            temp = temp * (t - x[i]) + a[i];
      }
      return temp;
   }

   public static final Function polyInterpolate(final double[] a, final double[] x) {
      return new Function() {
         @Override
         public double f(final double t) {
            return eval(t, x, a);
         }
      };
   }

   public static final Function polyInterpolate(final Function f, final double[] x) {
      return polyInterpolate(coef(f, x), x);
   }

   public static final Function polyInterpolate(final Function f, final double start, final double end, final int n) {
      return polyInterpolate(f, chebyshevNodes(start, end, n));
   }

   // public static final Function polyInterpolate(final double[] x, final double[] y) {
   // Function f = new Function() {
   // private double[] a = coef(x, y);
   //
   // public double f(final double t) {
   // return eval(t, x, a);
   // }
   // };
   // return f;
   // }

   public static final Function polyInterpolate(final Vector2D[] points) {
      final double[] x = new double[points.length];
      for (int i = 0; i < points.length; i++)
         x[i] = points[i].getX();
      return polyInterpolate(coef(points), x);
   }

   public static final Function polyInterpolate(final List<Vector2D> points) {
      return polyInterpolate(points.toArray(new Vector2D[points.size()]));
   }

   public static final Function polyInterpolate(final UserPoints points) {
      return new Function() {
         private Function f = polyInterpolate(points.get());
         private long ptRev = points.getRevision();

         @Override
         public double f(double x) {
            if (ptRev != points.getRevision()) {
               f = polyInterpolate(points.get());
               ptRev = points.getRevision();
            }
            return f.f(x);
         }
      };
   }

}
