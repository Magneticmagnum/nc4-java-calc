import gui.JGraph;
import gui.JGraphDims;

import javax.swing.JApplet;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import math.calculus.Differentiation;
import math.functions.Function;
import math.functions.Quadratic;
import math.functions.Trigonometric;
import math.plot.Plot;
import draw.Graph;
import draw.GraphDims;
import draw.IGraph;
import draw.Vector2D;


public class JavaCalcApp extends JApplet {

   private static final long serialVersionUID = -1506840320967282830L;

   @Override
   public void init() {
      // Execute a job on the event-dispatching thread; creating this applet's GUI.
      try {
         SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
               IGraph<Vector2D> graph = new Graph<Vector2D>();

               Function f = Quadratic.createStandard(-2.0, 3.0, 3.0);
               graph.add(f);
               graph.add(Differentiation.derivative(f));
               graph.add(Trigonometric.createSinusoid(3.0, 1.0, 0.0));
               graph.add(Quadratic.createStandard(2.0, -3.0, -3.0));

               Plot<Vector2D> p = new Plot<Vector2D>();
               p.add(new Vector2D(2.0, 2.0));
               p.add(new Vector2D(2.0, -2.0));
               p.add(new Vector2D(-2.0, 2.0));
               p.add(new Vector2D(-2.0, -2.0));

               graph.add(p);

               GraphDims dims1 = new GraphDims(-10, 10, -10, 10);
               JGraph g1 = new JGraph(graph, dims1);
               GraphDims dims2 = new GraphDims(-35, 35, -10, 10);
               JGraph g2 = new JGraph(graph, dims2);

               JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, g1, new JGraphDims(dims1));
               JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, g2, new JGraphDims(dims2));
               split1.setDividerLocation(getWidth() / 2);
               split2.setDividerLocation(getWidth() / 2);

               JSplitPane splitmain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, split2);
               splitmain.setDividerLocation(getHeight() / 2);
               add(splitmain);
            }
         });
      } catch (Exception e) {
         System.err.println("createGUI didn't complete successfully");
      }
   }



}
