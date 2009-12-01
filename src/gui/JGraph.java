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
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import math.functions.Function;
import math.matrices.Matrix;
import draw.IGraph;
import draw.Vector2D;

public class JGraph extends JPanel implements ComponentListener,
		MouseWheelListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 3725534931202232960L;
	private JLabel xLabel;
	private JLabel ylabel;
	private double xMax;
	private double yMax;
	private double xMin;
	private double yMin;

	private Vector2D mouse = new Vector2D();

	private boolean isZoom = false;

	private Matrix toWindow;
	private Matrix toGraph;

	private IGraph graph;

	public JGraph(double xMin, double xMax, double yMin, double yMax,
			IGraph graph) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;

		this.graph = graph;

		xLabel = new JLabel();
		ylabel = new JLabel();

		setLayout(new BorderLayout());
		setFocusable(true);
		setBackground(Color.WHITE);
		setTransformations();

		addComponentListener(this);
		add(xLabel, BorderLayout.NORTH);
		add(ylabel, BorderLayout.EAST);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addComponentListener(this);

		repaint();
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D.Double(xMin, yMax, xMax - xMin, yMax - yMin);
	}

	@Override
	public void paint(Graphics grid) {
		super.paint(grid);
		Graphics2D g = (Graphics2D) grid;
		DecimalFormat oneDForm = new DecimalFormat("#.#");
		xLabel.setText(String.valueOf(oneDForm.format(mouse.getY())));
		ylabel.setText(String.valueOf(oneDForm.format(mouse.getX())));
		drawXYAxis(g);

		for (Function f : graph.getFunctions())
			draw(g, f);
	}

	private void drawXYAxis(Graphics g) {
		g.setColor(Color.black); // axis color
		drawLine(g, new Vector2D(xMin, 0), new Vector2D(xMax, 0)); // x-axis
		drawLine(g, new Vector2D(0, yMin), new Vector2D(0, yMax)); // y-axis
	}

	private void drawLine(Graphics g, Vector2D p1, Vector2D p2) {
		p1 = toWindow(p1);
		p2 = toWindow(p2);
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2
				.getY());
	}

	private void draw(Graphics g, Function f) {
		Rectangle2D rec = getRectangle();
		double dx = (rec.getMaxX() - rec.getMinX()) / getWidth() * 2.0;

		Vector2D prev = new Vector2D(rec.getMinX(), f.f(rec.getMinX()));
		for (double x = rec.getMinX() + dx; x <= rec.getMaxX() + dx; x += dx) {
			Vector2D next = new Vector2D(x, f.f(x));
			if (!(Double.isInfinite(prev.getY()) || Double.isNaN(prev.getY())
					|| Double.isInfinite(next.getY()) || Double.isNaN(next
					.getY())))
				drawLine(g, prev, next);
			prev = next;
		}
	}

	public Vector2D toWindow(Vector2D v) {
		return v.transform(toWindow);
	}

	public Vector2D toGraph(Vector2D v) {
		return v.transform(toGraph);
	}

	private void setTransformations() {
		double ratio_x = getWidth() / (xMax - xMin);
		double ratio_y = getHeight() / (yMax - yMin);
		toWindow = new Matrix(new double[][] {
	            {
	                  ratio_x, 0.0, -ratio_x * xMin
	            }, {
	                  0.0, -ratio_y, ratio_y * yMax
	            }
	      });
	      toGraph = new Matrix(new double[][] {
	            {
	                  1.0 / ratio_x, 0.0, xMin
	            }, {
	                  0.0, -1.0 / ratio_y, yMax
	            }
	      });
	}

	public void setCoordinate(MouseEvent e) {
		mouse = toGraph(new Vector2D(e.getX(), e.getY()));
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (isZoom) {
			setCoordinate(e);

			double scale = -e.getWheelRotation() * 2.0;
			if (scale == 0.0)
				scale = 1.0;
			else if (scale < 0.0)
				scale = -1.0 / scale;

			double xMinDist = mouse.getX() - xMin;
			double xMaxDist = xMax - mouse.getX();

			double yMinDist = mouse.getY() - yMin;
			double yMaxDist = yMax - mouse.getY();

			xMinDist /= scale;
			xMaxDist /= scale;

			yMinDist /= scale;
			yMaxDist /= scale;

			xMin = mouse.getX() - xMinDist;
			xMax = mouse.getX() + xMaxDist;

			yMin = mouse.getY() - yMinDist;
			yMax = mouse.getY() + yMaxDist;

			setTransformations();
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setCoordinate(e);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setCoordinate(e);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 17)
			isZoom = true;
		if (e.getKeyChar() == 'r') {

			// reset scale

			setTransformations();
			repaint();
			isZoom = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 17)
			isZoom = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
