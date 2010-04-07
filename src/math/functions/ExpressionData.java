package math.functions;

public class ExpressionData implements gnu.jel.reflect.Double {
   public double value = 0.0;

   public ExpressionData() {
      this.value = 0.0;
   }

   public ExpressionData(double value) {
      this.value = value;
   }

   public void setValue(double d) {
      value = d;
   }

   // implements gnu.jel.reflect.Double interface
   public double getValue() {
      return value;
   }
}
