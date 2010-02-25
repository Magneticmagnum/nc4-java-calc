package math.calculus;

import math.matrices.Matrix;
import math.matrices.MatrixDimensionException;
import math.matrices.Vector;
import csci2031.math.Utils;

// BORED document: math.calculus.LinearAlgebra
public final class LinearAlgebra {

   private LinearAlgebra() {
   }


   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 252
   public static final Vector naiveGaussianElimination(Matrix a, Vector b) {
      if (a == null || b == null)
         throw new NullPointerException("Null Pointer Exception: can not compute naive gaussian elimination on a null "
               + "matrix.");
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not "
               + "perform gaussian elimination on a " + a.getRows() + "x" + a.getColumns() + " matrix.");
      if (a.getColumns() != b.getLength())
         throw new MatrixDimensionException("Matrix Dimension Exception: vector must be the same length as the matrix.");

      int n = b.getLength() - 1;
      Vector x = new Vector(b.getLength());
      if (n >= 0) {
         for (int k = 0; k <= n - 1; k++) {
            for (int i = k + 1; i <= n; i++) {
               double xmult = a.get(i, k) / a.get(k, k);
               a.set(i, k, xmult);
               for (int j = k + 1; j <= n; j++) {
                  a.set(i, j, a.get(i, j) - xmult * a.get(k, j));
               }
               b.set(i, b.get(i) - xmult * b.get(k));
            }
         }
         x.set(n, b.get(n) / a.get(n, n));
         for (int i = n - 1; i >= 0; i--) {
            double sum = b.get(i);
            for (int j = i + 1; j <= n; j++) {
               sum -= a.get(i, j) * x.get(j);
            }
            x.set(i, sum / a.get(i, i));
         }
      }
      return x;
   }


   public static final Vector gaussianElimination(Matrix a, Vector b) {
      a = a.copy();
      return gaussianElimination(a, b, pivotPositions(a));
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 269
   private static final Vector gaussianElimination(Matrix a, Vector b, int[] l) {
      if (a == null || b == null)
         throw new NullPointerException("Null Pointer Exception: can not compute naive gaussian elimination on a null "
               + "matrix.");
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not "
               + "perform gaussian elimination on a " + a.getRows() + "x" + a.getColumns() + " matrix.");
      if (a.getColumns() != b.getLength())
         throw new MatrixDimensionException("Matrix Dimension Exception: vector must be the same length as the matrix.");
      if (a.getColumns() != l.length)
         throw new MatrixDimensionException("Matrix Dimension Exception: pivot position array must be the same length "
               + "as the matrix.");

      int n = b.getLength() - 1;
      Vector x = new Vector(b.getLength());
      if (n >= 0) {
         for (int k = 0; k <= n - 1; k++) {
            for (int i = k + 1; i <= n; i++) {
               // {// if we copy matrix a in pivotPositions(Matrix), we need this:
               // double xmult = a.get(l[i], k) / a.get(l[k], k);
               // a.set(l[i], k, xmult);
               // for (int j = k + 1; j <= n; j++) {
               // a.set(l[i], j, a.get(l[i], j) - xmult * a.get(l[k], j));
               // }
               // }

               b.set(l[i], b.get(l[i]) - a.get(l[i], k) * b.get(l[k]));
            }
         }
         x.set(n, b.get(l[n]) / a.get(l[n], n));
         for (int i = n - 1; i >= 0; i--) {
            double sum = b.get(l[i]);
            for (int j = i + 1; j <= n; j++) {
               sum -= a.get(l[i], j) * x.get(j);
            }
            x.set(i, sum / a.get(l[i], i));
         }
      }
      return x;
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 267
   private static final int[] pivotPositions(Matrix a) {
      if (a == null)
         throw new NullPointerException("Null Pointer Exception: can not compute naive gaussian elimination on a null "
               + "matrix.");
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not "
               + "peform gaussian elimination on a " + a.getRows() + "x" + a.getColumns() + " matrix.");

      int n = a.getRows() - 1;
      int[] l = new int[a.getRows()];
      if (n >= 0) {
         // is it worth it to copy? do we want people calling this function on its own?
         // a = a.copy();

         Vector s = new Vector(a.getRows());
         for (int i = 0; i <= n; i++) {
            l[i] = i;
            double smax = 0.0;
            for (int j = 0; j <= n; j++) {
               smax += Math.max(smax, Math.abs(a.get(i, j)));
            }
            s.set(i, smax);
         }
         for (int k = 0; k <= n - 1; k++) {
            int t = 0;
            double rmax = 0.0;
            for (int i = k; i <= n; i++) {
               double r = Math.abs(a.get(l[i], k) / s.get(l[i]));
               if (r > rmax) {
                  rmax = r;
                  t = i;
               }
            }

            int l_k = l[t];
            l[t] = l[k];
            l[k] = l_k;

            for (int i = k + 1; i <= n; i++) {
               int l_i = l[i];
               double xmult = a.get(l_i, k) / a.get(l_k, k);
               a.set(l_i, k, xmult);
               for (int j = k + 1; j <= n; j++) {
                  a.set(l_i, j, a.get(l_i, j) - xmult * a.get(l_k, j));
               }
            }
         }
      }
      return l;
   }


   public static final Matrix rowEscilonForm(Matrix a) {
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not find "
               + "the row escilon form of a " + a.getRows() + "x" + a.getColumns() + " matrix.");
      a = a.copy();
      int n = a.getRows() - 1;
      for (int k = 0; k <= n - 1; k++) {
         for (int i = k + 1; i <= n; i++) {
            double xmult = a.get(i, k) / a.get(k, k);
            a.set(i, k, 0.0);
            for (int j = k + 1; j <= n; j++) {
               a.set(i, j, a.get(i, j) - xmult * a.get(k, j));
            }
         }
      }
      return a;
   }

   public static final Matrix rowReducedEscilonForm(Matrix a) {
      a = rowEscilonForm(a);
      int n = a.getRows() - 1;
      for (int k = 0; k <= n; k++) {
         double xdiv = a.get(k, k);
         for (int i = k; i <= n; i++) {
            a.set(k, i, a.get(k, i) / xdiv);
         }
      }
      return a;
   }

   public static final double determinate(Matrix a) {
      a = rowEscilonForm(a);
      double det = 0.0;
      if (a.getRows() > 0) {
         det = 1.0;
         for (int i = 0; i < a.getRows(); i++) {
            det *= a.get(i, i);
         }
      }
      return det;
   }


   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 300
   public static final void doolittleFactorization(Matrix a) {
      int n = a.getRows() - 1;
      Matrix u = new Matrix(a.getRows(), a.getColumns());
      Matrix l = new Matrix(a.getRows(), a.getColumns());

      for (int k = 0; k <= n; k++) {
         l.set(k, k, 1);

         for (int j = k; j <= n; j++) {
            double sum = 0.0;
            for (int s = 0; s <= k - 1; s++)
               sum += l.get(k, s) * u.get(s, j);

            u.set(k, j, a.get(k, j) - sum);
         }

         for (int i = k + 1; i <= n; i++) {
            double sum = 0.0;
            for (int s = 0; s <= k - 1; s++)
               sum += l.get(i, s) * u.get(s, k);

            l.set(i, k, (a.get(i, k) - sum) / u.get(k, k));
         }
      }

      a.print(4, 1);
      u.print(4, 1);
      l.print(4, 1);
   }


   public static final Vector jacobi(Matrix a, Vector b) {
      return jacobi(a, b, 100, 1e-10);
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 327-8
   public static final Vector jacobi(Matrix a, Vector b, int kmax, double epsilon) {
      int n = a.getRows() - 1;

      Vector x = new Vector(b.getLength());

      for (int k = 1; k <= kmax; k++) {
         Vector y = x.copy();

         for (int i = 0; i <= n; i++) {
            double sum = b.get(i);
            double diag = a.get(i, i);
            for (int j = 0; j <= n; j++) {
               if (j != i)
                  sum -= a.get(i, j) * y.get(j);
            }

            x.set(i, sum / diag);

         }
         if (Vector.subtract(x, y).abs() <= epsilon)
            return x;
      }
      System.err.println("Could not solve system: maximum iterations reached.");
      return x;
   }


   public static final Vector gaussSeidel(Matrix a, Vector b) {
      return gaussSeidel(a, b, 100, 1e-10);
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 327-8
   public static final Vector gaussSeidel(Matrix a, Vector b, int kmax, double epsilon) {
      int n = a.getRows() - 1;

      Vector x = new Vector(b.getLength());

      for (int k = 1; k <= kmax; k++) {
         Vector y = x.copy();

         for (int i = 0; i <= n; i++) {
            double sum = b.get(i);
            double diag = a.get(i, i);
            for (int j = 0; j <= i - 1; j++)
               sum -= a.get(i, j) * x.get(j);
            for (int j = i + 1; j <= n; j++)
               sum -= a.get(i, j) * y.get(j);

            x.set(i, sum / diag);
         }
         if (Vector.subtract(x, y).abs() <= epsilon)
            return x;
      }
      System.err.println("Could not solve system: maximum iterations reached.");
      return x;
   }


   public static final Vector SOR(Matrix a, Vector b, double omega) {
      return SOR(a, b, omega, 100, 1e-10);
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 327-8
   public static final Vector SOR(Matrix a, Vector b, double omega, int kmax, double epsilon) {
      if (omega > 2 || omega < 0) {
         System.err.println("LinearAlgebra - SOR: omega value give is out of range [0, 2], setting to default of 1.1");
         omega = 1.1;
      }
      int n = a.getRows() - 1;

      Vector x = new Vector(b.getLength());

      for (int k = 1; k <= kmax; k++) {
         Vector y = x.copy();

         for (int i = 0; i <= n; i++) {
            double sum = b.get(i);
            double diag = a.get(i, i);

            for (int j = 0; j <= i - 1; j++)
               sum -= a.get(i, j) * x.get(j);
            for (int j = i + 1; j <= n; j++)
               sum -= a.get(i, j) * y.get(j);

            x.set(i, sum / diag);
            x.set(i, omega * x.get(i) + (1 - omega) * y.get(i));
         }
         if (Vector.subtract(x, y).abs() <= epsilon)
            return x;
      }
      System.err.println("Could not solve system: maximum iterations reached.");
      return x;
   }


   public static final Vector conjugateGradient(Matrix a, Vector b) {
      return conjugateGradient(a, b, 100, 1e-10);
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 334
   public static final Vector conjugateGradient(Matrix a, Vector b, int kmax, double epsilon) {
      Vector x = new Vector(b.getLength());
      Vector r = Vector.subtract(b.copy(), Vector.multiply(a, x));
      double delta = Vector.innerProduct(r, r);
      double delta_old;
      Vector p = r.copy();

      for (int k = 1; delta >= epsilon * epsilon * Vector.innerProduct(b, b) && k < kmax; k++) {
         Vector omega = Vector.multiply(a, p);
         double alpha = delta / Vector.innerProduct(p, omega);
         x.add(Vector.scale(alpha, p));
         r.subtract(Vector.scale(alpha, omega));
         delta_old = delta;
         delta = Vector.innerProduct(r, r);


         double beta = delta / delta_old;
         p = Vector.add(r, Vector.scale(beta, p));
      }

      return x;
   }


   // FIXME code: error checks
   public static final double eigenvalue(Matrix a) {
      Vector x = new Vector(a.getColumn(0));
      return powerMethodAccelerated(a, x, 100, 0);
   }

   // FIXME code: error checks and better phi function
   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 362
   public static final double powerMethod(Matrix a, Vector x, int kmax, double epsilon) {
      Vector y = new Vector(x.getLength());
      double lambda = 0;
      double lambda_old = Double.POSITIVE_INFINITY;

      for (int k = 1; k <= kmax; k++) {
         y = Vector.multiply(a, x);
         lambda = y.get(1) / x.get(1); // phi function

         if (Math.abs(lambda - lambda_old) <= epsilon)
            return lambda;

         lambda_old = lambda;
         x = y;
      }

      return lambda;
   }

   // FIXME code: error checks and better phi function
   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 362
   public static final double powerMethodNormalized(Matrix a, Vector x, int kmax, double epsilon) {
      Vector y = new Vector(x.getLength());
      double lambda = 0;
      double lambda_old = Double.POSITIVE_INFINITY;

      for (int k = 1; k <= kmax; k++) {
         y = Vector.multiply(a, x);

         double ymax = 0;
         for (int i = 0; i < y.getLength(); i++)
            ymax = Math.max(Math.abs(y.get(i)), ymax);
         lambda = y.get(1) / x.get(1); // phi function

         if (Math.abs(lambda - lambda_old) <= epsilon)
            return lambda;

         lambda_old = lambda;
         x = y.scale(1.0 / ymax);
      }

      return lambda;
   }

   // FIXME code: error checks and better phi function
   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 362-3
   public static final double powerMethodAccelerated(Matrix a, Vector x, int kmax, double epsilon) {
      Vector y = new Vector(x.getLength());
      double[] r = new double[3];
      double s = 0;
      double s_old = Double.POSITIVE_INFINITY;

      for (int k = 0; k < kmax; k++) {
         y = Vector.multiply(a, x);

         double ymax = 0;
         for (int i = 0; i < y.getLength(); i++)
            ymax = Math.max(Math.abs(y.get(i)), ymax);
         r[k % 3] = y.get(1) / x.get(1); // phi function

         if (k > 2) {
            s = r[k % 3] - Utils.sqr(r[k % 3] - r[(k - 1) % 3]) / (r[k % 3] - 2.0 * r[(k - 1) % 3] + r[(k - 2) % 3]);
            if (Math.abs(s - s_old) <= epsilon)
               return s;
            s_old = s;
         }

         x = y.scale(1.0 / ymax);
      }

      return s;
   }

}