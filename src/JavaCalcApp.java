import gui.JFunctionList;
import gui.JGraph;
import gui.JGraphDims;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import math.graph.Graph;
import math.graph.GraphDims;
import math.graph.IGraph;


public class JavaCalcApp extends JApplet {

   private static final long serialVersionUID = -1506840320967282830L;

   private JFrame            frame;

   @Override
   public void init() {
      try {
         SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
               IGraph graph = new Graph();
               JFunctionList funcList = new JFunctionList(graph);
               final GraphDims dims = new GraphDims(-10, 10, -10, 10);

               JGraph jGraph = new JGraph(graph, dims);
               JButton jDims = new JButton("Set Dimensions");
               jDims.setAlignmentX(Component.CENTER_ALIGNMENT);
               jDims.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                     if (frame == null) {
                        frame = new JFrame("Graph Dimensions");
                        frame.add(new JGraphDims(dims));
                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                     } else {
                        frame.setVisible(true);
                     }
                  }
               });

               JPanel panel = new JPanel();
               panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
               panel.add(jGraph);
               panel.add(jDims);

               jGraph.addGraphable(funcList);

               JSplitPane splitmain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, funcList);
               splitmain.setDividerLocation(400);
               add(splitmain);
            }
         });
      } catch (Exception e) {
         System.err.println("createGUI didn't complete successfully");
      }
   }



}
