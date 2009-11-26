package csci2031.math;

import java.util.List;

import math.functions.Function;
import csci2031.graph.UserPoints;
import csci2031.graph.Vector2D;

public class Spline {

   // pg 374
   public static final double spline1(double[] x, double[] y, double t) {
      if (x.length > 1) {
         Utils.sort(x, y);
         int i;
         for (i = x.length - 2; i > 0 && (t - x[i]) < 0; i--)
            ;
         return y[i] + (t - x[i]) * (y[i + 1] - y[i]) / (x[i + 1] - x[i]);
      } else if (y.length == 1)
         return y[0];
      return Double.NaN;
   }

   public static final double spline1(Vector2D[] points, double t) {
      if (points.length > 1) {
         Utils.sort(points);
         int i;
         for (i = points.length - 2; i > 0 && (t - points[i].getX()) < 0; i--)
            ;
         return points[i].getY() + (t - points[i].getX()) * (points[i + 1].getY() - points[i].getY())
               / (points[i + 1].getX() - points[i].getX());
      } else if (points.length == 1)
         return points[0].getY();
      return Double.NaN;
   }

   public static final double spline1(List<Vector2D> points, double t) {
      return spline1(points.toArray(new Vector2D[points.size()]), t);
   }

   public static final Function spline1(final double[] x, final double[] y) {
      return new Function() {
         @Override
         public double f(double t) {
            return spline1(x, y, t);
         }
      };
   }

   public static final Function spline1(final Vector2D[] points) {
      return new Function() {
         @Override
         public double f(double x) {
            return spline1(points, x);
         }
      };
   }

   public static final Function spline1(List<Vector2D> points) {
      return spline1(points.toArray(new Vector2D[points.size()]));
   }

   public static final Function spline1(final UserPoints points) {
      return new Function() {
         private Function f = spline1(points.get());
         private long ptRev = points.getRevision();

         @Override
         public double f(double x) {
            if (ptRev != points.getRevision()) {
               f = spline1(points.get());
               ptRev = points.getRevision();
            }
            return f.f(x);
         }
      };
   }

   // pg 392
   public static final double[] spline3_coef(double[] x, double[] y) {
      double[] z = {
            0, 0
      };
      if (x.length > 2) {
         Utils.sort(x, y);
         int n = x.length - 1;
         z = new double[x.length];
         double[] h = new double[x.length - 1];
         double[] b = new double[x.length - 1];
         double[] u = new double[x.length - 1];
         double[] v = new double[x.length - 1];

         for (int i = 0; i <= n - 1; i++) {
            h[i] = x[i + 1] - x[i];
            b[i] = (y[i + 1] - y[i]) / h[i];
         }

         u[1] = 2.0 * (h[1] + h[0]);
         v[1] = 6.0 * (b[1] - b[0]);
         for (int i = 2; i <= n - 1; i++) {
            u[i] = 2.0 * (h[i] + h[i - 1]) - Utils.sqr(h[i - 1]) / u[i - 1];
            v[i] = 6.0 * (b[i] - b[i - 1]) - h[i - 1] * v[i - 1] / u[i - 1];
         }

         z[n] = 0;
         for (int i = n - 1; i >= 1; i--)
            z[i] = (v[i] - h[i] * z[i + 1]) / u[i];
         z[0] = 0;
      }
      return z;
   }

   public static final double[] spline3_coef(Vector2D[] points) {
      double[] z = {
            0, 0
      };
      if (points.length > 2) {
         Utils.sort(points);
         int n = points.length - 1;
         z = new double[points.length];
         double[] h = new double[points.length - 1];
         double[] b = new double[points.length - 1];
         double[] u = new double[points.length - 1];
         double[] v = new double[points.length - 1];

         for (int i = 0; i <= n - 1; i++) {
            h[i] = points[i + 1].getX() - points[i].getX();
            b[i] = (points[i + 1].getY() - points[i].getY()) / h[i];
         }

         u[1] = 2.0 * (h[1] + h[0]);
         v[1] = 6.0 * (b[1] - b[0]);
         for (int i = 2; i <= n - 1; i++) {
            u[i] = 2.0 * (h[i] + h[i - 1]) - Utils.sqr(h[i - 1]) / u[i - 1];
            v[i] = 6.0 * (b[i] - b[i - 1]) - h[i - 1] * v[i - 1] / u[i - 1];
         }

         z[n] = 0;
         for (int i = n - 1; i >= 1; i--)
            z[i] = (v[i] - h[i] * z[i + 1]) / u[i];
         z[0] = 0;

      }
      return z;
   }

   public static final double[] spline3_coef(List<Vector2D> points) {
      return spline3_coef(points.toArray(new Vector2D[points.size()]));
   }

   // pg 392 - 393
   public static final double spline3_eval(double[] x, double[] y, double[] z, double t) {
      if (x.length > 1) {
         Utils.sort(x, y);
         int i;
         for (i = x.length - 2; i > 0 && (t - x[i]) < 0; i--)
            ;
         double h = x[i + 1] - x[i];
         double temp = (z[i] / 2.0) + (t - x[i]) * (z[i + 1] - z[i]) / (6.0 * h);
         temp = -(h / 6.0) * (z[i + 1] + 2 * z[i]) + (y[i + 1] - y[i]) / h + (t - x[i]) * temp;
         return y[i] + (t - x[i]) * temp;
      } else if (x.length == 1)
         return y[0];
      return Double.NaN;
   }

   public static final double spline3_eval(Vector2D[] points, double[] z, double t) {
      if (points.length > 1) {
         Utils.sort(points);
         int i;
         for (i = points.length - 2; i > 0 && t - points[i].getX() < 0.0; i--)
            ;
         double h = points[i + 1].getX() - points[i].getX();
         double temp = (z[i] / 2.0) + (t - points[i].getX()) * (z[i + 1] - z[i]) / (6.0 * h);
         temp = -(h / 6.0) * (z[i + 1] + 2 * z[i]) + (points[i + 1].getY() - points[i].getY()) / h
               + (t - points[i].getX()) * temp;
         return points[i].getY() + (t - points[i].getX()) * temp;
      } else if (points.length == 1)
         return points[0].getY();
      return Double.NaN;
   }

   public static final Function spline3(final double[] x, final double[] y) {
      return new Function() {
         double[] z = spline3_coef(x, y);

         @Override
         public double f(double t) {
            return spline3_eval(x, y, z, t);
         }
      };
   }

   public static final Function spline3(final Vector2D[] points) {
      return new Function() {
         double[] z = spline3_coef(points);

         @Override
         public double f(double t) {
            return spline3_eval(points, z, t);
         }
      };
   }

   public static final Function spline3(final List<Vector2D> points) {
      return spline3(points.toArray(new Vector2D[points.size()]));
   }

   public static final Function spline3(final UserPoints points) {
      return new Function() {
         private Function f = spline3(points.get());
         private long ptRev = points.getRevision();

         @Override
         public double f(double x) {
            if (ptRev != points.getRevision()) {
               f = spline3(points.get());
               ptRev = points.getRevision();
            }
            return f.f(x);
         }
      };
   }

   public static final double bspline_coef(double[] x, double[] y, double[] a, double[] h) {
      return 0;
   }

}
