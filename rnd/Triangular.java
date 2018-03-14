package rnd;

public class Triangular extends RandomGenerators {
	private double a=0;
	private double b=1;
	private double c=2;
	
	
	public Triangular() {
		super();
	}

	public Triangular(double a, double b, double c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Triangular(double a, double b, double c, boolean round) {
		this( a,  b,  c);
		setNextAsRound(round);
	}
	public static boolean isCorrectParameters(double a, double b, double c) {
		if (b<a || c<b ) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Має бути a<=b<=c",
					"Трикутне розподілення",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;
	}
	public String toString() {
		return "Трикутне(a=" + a + "; b=" + b + "; c="+c+ ")";
	}

	@Override
	public double basicNext() {
		return basicNext(rnd.nextDouble());
	}

	@Override
	public double basicNext(double r) {
		double ba = b - a;
		double ca = c - a;
		double cb = c - b;
		if (r <= ba / ca)
			return a + Math.sqrt(r * ca * ba);
		else
			return c - Math.sqrt((1 - r) * cb * ca);
	}

	@Override
	public int getKParm() {
		
		return 3;
	}

	public double average() {
		
		return ((a+2*(b-a)/3)*(b-a)+(c-b)*(c-2*(c-b)/3))/(c-a);
	}

	public boolean average(double m) {
		return false;
	}

	public double max() {
		
		return c;
	}

	public double probability(double aNumber) {
		if(aNumber<=a)return 0;
		else if (aNumber>=c)return 1;
		else if(aNumber<=b)return (aNumber-a)*(aNumber-a)/(c-a)/(b-a);
		else return 1-(c-aNumber)*(c-aNumber)/(c-a)/(c-b);
	}
	@Override
	public RandomDlg getDialog() {
		return new TriangularDlg();
	}
}
