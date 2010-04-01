package math.functions;
import java.util.ArrayList;
import gnu.jel.*;

public class ExpressionEngine {
	public Library lib;
	public ExpressionDynamicVar resolver;
	public Object[] context;
	public ExpressionEngine() {
		//**********************************************************
		//**** to use JEL we first have to define the namespace ****

		// we shall export the static methods of java.lang.Math
		Class[] stLib=new Class[1];
		stLib[0]=java.lang.Math.class;

		// we shall enable access to methods of two classes
		Class[] dynLib=new Class[1];
		// we export YYY getXXXProperty() methods for dynamic variable access
		dynLib[0]=ExpressionDynamicVar.class;

		// this initializes the resolver object
		resolver=new ExpressionDynamicVar();

		// we shall allow dot operators on strings and data
		Class[] dotLib=new Class[2];
		dotLib[0]=ExpressionData.class;
		dotLib[1]=java.lang.String.class;
		
		// finally, the namespace is defined by constructing the library class
		lib=
		    new gnu.jel.Library(stLib,dynLib,dotLib,resolver,null);


		//**********************************************************
		//******** Now we setup the global context and data  *******

		context=new Object[1];
		context[0]=resolver; // this pointer for YYY getXXXProperty() methos
	}
	/* Use this function to construct a compiled expression!
	 * that way all the ExpressionData pointers can be passed
	 * appropriately.
	 */
	public FunctionExpression constructFunction(String expression, String[] vars)
	{
		ExpressionData[] data = new ExpressionData[vars.length];
		for (int i = 0 ; i < data.length; i++)
		{
			data[i] = new ExpressionData(0.0);
			resolver.addProperty(vars[i], data[i]);
		}
		FunctionExpression f = null;
		try {
			f = new FunctionExpression(
					Evaluator.compile(expression, lib),
					data,
					context);
		} catch (CompilationException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}		
		return f;
	}
	
}
