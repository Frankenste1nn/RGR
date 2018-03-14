package rnd;

/**
 * Insert the type's description here. Creation date: (30.03.2005 20:33:05)
 * 
 * @author: Administrator
 */
public class Norm extends RandomGenerators {
	private double m = 1;

	private double sigma = 0.1;

	/**
	 * Norm constructor comment.
	 */
	public Norm() {
		super();
	}

	/**
	 * Norm constructor comment.
	 */
	public Norm(double newM, double newSigma) {
		this();
		m = newM;
		sigma = newSigma;
	}

	/**
	 * Norm constructor comment.
	 */
	public Norm(double newM, double newSigma, boolean round) {
		this(newM, newSigma);
		setNextAsRound(round);
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:33:05)
	 * 
	 * @return boolean
	 */
	public double basicNext() {
		if (sigma == 0) {
			return m;
		}
		;
		double sum = 0;
		for (int i = 0; i < 12; i++) {
			sum = sum + rnd.nextDouble();
		}
		return (sum - 6) * sigma + m;
	}

	/**
	 * Insert the method's description here. Creation date: (22.01.2006
	 * 20:56:38)
	 * 
	 * @return int
	 */
	public int getKParm() {
		return 2;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:35:09)
	 * 
	 * @return double
	 */
	public double getM() {
		return m;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:35:39)
	 * 
	 * @return double
	 */
	public double getSigma() {
		return sigma;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:34:23)
	 * 
	 * @return boolean
	 */
	public static boolean isCorrectParameters() {

		return false;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:34:23)
	 * 
	 * @return boolean
	 */
	public static boolean isCorrectParameters(double m, double sigma) {
		if (m <= 0) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Середнє значення интервалу має бути більше 0",
					"Нормальне розподілення",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		if (m / sigma < 5) {
			javax.swing.JOptionPane
					.showMessageDialog(
							null,
							" m/sigma дуже мале. Можна отримати від'ємні числа",
							"Нормальне розподілення",
							javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:33:05)
	 * 
	 * @return double
	 * @param aNumber
	 *            double
	 */
	public double probability(double aNumber) {
		if (sigma == 0) {
			if (aNumber < m) {
				return 0;
			}
			;
			if (aNumber > m) {
				return 1;
			}
			;
			if (aNumber == m) {
				return 0.5;
			}
			;
		}
		double normNumber = (aNumber - m) / sigma;
		if (normNumber > 6) {
			return 1;
		}
		;
		if (normNumber < -6) {
			return 0;
		}
		;
		double x = normNumber / Math.sqrt(2.0);
		double s = x;
		double u = x;
		int i = 1;
		while (Math.abs(u) > 0.0000001) {
			u = -u * x * x / i / (2 * i + 1) * (2 * i - 1);
			s = s + u;
			i = i + 1;
		}
		;
		return s / Math.sqrt(Math.PI) + 0.5;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:35:09)
	 * 
	 * @param newM
	 *            double
	 */
	public void setM(double newM) {
		m = newM;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 20:35:39)
	 * 
	 * @param newSigma
	 *            double
	 */
	public void setSigma(double newSigma) {
		sigma = newSigma;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	public String toString() {
		// Insert code to print the receiver here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		return "Нормальне(m=" + getM() + ";сигма=" + getSigma() + ")";
	}
	public double average(){
	return m;
	}
	public double max(){
		return m+2*sigma;
		}

	@Override
	public double basicNext(double r) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean average(double a) {
		double k=sigma/m;
		m=a;
		sigma=a*k;
		return true;
	}
	@Override
	public RandomDlg getDialog() {
		return new NormDlg();
	}
}
