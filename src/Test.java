import gui.JGraph;
import javax.swing.JFrame;
import csci2031.graph.Functions;
import csci2031.graph.Graph;
import csci2031.graph.UserPoints;
import csci2031.math.Interpolation;
import csci2031.math.Parametric;
import csci2031.math.Spline;

public class Test extends JFrame {

	public static void main(String[] args) {
		Test t = new Test();

		JGraph g = new JGraph(-10, 10, 1, -10, 10, 1);

		Functions functions = new Functions();
		//UserPoints points = new UserPoints(g);

//		Parametric p = new Parametric() {
//			@Override
//			public double x(double t) {
//				return 5.0 * Math.sin(t);
//			}
//
//			@Override
//			public double y(double t) {
//				return 5.0 * Math.cos(t);
//			}
//
//			@Override
//			public double maxt() {
//				return Math.PI * 2.0;
//			}
//		};
//		functions.add(p);
//
//		functions.add(Interpolation.polyInterpolate(points));
//		functions.add(Spline.spline1(points));
//		functions.add(Spline.spline3(points));

		g.add(functions);
		//g.add(points);

		t.add(g);

		try {
			Thread.sleep(2);
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
