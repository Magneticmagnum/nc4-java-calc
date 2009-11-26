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
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import math.functions.Function;
import math.matrices.Matrix;
import csci2031.graph.GraphComponent;
import csci2031.graph.Vector2D;
import csci2031.math.Parametric;

public class JGraph extends JPanel implements ComponentListener,
		MouseWheelListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 3725534931202232960L;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private double xMax;
	private double yMax;
	private double xMin;
	private double yMin;
	private double xScl;
	private double yScl;
	// private double x;
	// private double y;
	private double scale = 1.0;

	private Vector2D coordinateVector = new Vector2D();

	private boolean isZoom = false;

	private Matrix toWindow;
	private Matrix toGraph;

	// TODO this should be an IGraph instead. the IGraph will take care of all
	// the components of this JGraph.
	private ArrayList<GraphComponent> components;

	public JGraph(double xMin, double xMax, double xScl, double yMin,
			double yMax, double yScl) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.xScl = xScl;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yScl = yScl;

		components = new ArrayList<GraphComponent>();

		jLabel1 = new JLabel(String.valueOf(scale));
		jLabel2 = new JLabel(String.valueOf(scale));
		jLabel3 = new JLabel(String.valueOf(scale));

		setLayout(new BorderLayout());
		setFocusable(true);
		setBackground(Color.WHITE);
		setTransformations();

		addComponentListener(this);
		add(jLabel1, BorderLayout.SOUTH);
		add(jLabel2, BorderLayout.NORTH);
		add(jLabel3, BorderLayout.EAST);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addComponentListener(this);

		repaint();
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D.Double(xMin, yMax, xMax - xMin, yMax - yMin);
	}

	public void add(GraphComponent c) {
		components.add(c);
	}

	@Override
	public void paint(Graphics grid) {
		super.paint(grid);
		Graphics2D g = (Graphics2D) grid;

		DecimalFormat oneDForm = new DecimalFormat("#.#");
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		jLabel1.setText(String.valueOf(Double.valueOf(twoDForm.format(scale))
				+ "x"));
		jLabel2.setText(String
				.valueOf(oneDForm.format(coordinateVector.getY())));
		jLabel3.setText(String
				.valueOf(oneDForm.format(coordinateVector.getX())));
		drawXYAxis(g);
		// for (GraphComponent c : components)
		// c.paint(this, g);
	}

	public void drawXYAxis(Graphics g) {
		g.setColor(Color.black); // axis color
		drawLine(g, new Vector2D(xMin, 0), new Vector2D(xMax, 0)); // x-axis
		drawLine(g, new Vector2D(0, yMin), new Vector2D(0, yMax)); // y-axis
	}

	public void drawLine(Graphics g, Vector2D p1, Vector2D p2) {
		p1 = toWindow(p1);
		p2 = toWindow(p2);
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2
				.getY());
	}

	public void fillOvel(Graphics g, Vector2D vector, double diameter) {
		vector = toWindow(vector);
		g.fillOval((int) (vector.getX() - diameter / 2.0),
				(int) (vector.getY() - diameter / 2.0), (int) diameter,
				(int) diameter);
	}

	public void draw(Graphics g, Function f) {
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

	public void draw(Graphics g, Parametric p) {
		double dt = p.maxt() / 1000.0;
		Vector2D prev = new Vector2D(p.y(0.0), p.x(0.0));
		for (double t = dt; t <= p.maxt() + dt; t += dt) {
			Vector2D next = new Vector2D(p.y(t), p.x(t));
			if (!(Double.isInfinite(prev.getY()) || Double.isNaN(prev.getY())
					|| Double.isInfinite(next.getY())
					|| Double.isNaN(next.getY())
					|| Double.isInfinite(prev.getX())
					|| Double.isNaN(prev.getX())
					|| Double.isInfinite(next.getX()) || Double.isNaN(next
					.getX())))
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

	// public Vector2D toWindow(double x, double y) {
	// return new Vector2D((x - xMin) * getWidth() / (xMax - xMin), (yMax - y)
	// * getHeight() / (yMax - yMin));
	// }

	// public Vector2D toGraph(double x, double y) {
	// return new Vector2D(x * (xMax - xMin) / getWidth() + xMin,
	// (getHeight() - y) * (yMax - yMin) / getHeight() + yMin);
	// }

	private void setTransformations() {
		// TODO look this over again
		// Brian: I'm not sure that I would delegate the ratio calculations off
		// to another method. You'll have to call it four times for each ratio,
		// so you should store it locally anyways so you might just save the
		// time and do the calculation locally as well.
		toWindow = new Matrix(new double[][] {
				{ getXRatio(), 0.0, -getXRatio() * xMin },
				{ 0.0, -getYRatio(), -getYRatio() * yMin } });
		toGraph = new Matrix(new double[][] { { 1.0 / getXRatio(), 0.0, xMin },
				{ 0.0, -1.0 / getYRatio(), -yMin } });
	}

	public double getGraphWidth() {
		return getWidth();
	}

	public double getGraphHeight() {
		return getHeight();
	}

	public double getXRatio() {
		return getGraphWidth() / (xMax - xMin);
	}

	public double getYRatio() {
		return getGraphHeight() / (yMax - yMin);
	}

	// public void setXCoordinate() {
	// x = coordinateVector.getY();
	// }

	// public void setYCoordinate() {
	// y = coordinateVector.getX();
	// }

	public void setCoordinate(MouseEvent e) {
		coordinateVector = toGraph(new Vector2D(e.getX(), e.getY()));
		// setXCoordinate();
		// setYCoordinate();
	}

	// public double getXCoordinate() {
	// return coordinateVector.getX();
	// }

	// public double getYCoordinate() {
	// return coordinateVector.getY();
	// }

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
		// TODO Auto-generated method stub
		if (isZoom) {
			scale -= (1.0 * e.getWheelRotation());
			scale = Math.max(1.0, scale);
			setTransformations();
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		setCoordinate(e);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		setCoordinate(e);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 17)
			isZoom = true;
		if (e.getKeyChar() == 'r') {
			scale = 1.0;
			setTransformations();
			repaint();
			isZoom = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 17)
			isZoom = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
