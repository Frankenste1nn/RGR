package widgets.regres;

/**
 * @author sanbok
 */
public class Regres1NonProportion1 extends Regres1 {

	public String getLabelName() {
		return "q=a*x/(1-x)";
	}

	public double fi1(double x) {
		if (x == 1)
			return 100;
		return x / (1 - x);
	}
}
