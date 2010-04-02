package gui;

import graphics.Graph2D;
import graphics.Vector2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import math.functions.Function;
import math.graph.GraphDims;
import math.graph.GraphDimsSubscriber;
import math.graph.IGraph;
import math.graph.Plot;
import math.matrices.Matrix;

public class JGraph extends JPanel implements GraphDimsSubscriber, ComponentListener, MouseListener,
      MouseMotionListener, MouseWheelListener, KeyListener {

   private static final long serialVersionUID = 3725534931202232960L;

   private Vector2D          mouse            = null;

   private Matrix            toGraph;

   private IGraph<Vector2D>  graph;
   private GraphDims         original;
   private GraphDims         current;

   public JGraph(IGraph<Vector2D> graph, GraphDims dimensions) {
      this.original = dimensions.copy();
      this.current = dimensions;
      this.current.addSubscriber(this);
      this.graph = graph;

      setTransformations();

      setLayout(new BorderLayout());
      setFocusable(true);
      setBackground(Color.WHITE);

      addMouseListener(this);
      addMouseMotionListener(this);
      addMouseWheelListener(this);
      addKeyListener(this);
      addComponentListener(this);

      repaint();
   }

   @Override
   public void paint(Graphics grid) {
      super.paint(grid);
      Graph2D graph2d = new Graph2D((Graphics2D) grid, current);

      graph2d.drawXYAxis();
      for (Function f : graph.getFunctions())
         graph2d.drawFunction(f);
      for (Plot<Vector2D> p : graph.getPlots())
         graph2d.drawPlot(p);
   }

   private Vector2D toGraph(Vector2D v) {
      return v.transform(toGraph);
   }

   private void setTransformations() {
      double ratio_x = getWidth() / (current.getMaxX() - current.getMinX());
      double ratio_y = getHeight() / (current.getMaxY() - current.getMinY());
      toGraph = new Matrix(new double[][] { { 1.0 / ratio_x, 0.0, current.getMinX() },
            { 0.0, -1.0 / ratio_y, current.getMaxY() } });
   }

   private void setMouse(MouseEvent e) {
      mouse = toGraph(new Vector2D(e.getX(), e.getY()));
   }

   @Override
   public void updateGraphDims() {
      setTransformations();
      repaint();
   }

   @Override
   public void componentHidden(ComponentEvent e) {
      repaint();
   }

   @Override
   public void componentMoved(ComponentEvent e) {
      repaint();
   }

   @Override
   public void componentResized(ComponentEvent e) {
      setTransformations();
      repaint();
   }

   @Override
   public void componentShown(ComponentEvent e) {
      repaint();
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      requestFocusInWindow(true);
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      if (mouse != null) {
         double dX = mouse.getX();
         double dY = mouse.getY();
         setMouse(e);
         dX -= mouse.getX();
         dY -= mouse.getY();

         current.set(current.getMinX() + dX, current.getMaxX() + dX, current.getMinY() + dY, current.getMaxY() + dY);
         setMouse(e);
      }
      requestFocusInWindow(true);
   }

   @Override
   public void mouseMoved(MouseEvent e) {
      setMouse(e);
      repaint();
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent e) {
      setMouse(e);
      double scale = 1.0 + Math.abs(e.getWheelRotation() * 0.1);
      if (e.getWheelRotation() < 0.0)
         scale = 1.0 / scale;

      double minX = mouse.getX() - (mouse.getX() - current.getMinX()) * scale;
      double maxX = mouse.getX() + (current.getMaxX() - mouse.getX()) * scale;
      double minY = mouse.getY() - (mouse.getY() - current.getMinY()) * scale;
      double maxY = mouse.getY() + (current.getMaxY() - mouse.getY()) * scale;

      current.set(minX, maxX, minY, maxY);
   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (e.getKeyChar() == 'r') {
         current.set(original.getMinX(), original.getMaxX(), original.getMinY(), original.getMaxY());
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }
}
