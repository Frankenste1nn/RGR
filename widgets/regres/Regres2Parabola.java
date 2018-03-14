package widgets.regres;

public class Regres2Parabola extends Regres2 {
	
	public double fi1(double x) {
		return x*x;
	}

	public double fi2(double x) {
		return x;
	}
	
	public String getLabelName() {
		return "q=a*x*x+b*x";
	}

}
