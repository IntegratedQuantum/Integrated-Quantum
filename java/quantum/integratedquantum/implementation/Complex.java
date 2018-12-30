package quantum.integratedquantum.implementation;

public class Complex {
	public double r;
	public double i;
	public Complex(double r, double i) {
		this.r = r;
		this.i = i;
	}
	public Complex plus(Complex z) {
		return new Complex(r+z.r, i+z.i);
	}
	public Complex minus(Complex z) {
		return new Complex(r-z.r, i-z.i);
	}
	public Complex times(Complex z) {
		return new Complex(r*z.r-i*z.i, r*z.i+i*z.r);
	}
	public Complex times(double x) {
		return new Complex(r*x, i*x);
	}
	public Complex divide(Complex z) {
		double r2 = 0;
		double i2 = 0;
		if(z.r != 0) {
			r2 = r/z.r;
			i2 = i/z.r;
		}
		if(z.i != 0) {
			r2 += i/z.i;
			i2 -= r/z.i;
		}
		return new Complex (r2, i2);
	}
	public Complex ePow() {
		double a = Math.exp(r);
		return new Complex(a*Math.cos(i), a*Math.sin(i));
	}
	public Complex negate() {
		return new Complex(-r, -i);
	}
	public static Complex sqrt(double x) {
		if (x < 0)
			return new Complex(0, Math.sqrt(-x));
		return new Complex(Math.sqrt(x), 0);
	}
	public double valueSquared() {
		return r*r+i*i;
	}
	public String toString() {
		String ret = "";
		if(r != 0)
			ret += r;
		if(ret.length() > 0 && i > 0)
			ret += "+";
		if(i != 0)
			ret += i+"i";
		if(ret.length() == 0)
			ret = "0";
		return ret;
	}
}
