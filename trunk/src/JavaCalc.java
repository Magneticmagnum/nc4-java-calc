import gui.JGraph;
import gui.JGraphDims;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import math.functions.Quadratic;
import math.functions.Trigonometric;
import math.graph.Graph;
import math.graph.GraphDims;
import math.graph.IGraph;
import math.graph.Plot;
import math.matrices.Vector2D;

@SuppressWarnings("serial")
public class JavaCalc extends JFrame {

   public static void main(String[] args) {
      JavaCalc calc = new JavaCalc();


      IGraph<Vector2D> graph1 = new Graph<Vector2D>();
      graph1.add(Trigonometric.createSinusoid(-3.0, 1.0, 0.0));
      Plot<Vector2D> p = new Plot<Vector2D>();
      p.add(new Vector2D(2.0, 2.0));
      p.add(new Vector2D(2.0, -2.0));
      p.add(new Vector2D(-2.0, 2.0));
      p.add(new Vector2D(-2.0, -2.0));
      graph1.add(p);
      GraphDims dims1 = new GraphDims(-10, 10, -10, 10);
      JGraph g1 = new JGraph(graph1, dims1);


      IGraph<Vector2D> graph2 = new Graph<Vector2D>();
      graph2.add(Quadratic.createFactored(-1.0, 2.0, 3.0));
      // graph2.add(Quadratic.createPolynomial(-1.0, 2.0, 8.0));
      // graph2.add(Linear.createPointSlope(-1.0, 3.0, 2.0));
      GraphDims dims2 = new GraphDims(-10, 10, -10, 10);
      JGraph g2 = new JGraph(graph2, dims2);


      JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, g1, new JGraphDims(dims1));
      JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, g2, new JGraphDims(dims2));
      split1.setDividerLocation(calc.getWidth() / 2);
      split2.setDividerLocation(calc.getWidth() / 2);

      JSplitPane splitmain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, split2);
      splitmain.setDividerLocation(calc.getHeight() / 2);
      calc.add(splitmain);
   }

   public JavaCalc() {
      super("NCC CompClub Graphing Calculator");

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(1000, 1000);
      setVisible(true);
   }

}
