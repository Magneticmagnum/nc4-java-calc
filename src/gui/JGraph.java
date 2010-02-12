package gui;

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
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import math.functions.Function;
import math.matrices.Matrix;
import math.plot.Plot;
import draw.GraphDims;
import draw.GraphDimsSubscriber;
import draw.IGraph;
import draw.Vector2D;

public class JGraph extends JPanel implements GraphDimsSubscriber, ComponentListener, MouseListener,
      MouseMotionListener, MouseWheelListener, KeyListener {

   private static final long    serialVersionUID = 3725534931202232960L;


   private static DecimalFormat oneDForm         = new DecimalFormat("0.000");

   private JLabel               xLabel;
   private JLabel               ylabel;

   private Vector2D             mouse            = null;

   private Matrix               toWindow;
   private Matrix               toGraph;

   private IGraph<Vector2D>     graph;
   private GraphDims            original;
   private GraphDims            current;

   public JGraph(IGraph<Vector2D> graph, GraphDims dimensions) {
      this.original = dimensions.copy();
      this.current = dimensions;
      this.current.addSubscriber(this);
      this.graph = graph;

      setTransformations();

      this.xLabel = new JLabel();
      this.ylabel = new JLabel();
      add(xLabel, BorderLayout.NORTH);
      add(ylabel, BorderLayout.EAST);

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
      Graphics2D g = (Graphics2D) grid;
      if (mouse != null) {
         xLabel.setText(oneDForm.format(mouse.getY()));
         ylabel.setText(oneDForm.format(mouse.getX()));
      }
      drawXYAxis(g);

      for (Function f : graph.getFunctions())
         draw(g, f);
      for (Plot<Vector2D> plot : graph.getPlots())
         draw(g, plot);
   }

   private void drawXYAxis(Graphics g) {
      g.setColor(Color.black); // axis color
      drawLine(g, new Vector2D(current.getMinX(), 0), new Vector2D(current.getMaxX(), 0)); // x-axis
      drawLine(g, new Vector2D(0, current.getMinY()), new Vector2D(0, current.getMaxY())); // y-axis
   }

   private void draw(Graphics g, Function f) {
      double dx = (current.getMaxX() - current.getMinX()) / (getWidth() * 2.0);

      Vector2D prev = new Vector2D(current.getMinX(), f.f(current.getMinX()));
      for (double x = current.getMinX() + dx; x <= current.getMaxX() + dx; x += dx) {
         Vector2D next = new Vector2D(x, f.f(x));
         if (!(Double.isInfinite(prev.getY()) || Double.isInfinite(next.getY()) || Double.isNaN(prev.getY()) || Double
               .isNaN(next.getY())))
            drawLine(g, prev, next);
         prev = next;
      }
   }

   private void draw(Graphics g, Plot<Vector2D> plot) {
      for (Vector2D point : plot.getData())
         fillOval(g, point);
   }

   private void drawLine(Graphics g, Vector2D p1, Vector2D p2) {
      p1 = toWindow(p1);
      p2 = toWindow(p2);
      g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
   }

   private void fillOval(Graphics g, Vector2D p) {
      p = toWindow(p);
      g.fillOval((int) p.getX() - 2, (int) p.getY() - 2, 4, 4);
   }

   public Vector2D toWindow(Vector2D v) {
      return v.transform(toWindow);
   }

   public Vector2D toGraph(Vector2D v) {
      return v.transform(toGraph);
   }

   private void setTransformations() {
      double ratio_x = getWidth() / (current.getMaxX() - current.getMinX());
      double ratio_y = getHeight() / (current.getMaxY() - current.getMinY());
      toWindow = new Matrix(new double[][] { 
            { ratio_x, 0.0,    -ratio_x * current.getMinX() },
            { 0.0,    -ratio_y, ratio_y * current.getMaxY() }
      });
      toGraph = new Matrix(new double[][] { 
            { 1.0 / ratio_x, 0.0,           current.getMinX() },
            { 0.0,          -1.0 / ratio_y, current.getMaxY() }
      });
   }

   private void setMouse(MouseEvent e) {
      mouse = toGraph(new Vector2D(e.getX(), e.getY()));
   }

   @Override
   public void publishGraphDims() {
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
