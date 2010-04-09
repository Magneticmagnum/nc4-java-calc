package gui;

import graphics.Graph2D;
import graphics.Graphable;
import gui.funcops.Differentiate;
import gui.funcops.FunctionOperation;
import gui.funcops.TangentLine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import math.functions.ExpressionEngine;
import math.functions.Function;
import math.functions.ModularFunction;

public class JFunctionPanel extends JPanel implements ActionListener, FocusListener, Graphable {

   private static final long             serialVersionUID = 4165856416862963979L;

   private ModularFunction               f;

   private JButton                       remove;
   private JTextField                    functionText;
   private JMenuBar                      functionMenu;

   private LinkedList<FunctionOperation> ops;

   public JFunctionPanel() {
      f = new ModularFunction();

      remove = new JButton("x");
      functionText = new JTextField();
      functionText.addActionListener(this);
      functionText.addFocusListener(this);

      ops = new LinkedList<FunctionOperation>();

      buildFuncMenu();
      buildLayout();
   }

   private void buildLayout() {
      GroupLayout layout = new GroupLayout(this);

      GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
      hGroup.addComponent(remove).addGap(5).addComponent(functionText).addGap(5).addComponent(functionMenu);

      GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(Alignment.BASELINE);
      vGroup.addComponent(remove).addComponent(functionText).addComponent(functionMenu, 0, GroupLayout.PREFERRED_SIZE,
            GroupLayout.PREFERRED_SIZE);

      layout.setHorizontalGroup(hGroup);
      layout.setVerticalGroup(vGroup);

      this.setLayout(layout);
   }

   private void buildFuncMenu() {
      functionMenu = new JMenuBar();
      JMenu menu = new JMenu("Function Ops");

      JMenu differentiate = new JMenu("Differentiate");
      differentiate.add(new FuncOpCheckBox("Derivative", new Differentiate(f)));
      differentiate.add(new FuncOpCheckBox("Tangent Line", new TangentLine(f)));

      JMenu integrate = new JMenu("Integrate");

      menu.add(differentiate);
      menu.add(integrate);

      functionMenu.add(menu);
   }

   public void addRemoveButtonActionListener(ActionListener listener) {
      remove.addActionListener(listener);
   }

   @Override
   public void onGraph(Graph2D graph) {
      for (FunctionOperation op : ops)
         op.onGraph(graph);
   }

   public String getText() {
      return functionText.getText();
   }

   public Function getFunction() {
      return f;
   }

   private void setFunction() {
      String text = getText();
      if (text.length() > 0)
         f.set(ExpressionEngine.construct(text, "x"));
      functionText.setBackground(f.equals(Function.NULL_FUNCTION) ? Color.RED : Color.WHITE);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      setFunction();
   }

   @Override
   public void focusGained(FocusEvent e) {
      functionText.selectAll();
   }

   @Override
   public void focusLost(FocusEvent e) {
      setFunction();
   }

   @SuppressWarnings("serial")
   private class FuncOpCheckBox extends JCheckBox {
      public FuncOpCheckBox(String name, FunctionOperation listener) {
         super(name);
         addItemListener(listener);
         ops.add(listener);
      }
   }

}
