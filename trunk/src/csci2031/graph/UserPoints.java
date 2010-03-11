package csci2031.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import math.Utils;
import math.matrices.Vector;
import math.matrices.Vector2D;

public class UserPoints implements GraphComponent, MouseListener, MouseMotionListener, KeyListener {

   public static double SIZE_D = 6.0;
   public static double SIZE_R = SIZE_D / 2.0;

   private ArrayList<Vector2D> points;
   private long revision = 0L;
   private Graph containee;

   private boolean SHIFT_ON = false;
   private boolean CTRL_ON = false;

   private boolean MOVING = false;
   private Point previous = null;

   public UserPoints(Graph containee) {
      containee.addMouseListener(this);
      containee.addMouseMotionListener(this);
      containee.addKeyListener(this);
      this.containee = containee;
      points = new ArrayList<Vector2D>();
   }

   public void add(Vector2D p) {
      revision++;
      points.add(new Point(p));
   }

   public void remove(Vector2D p) {
      revision++;
      points.remove(new Point(p));
   }

   public ArrayList<Vector2D> get() {
      return points;
   }

   public long getRevision() {
      return revision;
   }

   @Override
   public void paint(Graph graph, Graphics2D grid) {
      grid.setColor(Color.BLACK);
      for (Vector2D p : points)
         graph.fillOvel(grid, new Vector2D(p.getX(), p.getY()), SIZE_D);
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      if (SHIFT_ON)
         add(containee.toGraph(e.getX(), e.getY()));
      if (CTRL_ON)
         remove(containee.toGraph(e.getX(), e.getY()));
      containee.repaint();
   }

   @Override
   public void mouseEntered(MouseEvent e) {
      containee.requestFocusInWindow();
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
      Point p = new Point(containee.toGraph(e.getX(), e.getY()));
      if (points.contains(p)) {
         previous = p;
         MOVING = true;
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      if (MOVING && !CTRL_ON) {
         add(containee.toGraph(e.getX(), e.getY()));
         if (previous != null)
            remove(previous);
      }
      MOVING = false;
      containee.repaint();
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      if (MOVING && !CTRL_ON) {
         remove(previous);
         add(containee.toGraph(e.getX(), e.getY()));
         containee.repaint();
         previous = new Point(containee.toGraph(e.getX(), e.getY()));
      }
   }

   @Override
   public void mouseMoved(MouseEvent e) {
      // if (MOVING && !CTRL_ON) {
      // add(containee.transformToGraph(e.getX(), e.getY()));
      // containee.repaint();
      // remove(containee.transformToGraph(e.getX(), e.getY()));
      // }
   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_CONTROL)
         CTRL_ON = true;
      else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
         SHIFT_ON = true;
   }

   @Override
   public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_CONTROL)
         CTRL_ON = false;
      else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
         SHIFT_ON = false;
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   private class Point extends Vector2D {
      private static final long serialVersionUID = 8841355882868718612L;

      public Point(Vector2D p) {
         super(p.getX(), p.getY());
      }

      @Override
      public boolean equals(Object obj) {
         if (obj instanceof Vector2D) {
            Vector p1 = containee.toWindow(this);
            Vector p2 = containee.toWindow((Vector2D) obj);
            return Utils.sqr(p1.get(0) - p2.get(0)) + Utils.sqr(p1.get(1) - p2.get(1)) <= Utils.sqr(SIZE_R);
         }
         return super.equals(obj);
      }
   }

}
