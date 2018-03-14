package widgets.regres;

public class Regres2Giperbola extends Regres2 {

	public String getLabelName() {
		return "q=a/x+b";
	}
	public double fi1(double x) {
		return 1/x;
	}
	
	public double fi2(double x) {
		
		return 1;
	}

}
