package math.functions;
import math.functions.Function;
import math.calculus.Differentiation;
/**
 * @author Steve Nelson
 * Work in progress...
 *
 */
public final class getRoot 
{
/*This class has methods for finding roots of a function given one or two initial points. Each method has an associated
 * convergence method that returns the number of times the method should be called to gain a given precision (eps).*/
	

	/*CAUTION EXPERIMENTAL getRoot()s
	This function is a hybrid method for root finding which uses bisection, newtons, and the secant method for finding roots.

	Overloaded options:
	1) searches for root by using alternative methods in unstable areas and newtons for the rest.
	2) searches for root to a given tolerance by using alternative methods in unstable areas and newtons for the rest.
	3) tries to determine the maximum possible number of roots for a function and returns them*/
	public double getRoot(double a, Function f)
	{	
		double root;
		
		return root;
	}
	public double getRoot(double a, double eps, Function f)
	{	
		double root;
		
		return root;
	}
	public double[] getRoots(Function f)
	{
		int max = maxRoots(f);//maxRoots needs to be declared and defined
		double []roots = new double[max];
		
		return roots;
	}
	
	//BISECTION METHOD FOR ROOT FINDING//////////////////////////////////////
	public double bisection(double a, double b, Function f)
	{//should be used in areas that are unstable. ie when the slope of f(x) is small.
	 //Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid P79
		
		int n;//number of 
		double c, fa, fb, fc, error, eps, root;
		
		eps = machineEps();//find root within this tolerance
		n =	convergence_bisection(a, b, eps, f);
		
		fa = f(a);
		fb = f(b);
		
		if((fa*fb)<0)//check for sign. if(sign fa == sign fb)...
		{
			//ERROR output: a, b, fa, fb, "function has same signs at a and b
		}
		else
		{
			error = b - a;
			for(int i = 0; i < n; i ++) 
			{
				error = error/2.0;
				c = a + error;
				fc = f(c);
				//output: n, c, fc, error
				if(Math.abs(error)< eps)
				{
					// convergence at c
					root = c; 
					break;
				}
				if( (fa*fc) < 0)//check for sign. if(sign fa == sign fb)...
				{
					b = c;
					fb = fc;
				}
				else
				{
					a = c;
					fa = fc;
				}
			}
		}
		return root;
	}
	public int convergence_bisection(double a, double b, double eps, Function f)
	{
		int n; //maximum number of steps to converge to some tolerance(eps)
		n = (int)(log(b-a)-log(2*eps))/log(2);
		return n;
	}
	
	//FALSE POSITION METHOD FOR ROOT FINDING//////////////////////////////////////
	public double false_Position(double a, double b, Function f)
	{//should be used in areas that are unstable. ie when the slope of f(x) is small.
		 //Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid P79
			
			int n;//number of 
			double c = 0, fa, fb, fc, error, eps, root;
			
			eps = machineEps();//find root within this tolerance
			n =	convergence_bisection(a, b, eps, f);
			
			fa = f(a);
			fb = f(b);
			
			if((fa*fb)<0)//check for sign. if(sign fa == sign fb)...
			{
				//ERROR output: a, b, fa, fb, "function has same signs at a and b
			}
			else
			{
				error = b - a;
				for(int i = 0; i < n; i ++) 
				{
					error = error/2.0;
					if(c ==((a*fb-b*fa)/(fb-fa)) )//if the secant line hits the same point again,...
					{   //change the slope of the secant line to get closer to the root
						if (fa*fb < 0)
						{
							c = (a*fb-2*b*fa)/(fb-2*fa);//the 2 guarantees superlinear convergence; however, other methods exist to get even better convergence. 
						}
						else 
						{
							c = (2*a*fb-b*fa)/(2*fb-fa);//the 2 guarantees superlinear convergence; however, other methods exist to get even better convergence.
						}
					}
					else
					{

						c = (a*fb-b*fa)/(fb-fa);
					}
					fc = f(c);
					//output: n, c, fc, error
					if(Math.abs(error)< eps)
					{
						// convergence at c
						root = c; 
						break;
					}
					if( (fa*fc) < 0)//check for sign. if(sign fa == sign fb)...
					{
						b = c;
						fb = fc;
					}
					else
					{
						a = c;
						fa = fc;
					}
				}
			}
			return root;
	}
	public int convergence_false_Position(double a, double b, double eps, Function f)
	{//the convergence rate is 2/3 but deriving it will take some work
		int n;
	
		return n;
	}

	//NEWTONS METHOD FOR ROOT FINDING//////////////////////////////////////
	public double newtons(double a,  Function f)
	{
		Function fprime = derivative(f);//NOT SURE IF IM DOING THIS CORRECTLY. 
		int n; 
		double gamma = .25;//minimum slope allowed. small slope = VOLATILE 
		double eps, d, fa, fp, root;
		
		fa = f(a);
		eps = machineEps();//find root within this tolerance
		n =	convergence_newtons(a, b, eps, f);
		
		//output: 0, a, fa
		for(int i = 0; i < n; i++)
		{
			fp = fprime(a);
			if (Math.abs(fp) < gamma)
			{
				//output: probably shouldn't use newtons method at this point
				//break;?
			}
			
			d = fa/fp;
			a -= d;
			fa = f(a);
			//output: n, x, fx
			if(Math.abs(d) < eps)
			{
				root = a;
				break;
			}
		}
		return root;
	}
	public int convergence_newtons(double a, double eps, Function f)
	{//don't totally understand how this can be derived for any function yet
		double root;
		
		return root;
	}
	
	//SECANT METHOD FOR ROOT FINDING//////////////////////////////////////
	public double secant(double a, double b, Function f)
	{
		double root;
		
		return root;
	}
	public int convergence_secant(double a, double b, double eps, Function f)
	{
		double root;
		
		return root;
	}
	
	//machineEps() will find the smallest possible number which can be represented on the current machine. 
	public double machineEps()
	{
		double intPart1, intPart2, machineEps;
		intPart1 = 1.0;
		do
		{
			machineEps = intPart1;
		    intPart1 /= 2;
		    intPart2 = 1.0 + intPart1;
		}while (intPart2 > 1.0);
		
		return machineEps;//the smallest possible number such that 1.0 + intPart1 > 1.0 in the machine
	}
}