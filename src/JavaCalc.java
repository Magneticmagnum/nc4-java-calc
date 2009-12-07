import gui.JGraph;

import javax.swing.JFrame;

import math.calculus.Differentiation;
import math.functions.Function;
import math.functions.Quadratic;
import math.functions.Trigonometric;
import draw.Graph;
import draw.GraphDims;
import draw.IGraph;

@SuppressWarnings("serial")
public class JavaCalc extends JFrame {

	public static void main(String[] args) {
		JavaCalc t = new JavaCalc();
		IGraph graph = new Graph();
		Function f = Quadratic.createStandard(-2.0, 3.0, 3.0);

		graph.add(f);
		graph.add(Differentiation.derivative(f));
		graph.add(Trigonometric.createSinusoid(3.0, 1.0, 0.0));

		graph.add(Quadratic.createStandard(2.0, -3.0, -3.0));
		
		GraphDims dims = new GraphDims(-10, 10, -10, 10);
		JGraph g = new JGraph(graph, dims);
		t.add(g);
		t.repaint();
	}

	public JavaCalc() {
		super("NCC CompClub Grapher");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
}
