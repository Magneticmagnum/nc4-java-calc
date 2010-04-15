package gui;

import graphics.Graph2D;
import graphics.Graphable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import math.graph.IGraph;

public class JFunctionList extends JPanel implements Graphable {

   private static final long          serialVersionUID = -1496183123559691747L;

   private IGraph                     graph;

   private LinkedList<JFunctionPanel> functionPanels;
   private JScrollPane                functionScrollPane;
   private JButton                    addButton;


   public JFunctionList(IGraph graph) {
      this.graph = graph;

      functionScrollPane = new JScrollPane();
      functionPanels = new LinkedList<JFunctionPanel>();
      addButton = new AddButton();

      addFunction();

      buildScrollPaneLayout();
      buildLayout();
   }

   private void buildScrollPaneLayout() {
      JPanel panel = new JPanel();
      BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
      panel.setLayout(layout);
      for (JFunctionPanel f : functionPanels) {
         panel.add(f);
         panel.add(Box.createRigidArea(new Dimension(0, 5)));
      }
      panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
      functionScrollPane.setViewportView(panel);
   }

   private void buildLayout() {
      GroupLayout layout = new GroupLayout(this);

      layout.setAutoCreateGaps(true);
      layout.setAutoCreateContainerGaps(true);

      GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
      hGroup.addComponent(functionScrollPane).addGroup(
            layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(
                  addButton));
      layout.setHorizontalGroup(hGroup);

      GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
      vGroup.addComponent(functionScrollPane).addComponent(addButton);
      layout.setVerticalGroup(vGroup);

      this.setLayout(layout);
   }

   private void addFunction() {
      JFunctionPanel panel = new JFunctionPanel();
      graph.add(panel.getFunction());
      panel.addRemoveButtonActionListener(new RemoveListener(panel));
      functionPanels.add(panel);
   }

   @Override
   public void onGraph(Graph2D graph) {
      for (JFunctionPanel f : functionPanels)
         f.onGraph(graph);
   }

   private class AddButton extends JButton implements ActionListener {

      private static final long serialVersionUID = 2476909996003822227L;

      public AddButton() {
         super("Add Function");
         this.addActionListener(this);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (functionPanels.size() == 0 || !functionPanels.getLast().getText().equals("")) {
            addFunction();
            buildScrollPaneLayout();
         }
      }
   }

   private class RemoveListener implements ActionListener {

      private JFunctionPanel toRemove;

      public RemoveListener(JFunctionPanel remove) {
         this.toRemove = remove;
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         graph.remove(toRemove.getFunction());
         functionPanels.remove(toRemove);
         buildScrollPaneLayout();
      }

   }

}