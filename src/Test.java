import gui.JGraph;

import javax.swing.JFrame;

import math.functions.Quadratic;

import draw.Graph;
import draw.IGraph;

public class Test extends JFrame {

	private static final long serialVersionUID = 1802199483736051124L;

	public static void main(String[] args) {
		Test t = new Test();
		IGraph graph = new Graph();
		graph.add(Quadratic.createStandard(-2.0, 3.0, 3.0));
		JGraph g = new JGraph(-10, 10, -10, 10, graph);
		t.add(g);

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

		t.repaint();
	}

	public Test() {
		super("NCC CompClub Grapher");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
}
