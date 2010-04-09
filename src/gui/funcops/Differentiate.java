package gui.funcops;

import graphics.Graph2D;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import math.calculus.Differentiation;
import math.functions.Function;
import math.functions.ModularFunction;

public class Differentiate extends FunctionOperation implements ItemListener {

   private static final long serialVersionUID = -864954151390321214L;

   private ModularFunction   f_prime;

   public Differentiate(Function f) {
      super(f);
      f_prime = new ModularFunction();
   }

   @Override
   public void onGraph(Graph2D graph) {
      graph.setColor(Color.BLUE);
      graph.drawFunction(f_prime);
   }

   @Override
   public void itemStateChanged(ItemEvent e) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
         f_prime.set(Differentiation.derivative(getFunction()));
      } else if (e.getStateChange() == ItemEvent.DESELECTED) {
         f_prime.reset();
      }
   }
}