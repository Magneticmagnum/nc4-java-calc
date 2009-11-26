package csci2031.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import math.functions.Function;
import csci2031.math.Parametric;

public class Functions implements GraphComponent {

   public static Color[] COLORS = {
         Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW
   };

   private ArrayList<Function> functions;
   private ArrayList<Parametric> parametrics;

   public Functions() {
      functions = new ArrayList<Function>();
      parametrics = new ArrayList<Parametric>();
   }

   public void add(Function f) {
      functions.add(f);
   }

   public void add(Parametric p) {
      parametrics.add(p);
   }

   public void paint(Graph graph, Graphics2D grid) {
      int count = 0;
      grid.setStroke(new BasicStroke(2f));
      for (Function f : functions) {
         grid.setColor(COLORS[count++ % COLORS.length]);
         graph.draw(grid, f);
      }
      for (Parametric p : parametrics) {
         grid.setColor(COLORS[count++ % COLORS.length]);
         graph.draw(grid, p);
      }
   }

}
