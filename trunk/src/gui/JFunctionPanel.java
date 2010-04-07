package gui;

import graphics.Vector2D;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import math.calculus.Differentiation;
import math.functions.ExpressionEngine;
import math.functions.Function;
import math.functions.ModularFunction;
import math.graph.IGraph;

public class JFunctionPanel extends JPanel implements ActionListener, FocusListener {

   private static final long serialVersionUID = 4165856416862963979L;

   private ModularFunction   f;
   private IGraph<Vector2D>  graph;

   private JTextField        functionText;
   private JMenuBar          functionMenu;

   public JFunctionPanel(IGraph<Vector2D> graph) {
      f = new ModularFunction();
      this.graph = graph;
      this.graph.add(f);

      functionText = new JTextField();
      functionText.addActionListener(this);
      functionText.addFocusListener(this);

      buildFuncMenu();
      buildLayout();
   }

   private void buildLayout() {
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

   private void buildFuncMenu() {
      functionMenu = new JMenuBar();
      JMenu menu = new JMenu("Function Ops");

      JMenu differentiate = new JMenu("Differentiate");
      differentiate.add(new DifferentiateFunc());

      JMenu integrate = new JMenu("Integrate");

      menu.add(differentiate);
      menu.add(integrate);

      functionMenu.add(menu);
   }

   public String getText() {
      return functionText.getText();
   }

   public Function getFunction() {
      return f;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String text = getText();
      if (text.length() > 0)
         f.set(ExpressionEngine.construct(text, "x"));
      functionText.setBackground(f.equals(Function.NULL_FUNCTION) ? Color.RED : Color.WHITE);
   }

   @Override
   public void focusGained(FocusEvent e) {
      functionText.selectAll();
   }

   @Override
   public void focusLost(FocusEvent e) {
      String text = getText();
      if (text.length() > 0)
         f.set(ExpressionEngine.construct(text, "x"));
      functionText.setBackground(f.equals(Function.NULL_FUNCTION) ? Color.RED : Color.WHITE);
   }

   private class DifferentiateFunc extends JCheckBox implements ItemListener {

      private static final long serialVersionUID = -864954151390321214L;
      private ModularFunction   f_prime;

      public DifferentiateFunc() {
         super("Differentiate");
         this.addItemListener(this);

         f_prime = new ModularFunction();
         graph.add(f_prime);
      }

      @Override
      public void itemStateChanged(ItemEvent e) {
         if (e.getStateChange() == ItemEvent.SELECTED) {
            f_prime.set(Differentiation.derivative(f));
         } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            f_prime.reset();
         }
      }
   }


}
