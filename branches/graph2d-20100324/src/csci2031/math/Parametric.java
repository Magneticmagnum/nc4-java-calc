package csci2031.math;

import java.awt.Graphics2D;

import csci2031.graph.Graph;

public abstract class Parametric {

   public abstract double x(double t);

   public abstract double y(double t);

   public double maxt() {
      return 100;
   }

   public void paint(Graph graph, Graphics2D grid) {
      graph.draw(grid, this);
   }

}
