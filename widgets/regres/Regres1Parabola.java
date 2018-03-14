package widgets.regres;

/**
 * @author sanbok
 */
public class Regres1Parabola extends Regres1 {

	public String getLabelName() {
		return "q=a*x*x";
	}

	public double fi1(double x) {
		return x*x;
	}

}
