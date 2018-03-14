package widgets.regres;

public class Regres1Sqrt extends Regres1 {

	public String getLabelName() {
		return "q=a*sqrt(x)";
	}
	public double fi1(double x) {
		return Math.sqrt(x);
	}
}
