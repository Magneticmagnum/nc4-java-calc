package gui;

import javax.swing.GroupLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import math.functions.Function;

public class JFunctionPanel extends JPanel {

   private static final long serialVersionUID = 4165856416862963979L;

   private Function          f;

   private JTextField        functionText;
   private JMenuBar          functionMenu;

   public JFunctionPanel() {
      functionText = new JTextField();
      functionMenu = buildMenuBar();
      buildLayout();
   }

   private void buildLayout() {
      // SpringLayout layout = new SpringLayout();
      // this.setLayout(layout);
      // this.add(functionText);
      // this.add(functionMenu);
      // layout.putConstraint(SpringLayout.NORTH, functionMenu, 5, SpringLayout.NORTH, this);
      // layout.putConstraint(SpringLayout.EAST, functionMenu, -5, SpringLayout.EAST, this);
      // layout.putConstraint(SpringLayout.WEST, functionText, 5, SpringLayout.WEST, this);
      // layout.putConstraint(SpringLayout.NORTH, functionText, 5, SpringLayout.NORTH, this);
      // layout.putConstraint(SpringLayout.EAST, functionText, -5, SpringLayout.WEST, functionMenu);
      // layout.putConstraint(SpringLayout.NORTH, this, 10, SpringLayout.SOUTH, this);

      GroupLayout layout = new GroupLayout(this);

      GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
      hGroup.addComponent(functionText).addGap(5).addComponent(functionMenu);

      GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(Alignment.BASELINE);
      vGroup.addComponent(functionText).addComponent(functionMenu, 0, GroupLayout.PREFERRED_SIZE,
            GroupLayout.PREFERRED_SIZE);

      layout.setHorizontalGroup(hGroup);
      layout.setVerticalGroup(vGroup);

      this.setLayout(layout);
   }

   private JMenuBar buildMenuBar() {
      JMenuBar bar = new JMenuBar();
      JMenu menu = new JMenu("Function Ops");

      JMenu differentiate = new JMenu("Differentiate");
      JMenu integrate = new JMenu("Integrate");

      menu.add(differentiate);
      menu.add(integrate);

      bar.add(menu);
      return bar;
   }

   public String getText() {
      return functionText.getText();
   }

   public Function getFunction() {
      return f;
   }

}
