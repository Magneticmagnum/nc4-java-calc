import graphics.Vector2D;
import gui.JFunctionList;
import gui.JGraph;
import gui.JGraphDims;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import math.functions.Trigonometric;
import math.graph.Graph;
import math.graph.GraphDims;
import math.graph.IGraph;
import math.graph.Plot;

@SuppressWarnings("serial")
public class JavaCalc extends JFrame {

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
      }

      JavaCalc calc = new JavaCalc();


      IGraph<Vector2D> graph = new Graph<Vector2D>();
      graph.add(Trigonometric.createSinusoid(-3.0, 1.0, 0.0));
      Plot<Vector2D> plot = new Plot<Vector2D>();
      plot.add(new Vector2D(2.0, 2.0));
      plot.add(new Vector2D(2.0, -2.0));
      plot.add(new Vector2D(-2.0, 2.0));
      plot.add(new Vector2D(-2.0, -2.0));
      graph.add(plot);
      GraphDims dims = new GraphDims(-10, 10, -10, 10);
      JGraph jGraph = new JGraph(graph, dims);


      JSplitPane split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jGraph, new JFunctionList());
      split1.setDividerLocation(calc.getHeight() * 5 / 7);

      JSplitPane splitmain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, new JGraphDims(dims));
      splitmain.setDividerLocation(calc.getWidth() * 5 / 7);
      calc.add(splitmain);
   }

   public JavaCalc() {
      super("NCC CompClub Graphing Calculator");

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(700, 700);
      setVisible(true);
   }

}
