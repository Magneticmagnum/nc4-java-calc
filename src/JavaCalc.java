import graphics.Vector2D;
import gui.JFunctionList;
import gui.JGraph;
import gui.JGraphDims;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import math.graph.Graph;
import math.graph.GraphDims;
import math.graph.IGraph;

@SuppressWarnings("serial")
public class JavaCalc extends JFrame {

   public static void main(String[] args) {
      JavaCalc calc = new JavaCalc();

      IGraph<Vector2D> graph = new Graph<Vector2D>();
      JFunctionList funcList = new JFunctionList(graph);
      final GraphDims dims = new GraphDims(-10, 10, -10, 10);

      JGraph jGraph = new JGraph(graph, dims);
      JButton jDims = new JButton("Set Dimensions");
      jDims.setAlignmentX(Component.CENTER_ALIGNMENT);
      jDims.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Graph Dimensions");
            frame.add(new JGraphDims(dims));
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
         }
      });

      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
      panel.add(jGraph);
      panel.add(jDims);

      jGraph.addGraphable(funcList);

      JSplitPane splitmain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, funcList);
      splitmain.setDividerLocation(400);
      calc.add(splitmain);
   }

   public JavaCalc() {
      super("NCC CompClub Graphing Calculator");

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(400, 600);
      setVisible(true);
   }

}
