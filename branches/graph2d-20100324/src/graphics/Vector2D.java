package graphics;

import math.graph.Plotable;
import math.matrices.Matrix;
import math.matrices.Vector;

// BORED document: draw.Vector2D
public class Vector2D extends Vector implements Plotable {

   /**
    * Determines if a deserialized file is compatible with {@code this class}.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of {@code this class} is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -5955314561155916948L;

   public Vector2D() {
      super(new double[] { 0.0, 0.0, 1.0 });
   }

   public Vector2D(double x, double y) {
      super(new double[] { x, y, 1.0 });
   }

   public Vector2D(Vector v) {
      this(v.get(0), v.get(1));
   }

   public void setX(double x) {
      super.set(0, x);
   }

   public void setY(double y) {
      super.set(1, y);
   }

   public double getX() {
      return super.get(0);
   }

   public double getY() {
      return super.get(1);
   }

   public Vector2D transform(Matrix m) {
      return new Vector2D(Vector.multiply(m, this));
   }

}
