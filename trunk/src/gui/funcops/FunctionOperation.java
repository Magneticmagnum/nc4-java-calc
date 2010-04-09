package gui.funcops;

import graphics.Graphable;

import java.awt.event.ItemListener;

import math.functions.Function;

public abstract class FunctionOperation implements Graphable, ItemListener {

   private Function function;

   public FunctionOperation(Function f) {
      this.function = f;
   }

   public Function getFunction() {
      return function;
   }

}
