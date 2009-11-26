package math.functions;

public class FunctionSymbol {
	private String symb;
	private double dconstant;
	private boolean isindependent;
	
	public FunctionSymbol(String symb)
	{
		this.symb = symb;
		isindependent = false;
	}
	public FunctionSymbol(double dconst)
	{
		this.dconstant = dconst;
		isindependent = false;
	}
	public FunctionSymbol(String symb, boolean isindependent)
	{
		this.isindependent = isindependent;
		this.symb = symb;
	}
	public String getSymbol()
	{
		return symb;
	}
	public double getDConst()
	{
		return dconstant;
	}
	public boolean isIndependent()
	{
		return isindependent;
	}
}
