package gui.funcops;

import graphics.Graph2D;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import math.calculus.Differentiation;
import math.functions.Function;
import math.functions.Linear;
import math.functions.ModularFunction;

public class TangentLine extends FunctionOperation implements ItemListener {

   private static final long serialVersionUID = -864954151390321214L;

   private ModularFunction   f_prime;
   private double            x                = 0;

   private JFrame            frame;

   public TangentLine(Function f) {
      super(f);
      f_prime = new ModularFunction();
   }

   @Override
   public void onGraph(Graph2D graph) {
      graph.setColor(Color.GREEN);
      graph.drawFunction(f_prime);
   }

   @Override
   public void itemStateChanged(ItemEvent e) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
         if (frame == null) {
            frame = new JFrame("Enter Tanget Point");

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(new JLabel("Enter Point: "));
            panel.add(new DoubleTextField(0.0, 15));
            frame.add(panel);

            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
         } else {
            frame.setVisible(true);
         }
         f_prime.set(Linear.createPointSlope(Differentiation.derivative(getFunction(), x), x, getFunction().f(x)));
      } else if (e.getStateChange() == ItemEvent.DESELECTED) {
         f_prime.reset();
      }
   }

   private class DoubleTextField extends JTextField implements ActionListener, FocusListener {

      private static final long   serialVersionUID = 7092604735271985943L;
      private static final String badchars         = "`~!@#$%^&*()_+=\\|\"':;?/><, ";

      public DoubleTextField(Double d, int width) {
         super(String.valueOf(d), width);
         addActionListener(this);
         addFocusListener(this);
      }

      @Override
      public void processKeyEvent(KeyEvent ev) {
         char c = ev.getKeyChar();
         if ((Character.isLetter(c) && !ev.isAltDown()) || badchars.indexOf(c) > -1) {
            ev.consume();
         } else {
            super.processKeyEvent(ev);
         }
      }

      public Double getValue() {
         return Double.valueOf(super.getText());
      }

      // public void setValue(Double d) {
      // super.setText(String.valueOf(d));
      // }

      @Override
      public void actionPerformed(ActionEvent e) {
         x = getValue();
         f_prime.set(Linear.createPointSlope(Differentiation.derivative(getFunction(), x), x, getFunction().f(x)));
         selectAll();
      }

      @Override
      public void focusGained(FocusEvent e) {
         selectAll();
      }

      @Override
      public void focusLost(FocusEvent e) {
         x = getValue();
         f_prime.set(Linear.createPointSlope(Differentiation.derivative(getFunction(), x), x, getFunction().f(x)));
      }

   }
}