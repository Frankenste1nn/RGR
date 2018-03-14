package widgets.regres;

/**
 * @author sanbok
 */
public class Regres1Proportion extends Regres1 {




	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.Tester#getLabelName()
	 */
	public String getLabelName() {
		return "q=a*x";
	}


	public double fi1(double x) {
		return x;
	}

}
