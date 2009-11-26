package math.functions;

public class FunctionIdentities {

	public static Function AdditionIdentity() {
		return new Function() {
			public double f(double a) {
				return 0;
			}
		};
	}
	public static Function MultiplicationIdentity() {
		return new Function() {
			public double f(double a) {
				return 1;
			}
		};
	}
}
