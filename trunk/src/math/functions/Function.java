package math.functions;

/**
 * An {@code abstract class} for defining a mathematical function.
 * 
 * @author Brian Norman
 * @version 0.1
 */
public abstract class Function {

   public static final Function NULL_FUNCTION = new Function() {
                                                 @Override
                                                 public double f(double x) {
                                                    return Double.NaN;
                                                 }
                                              };

   private double               UID;

   public Function() {
      UID = Math.random();
   }


   /**
    * Defines a mathematical relationship between an input and an output.
    * 
    * @param x
    *           input number
    * @return output number
    */
   public abstract double f(double x);


   // BORED document: Function#add(Function)
   public Function add(final double a) {
      return Function.add(this, a);
   }

   // BORED document: Function#add(Function, Function)
   public static final Function add(final Function f, final double a) {
      return new Function() {
         @Override
         public double f(double x) {
            return a + f.f(x);
         }
      };
   }

   // BORED document: Function#add(Function)
   public Function add(final Function f) {
      return Function.add(this, f);
   }

   // BORED document: Function#add(Function, Function)
   public static final Function add(final Function f, final Function g) {
      return new Function() {
         @Override
         public double f(double x) {
            return f.f(x) + g.f(x);
         }
      };
   }

   // BORED document: Function#subtract(Function)
   public Function subtract(final double a) {
      return Function.add(this, -a);
   }

   // BORED document: Function#subtract(Function)
   public Function subtract(final Function f) {
      return Function.subtract(this, f);
   }

   // BORED document: Function#subtract(Function, Function)
   public static final Function subtract(final Function f, final Function g) {
      return new Function() {
         @Override
         public double f(double x) {
            return f.f(x) - g.f(x);
         }
      };
   }

   // BORED document: Function#multiply(Function)
   public Function multiply(final double a) {
      return Function.multiply(this, a);
   }

   // BORED document: Function#multiply(Function, Function)
   public static final Function multiply(final Function f, final double a) {
      return new Function() {
         @Override
         public double f(double x) {
            return a * f.f(x);
         }
      };
   }

   // BORED document: Function#multiply(Function)
   public Function multiply(final Function f) {
      return Function.multiply(this, f);
   }

   // BORED document: Function#multiply(Function, Function)
   public static final Function multiply(final Function f, final Function g) {
      return new Function() {
         @Override
         public double f(double x) {
            return f.f(x) * g.f(x);
         }
      };
   }

   // BORED document: Function#divide(Function)
   public Function divide(final double a) {
      return Function.multiply(this, 1.0 / a);
   }

   // BORED document: Function#divide(Function)
   public Function divide(final Function f) {
      return Function.divide(this, f);
   }

   // BORED document: Function#divide(Function, Function)
   public static final Function divide(final Function f, final Function g) {
      return new Function() {
         @Override
         public double f(double x) {
            return f.f(x) / g.f(x);
         }
      };
   }

   // BORED document: Function#composite(Function)
   public Function composite(final Function g) {
      return Function.composite(this, g);
   }

   // BORED document: Function#composite(Function, Function)
   public static final Function composite(final Function f, final Function g) {
      return new Function() {
         @Override
         public double f(double x) {
            return f.f(g.f(x));
         }
      };
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Function) {
         Function func = (Function) obj;
         return this.UID == func.UID;
      }
      return super.equals(obj);
   }

}
