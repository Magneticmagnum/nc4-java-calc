package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import math.matrices.Matrix;
import math.matrices.Vector;

public class RectangularGraph { // implements IGraph {

	private static final long serialVersionUID = 3725534931202232960L;

	private Rectangle2D graph;
	private double windowWidth;
	private double windowHeight;

	private Matrix toWindow;
	private Matrix toGraph;

	private Graphics2D currentGraphics;

	public RectangularGraph(double minX, double maxX, double minY, double maxY,
			double windowWidth, double windowHeight) {
		this(new Rectangle2D.Double(minX, maxY, maxX - minX, maxY - minY),
				windowWidth, windowHeight);
	}

	public RectangularGraph(Rectangle2D dimensions, double windowWidth,
			double windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		setRectangle(dimensions);
	}

	public void setRectangle(Rectangle2D newDimensions) {
		this.graph = newDimensions;
		setTransformations();
	}

	public Rectangle2D getRectangle() {
		return graph;
	}

	public void paint(Graphics grid) {
		currentGraphics = (Graphics2D) grid;

		currentGraphics.setColor(Color.BLACK);
		drawLine(getVector(0, graph.getMinY()), getVector(0, graph.getMaxY()));
		drawLine(getVector(graph.getMinX(), 0), getVector(graph.getMaxX(), 0));
	}

	public void drawLine(Vector p1, Vector p2) {
		p1 = toWindow(p1);
		p2 = toWindow(p2);
		currentGraphics.drawLine((int) p1.get(0), (int) p1.get(1), (int) p2
				.get(0), (int) p2.get(1));
	}

	public void fillOvel(Vector vector, double diameter) {
		vector = toWindow(vector);
		currentGraphics.fillOval((int) (vector.get(0) - diameter / 2.0),
				(int) (vector.get(1) - diameter / 2.0), (int) diameter,
				(int) diameter);
	}

	public Vector getVector(double x, double y) {
		return new Vector(new double[] { x, y, 1.0 });
	}

	public Vector toWindow(Vector v) {
		return Vector.multiply(toWindow, v);
	}

	public Vector toGraph(Vector v) {
		return Vector.multiply(toGraph, v);
	}

	private void setTransformations() {
		double ratio_x = windowWidth / graph.getWidth();
		double ratio_y = windowHeight / graph.getHeight();
		toWindow = new Matrix(new double[][] {
				{ ratio_x, 0.0, -ratio_x * graph.getMinX() },
				{ 0.0, -ratio_y, -ratio_y * graph.getMinY() },
				{ 0.0, 0.0, 1.0 } });
		toGraph = new Matrix(new double[][] {
				{ 1.0 / ratio_x, 0.0, graph.getMinX() },
				{ 0.0, -1.0 / ratio_y, -graph.getMinY() }, { 0.0, 0.0, 1.0 } });
	}

}
