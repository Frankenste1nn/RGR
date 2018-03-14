package widgets.regres;


/**
 * @author sanbok
 */
public class Regres2Linear extends Regres2{
	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationRegress.Tester#getLabelName()
	 */
	public String getLabelName() {
		return "q=a*x+b";
	}

	@Override
	public double fi1(double x) {
	
		return x;
	}


	@Override
	public double fi2(double x) {
		
		return 1;
	}

	
}
