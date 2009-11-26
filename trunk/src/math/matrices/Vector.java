package math.matrices;


// BORED document: math.matrices.Vector
public class Vector extends Matrix {

   /**
    * Determines if a deserialized file is compatible with {@code this class}. <br>
    * <br>
    * Maintainers must change this value if and only if the new version of {@code this class} is not
    * compatible with old versions.
    */
   private static final long serialVersionUID = -4882635154030276051L;


   public Vector(int n) {
      this(new double[n]);
   }

   public Vector(double[] v) {
      set(v);
   }


   public Vector set(double[] v) {
      double[][] m = new double[v.length][1];
      for (int i = 0; i < v.length; i++)
         m[i][0] = v[i];
      return (Vector) super.set(m);
   }

   public void set(int i, double n) {
      super.set(i, 0, n);
   }


   public int getLength() {
      return getRows();
   }

   public double get(int i) {
      return super.get(i, 0);
   }


   public static final Vector multiply(Matrix a, Vector b) {
      if (a == null || b == null) {
         throw new NullPointerException("Cannot multiply a null matrices.");
      } else if (a.getColumns() != b.getRows()) {
         System.out.println("Error - Matrix dimension error");
      }
      Vector c = new Vector(a.getRows());
      for (int i = 0; i < c.getRows(); i++) {
         double sum = 0;
         for (int k = 0; k < b.getRows(); k++)
            sum += a.get(i, k) * b.get(k, 0);
         c.set(i, 0, sum);
      }
      return c;
   }


   @Override
   public Vector copy() {
      return new Vector(getColumn(0));
   }

}
