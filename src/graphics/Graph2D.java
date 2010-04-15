package graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import math.functions.Function;
import math.graph.GraphDims;
import math.graph.Plot;
import math.graph.Plotable;
import math.matrices.Matrix;

public class Graph2D {

   private Graphics2D graph;
   private GraphDims  dims;

   private Matrix     toWindow;


   public Graph2D(Graphics2D graph, GraphDims dims) {
      this.graph = graph;
      this.dims = dims;

      double ratio_x = getWidth() / (dims.getMaxX() - dims.getMinX());
      double ratio_y = getHeight() / (dims.getMaxY() - dims.getMinY());
      toWindow = new Matrix(new double[][] { { ratio_x, 0.0, -ratio_x * dims.getMinX() },
            { 0.0, -ratio_y, ratio_y * dims.getMaxY() } });
   }


   private Vector2D toWindow(Vector2D v) {
      return v.transform(toWindow);
   }


   public void drawXYAxis() {
      graph.setColor(Color.black); // axis color
      drawLine(new Vector2D(dims.getMinX(), 0), new Vector2D(dims.getMaxX(), 0)); // x-axis
      drawLine(new Vector2D(0, dims.getMinY()), new Vector2D(0, dims.getMaxY())); // y-axis
   }

   public void drawFunction(Function f) {
      if (f != null && !f.equals(Function.NULL_FUNCTION)) {
         double dx = (dims.getMaxX() - dims.getMinX()) / (getWidth() * 2.0);

         Vector2D prev = new Vector2D(dims.getMinX(), f.f(dims.getMinX()));
         for (double x = dims.getMinX() + dx; x <= dims.getMaxX() + dx; x += dx) {
            Vector2D next = new Vector2D(x, f.f(x));
            if (!(Double.isInfinite(prev.getY()) || Double.isInfinite(next.getY()) || Double.isNaN(prev.getY()) || Double
                  .isNaN(next.getY())))
               drawLine(prev, next);
            prev = next;
         }
      }
   }

   public void drawPlot(Plot plot) {
      for (Plotable point : plot.getData())
         fillCircle(point, 2);
   }

   public void drawLine(Vector2D p1, Vector2D p2) {
      p1 = toWindow(p1);
      p2 = toWindow(p2);
      graph.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
   }

   public void fillCircle(Vector2D p, int radius) {
      p = toWindow(p);
      graph.fillOval((int) p.getX() - radius, (int) p.getY() - radius, 2 * radius, 2 * radius);
   }

   public void fillCircle(Plotable p, int radius) {
      fillCircle(new Vector2D(p.getX(), p.getY()), radius);
   }

   public void setColor(Color c) {
      graph.setColor(c);
   }


   public double getWidth() {
      return graph.getClipBounds().getWidth();
   }

   private double getHeight() {
      return graph.getClipBounds().getHeight();
   }

}
