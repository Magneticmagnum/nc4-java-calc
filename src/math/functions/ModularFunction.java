package math.functions;

public class ModularFunction extends Function {

   private Function func;

   public ModularFunction() {
      this(null);
   }

   public ModularFunction(Function f) {
      set(f);
   }

   public void set(Function f) {
      this.func = Function.NULL_FUNCTION;
      if (f != null)
         this.func = f;
   }

   public void reset() {
      set(null);
   }

   @Override
   public double f(double x) {
      return func.f(x);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ModularFunction)
         return func.equals(((ModularFunction) obj).func);
      else
         return func.equals(obj);
   }

}
