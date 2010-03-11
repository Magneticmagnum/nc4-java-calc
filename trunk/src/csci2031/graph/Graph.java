package csci2031.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import math.functions.Function;
import math.matrices.Matrix;
import math.matrices.Vector;
import math.matrices.Vector2D;
import csci2031.math.Parametric;

public class Graph extends JPanel implements ComponentListener {

   private static final long serialVersionUID = 3725534931202232960L;


   private double maxX;
   private double maxY;
   private double minX;
   private double minY;

   private Matrix toWindow;
   private Matrix toGraph;

   private ArrayList<GraphComponent> components;

   public Graph(double minX, double maxX, double minY, double maxY) {
      this.minX = minX;
      this.maxX = maxX;
      this.minY = minY;
      this.maxY = maxY;

      addComponentListener(this);
      setTransformations();

      components = new ArrayList<GraphComponent>();

      repaint();
   }

   public Rectangle2D getRectangle() {
      return new Rectangle2D.Double(minX, maxY, maxX - minX, maxY - minY);
   }

   public void add(GraphComponent c) {
      components.add(c);
   }

   @Override
   public void paint(Graphics grid) {
      super.paint(grid);
      Graphics2D g = (Graphics2D) grid;

      g.setColor(Color.BLACK);
      drawLine(g, new Vector2D(0, minY), new Vector2D(0, maxY));
      drawLine(g, new Vector2D(minX, 0), new Vector2D(maxX, 0));
      for (GraphComponent c : components)
         c.paint(this, g);
   }

   public void drawLine(Graphics g, Vector p1, Vector p2) {
      p1 = toWindow(p1);
      p2 = toWindow(p2);
      g.drawLine((int) p1.get(0), (int) p1.get(1), (int) p2.get(0), (int) p2.get(1));
   }

   public void fillOvel(Graphics g, Vector vector, double diameter) {
      vector = toWindow(vector);
      g.fillOval((int) (vector.get(0) - diameter / 2.0), (int) (vector.get(1) - diameter / 2.0), (int) diameter,
            (int) diameter);
   }

   public void draw(Graphics g, Function f) {
      Rectangle2D rec = getRectangle();
      double dx = (rec.getMaxX() - rec.getMinX()) / getWidth() * 2.0;

      Vector2D prev = new Vector2D(rec.getMinX(), f.f(rec.getMinX()));
      for (double x = rec.getMinX() + dx; x <= rec.getMaxX() + dx; x += dx) {
         Vector2D next = new Vector2D(x, f.f(x));
         if (!(Double.isInfinite(prev.getY()) || Double.isNaN(prev.getY()) || Double.isInfinite(next.getY()) || Double
               .isNaN(next.getY())))
            drawLine(g, prev, next);
         prev = next;
      }
   }

   public void draw(Graphics g, Parametric p) {
      double dt = p.maxt() / 1000.0;
      Vector2D prev = new Vector2D(p.y(0.0), p.x(0.0));
      for (double t = dt; t <= p.maxt() + dt; t += dt) {
         Vector2D next = new Vector2D(p.y(t), p.x(t));
         if (!(Double.isInfinite(prev.getY()) || Double.isNaN(prev.getY()) || Double.isInfinite(next.getY())
               || Double.isNaN(next.getY()) || Double.isInfinite(prev.getX()) || Double.isNaN(prev.getX())
               || Double.isInfinite(next.getX()) || Double.isNaN(next.getX())))
            drawLine(g, prev, next);
         prev = next;
      }
   }


   public Vector toWindow(Vector v) {
      return Vector.multiply(toWindow, v);
   }

   public Vector toGraph(Vector v) {
      return Vector.multiply(toGraph, v);
   }

   public Vector2D toWindow(double x, double y) {
      return new Vector2D((x - minX) * getWidth() / (maxX - minX), (maxY - y) * getHeight() / (maxY - minY));
   }

   public Vector2D toGraph(double x, double y) {
      return new Vector2D(x * (maxX - minX) / getWidth() + minX, (getHeight() - y) * (maxY - minY) / getHeight() + minY);
   }

   private void setTransformations() {
      double wx_min = 0;
      double wx_max = getWidth();
      double wy_min = 0;
      double wy_max = getHeight();

      double ratio_x = (wx_max - wx_min) / (maxX - minX);
      double ratio_y = (wy_max - wy_min) / (maxY - minY);
      toWindow = new Matrix(new double[][] {
            {
                  ratio_x, 0.0, -ratio_x * minX
            }, {
                  0.0, -ratio_y, -ratio_y * minY
            }
      });

      toGraph = new Matrix(new double[][] {
            {
                  1.0 / ratio_x, 0.0, minX
            }, {
                  0.0, -1.0 / ratio_y, -minY
            }
      });
   }

   @Override
   public void componentHidden(ComponentEvent e) {
   }

   @Override
   public void componentMoved(ComponentEvent e) {
   }

   @Override
   public void componentResized(ComponentEvent e) {
      setTransformations();
      repaint();
   }

   @Override
   public void componentShown(ComponentEvent e) {
   }

}
