package math.functions;

import gnu.jel.CompiledExpression;


public class FunctionExpression extends Function {

   CompiledExpression exp;
   ExpressionData[]   data;
   Object[]           context;

   public FunctionExpression(CompiledExpression exp, ExpressionData[] data, Object[] context) {
      this.exp = exp;
      this.data = data;
      this.context = context;
   }

   @Override
   public double f(double x) {
      // TODO Auto-generated method stub
      double result = 0.0;
      data[0].setValue(x);
      try {
         result = exp.evaluate_double(context);
      } catch (Throwable e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return result;
   }

   public double f(double[] x) {
      double result = 0.0;
      if (x.length == data.length) {
         for (int i = 0; i < x.length; i++)
            data[i].setValue(x[i]);
         try {
            result = exp.evaluate_double(context);
         } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      } else
         System.err.println("Incompatible function argument length!");
      return result;
   }

}
