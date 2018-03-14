package widgets.regres;


/**
 * @author sanbok
 */
public abstract class Regres2 extends RegresTesters {
	double a, minA, maxA, b, minB, maxB;

	public void calculateParameters() {
		double sumX1X1 = 0, sumX2X2 = 0, sumX1X2 = 0, sumX1Y = 0, sumX2Y = 0, d;

		for (int i = 0; i < factorArray.length; i++) {
			double x1 = factorArray[i];
			double x2 = avrgArray[i];
			sumX1X1 += this.fi1(x1) * this.fi1(x1);
			sumX2X2 += this.fi2(x1) * this.fi2(x1);
			sumX1X2 += this.fi1(x1) * this.fi2(x1);
			sumX1Y += this.fi1(x1) * x2;
			sumX2Y += this.fi2(x1) * x2;
		}
		d = (sumX1X1 * sumX2X2) - (sumX1X2 * sumX1X2);
		a = ((sumX1Y * sumX2X2) - (sumX2Y * sumX1X2)) / d;
		b = ((sumX2Y * sumX1X1) - (sumX1Y * sumX1X2)) / d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.RegresTesters#f(java.lang.Float)
	 */
	public double f(double arg0) {
		return a * this.fi1(arg0) + b * this.fi2(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.RegresTesters#k()
	 */
	public int k() {
		return 2;
	}

	public String parametersAsString() {
		return ("a=" + String.valueOf(a) +"\n"+ "b=" + String.valueOf(b));
	}

	public abstract double fi1(double x) ;
	

	public abstract double fi2(double x) ;
	
	public double getA(){
		return a;
	}
	public double getB(){
		return b;
	}
}
