package math.calculus;

import math.matrices.Matrix;
import math.matrices.MatrixDimensionException;
import math.matrices.Vector;

// BORED document: math.calculus.LinearAlgebra
public final class LinearAlgebra {

   public LinearAlgebra() {
   }


   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 252
   public static final Vector naiveGaussianElimination(Matrix a, Vector b) {
      if (a == null || b == null)
         throw new NullPointerException("Null Pointer Exception: can not compute naive gaussian elimination on a null " + "matrix.");
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not "
               + "compute naive gaussian elimination on a " + a.getRows() + "x" + a.getColumns() + " matrix.");
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
      return gaussianElimination(a, b, pivotPositions(a));
   }

   // Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid
   // Page 269
   public static final Vector gaussianElimination(Matrix a, Vector b, int[] l) {
      if (a == null || b == null)
         throw new NullPointerException("Null Pointer Exception: can not compute naive gaussian elimination on a null " + "matrix.");
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not "
               + "compute naive gaussian elimination on a " + a.getRows() + "x" + a.getColumns() + " matrix.");
      if (a.getColumns() != b.getLength())
         throw new MatrixDimensionException("Matrix Dimension Exception: vector must be the same length as the matrix.");
      if (a.getColumns() != l.length)
         throw new MatrixDimensionException("Matrix Dimension Exception: pivot position array must be the same length " + "as the matrix.");

      int n = b.getLength() - 1;
      Vector x = new Vector(b.getLength());
      if (n >= 0) {
         for (int k = 0; k <= n - 1; k++) {
            for (int i = k + 1; i <= n; i++) {
               {// if we copy matrix a in pivotPositions(Matrix), we need this:
                  double xmult = a.get(l[i], k) / a.get(l[k], k);
                  a.set(l[i], k, xmult);
                  for (int j = k + 1; j <= n; j++) {
                     a.set(l[i], j, a.get(l[i], j) - xmult * a.get(l[k], j));
                  }
               }

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
   public static final int[] pivotPositions(Matrix a) {
      if (a == null)
         throw new NullPointerException("Null Pointer Exception: can not compute naive gaussian elimination on a null " + "matrix.");
      if (a.getRows() != a.getColumns())
         throw new MatrixDimensionException("Matrix Dimension Exception: matrix must be a square matrix. Can not "
               + "compute naive gaussian elimination on a " + a.getRows() + "x" + a.getColumns() + " matrix.");

      int n = a.getRows() - 1;
      int[] l = new int[a.getRows()];
      if (n >= 0) {
         // FIXME code: is it worth it to copy? do we want people calling this function on its own?
         a = a.copy();

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

}
