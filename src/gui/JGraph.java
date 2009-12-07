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
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import math.functions.Function;
import math.matrices.Matrix;
import draw.GraphDims;
import draw.IGraph;
import draw.GraphDimsSubscriber;
import draw.Vector2D;

public class JGraph extends JPanel implements ComponentListener, MouseWheelListener, MouseMotionListener, KeyListener,
		GraphDimsSubscriber {

	private static final long serialVersionUID = 3725534931202232960L;
	private JLabel xLabel;
	private JLabel ylabel;

	private Vector2D mouse = null;

	private boolean isZoom = false;
	private boolean isDrag = false;

	private Matrix toWindow;
	private Matrix toGraph;

	private IGraph graph;
	private GraphDims original;
	private GraphDims current;

	public JGraph(IGraph graph, GraphDims dimensions) {
		this.original = dimensions.copy();
		this.current = dimensions;
		current.addSubscriber(this);
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

	@Override
	public void paint(Graphics grid) {
		super.paint(grid);
		Graphics2D g = (Graphics2D) grid;
		DecimalFormat oneDForm = new DecimalFormat("0.000");
		if (mouse != null) {
			xLabel.setText(String.valueOf(oneDForm.format(mouse.getY())));
			ylabel.setText(String.valueOf(oneDForm.format(mouse.getX())));
		}
		drawXYAxis(g);

		for (Function f : graph.getFunctions())
			draw(g, f);
	}

	private void drawXYAxis(Graphics g) {
		g.setColor(Color.black); // axis color
		drawLine(g, new Vector2D(current.getMinX(), 0), new Vector2D(current.getMaxX(), 0)); // x-axis
		drawLine(g, new Vector2D(0, current.getMinY()), new Vector2D(0, current.getMaxY())); // y-axis
	}

	private void drawLine(Graphics g, Vector2D p1, Vector2D p2) {
		p1 = toWindow(p1);
		p2 = toWindow(p2);
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
	}

	private void draw(Graphics g, Function f) {
		double dx = (current.getMaxX() - current.getMinX()) / getWidth() * 2.0;

		Vector2D prev = new Vector2D(current.getMinX(), f.f(current.getMinX()));
		for (double x = current.getMinX() + dx; x <= current.getMaxX() + dx; x += dx) {
			Vector2D next = new Vector2D(x, f.f(x));
			if (!(Double.isInfinite(prev.getY()) || Double.isNaN(prev.getY()) || Double.isInfinite(next.getY()) || Double
					.isNaN(next.getY())))
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
		double ratio_x = getWidth() / (current.getMaxX() - current.getMinX());
		double ratio_y = getHeight() / (current.getMaxY() - current.getMinY());
		toWindow = new Matrix(new double[][] { { ratio_x, 0.0, -ratio_x * current.getMinX() },
				{ 0.0, -ratio_y, ratio_y * current.getMaxY() } });
		toGraph = new Matrix(new double[][] { { 1.0 / ratio_x, 0.0, current.getMinX() },
				{ 0.0, -1.0 / ratio_y, current.getMaxY() } });
	}

	public void setMouse(MouseEvent e) {
		mouse = toGraph(new Vector2D(e.getX(), e.getY()));
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
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (isZoom) {
			double scale = 1.0 + Math.abs(e.getWheelRotation() * 0.1);
			if (e.getWheelRotation() < 0.0)
				scale = 1.0 / scale;

			double xMinDist = mouse.getX() - current.getMinX();
			double xMaxDist = current.getMaxX() - mouse.getX();

			double yMinDist = mouse.getY() - current.getMinY();
			double yMaxDist = current.getMaxY() - mouse.getY();

			xMinDist *= scale;
			xMaxDist *= scale;

			yMinDist *= scale;
			yMaxDist *= scale;

			current.set(mouse.getX() - xMinDist, mouse.getX() + xMaxDist, mouse.getY() - yMinDist, mouse.getY()
					+ yMaxDist);
			setMouse(e);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (mouse != null && isDrag) {
			double deltaX = mouse.getX();
			double deltaY = mouse.getY();
			setMouse(e);
			deltaX -= mouse.getX();
			deltaY -= mouse.getY();

			current.set(current.getMinX() + deltaX, current.getMaxX() + deltaX, current.getMinY() + deltaY, current
					.getMaxY()
					+ deltaY);
			setMouse(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setMouse(e);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isZoom = true;
		} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
			isDrag = true;
		} else if (e.getKeyChar() == 'r') {
			current.set(original.getMinX(), original.getMaxX(), original.getMinY(), original.getMaxY());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isZoom = false;
		} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
			isDrag = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void publishGraphDims() {
		setTransformations();
		repaint();
	}
}
