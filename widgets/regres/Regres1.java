package widgets.regres;

/**
 * @author sanbok
 */
public abstract class Regres1 extends RegresTesters {
	private double a;

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.CalcParam#calculateParameters()
	 */
	public void calculateParameters() {
		double  tmp;
		double sumW2 = 0, sumWY = 0;
		for (int i = 0; i < factorArray.length; i++) {
			tmp = this.fi1(factorArray[i]);
			sumW2 += Math.pow(tmp, 2);
			sumWY += tmp * avrgArray[i];
		}
		a = sumWY / sumW2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.CalcParam#f(java.lang.Float)
	 */
	public double f(double arg0) {
		return a * this.fi1(arg0);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.CalcParam#k()
	 */
	public int k() {
		return 1;
	}

	public String parametersAsString() {
		return ("a=" + String.valueOf(a));
	}

	public abstract double fi1(double x);
	
	public double getA(){
		return a;
	}
}
